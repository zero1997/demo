package com.evan.miaosha.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evan.miaosha.domain.Goods;
import com.evan.miaosha.domain.MiaoshaOrder;
import com.evan.miaosha.domain.MiaoshaUser;
import com.evan.miaosha.domain.OrderInfo;
import com.evan.miaosha.rabbitmq.MQSender;
import com.evan.miaosha.rabbitmq.MiaoshaMessage;
import com.evan.miaosha.redis.GoodsKey;
import com.evan.miaosha.redis.RedisService;
import com.evan.miaosha.result.CodeMsg;
import com.evan.miaosha.result.Result;
import com.evan.miaosha.service.GoodsService;
import com.evan.miaosha.service.MiaoshaService;
import com.evan.miaosha.service.MiaoshaUserService;
import com.evan.miaosha.service.OrderService;
import com.evan.miaosha.vo.GoodsVo;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-07-31
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    private static Logger log = LoggerFactory.getLogger(MiaoshaController.class);

    @Autowired
    RedisService redisService;

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    MQSender sender;

    private Map<Long, Boolean> localOverMap = new HashMap<>();


    /**
     * spring在系统初始化的时候会调用这个方法，在系统初始化的时候就把商品库存加载到redis中来
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null) {
            return;
        }
        for (GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goods.getId(), goods.getStockCount());
            //标记秒杀没有结束
            localOverMap.put(goods.getId(), false);
        }


    }

    /**
     * 2229/sec
     * 5000*10
     *
     * 消息队列等等优化之后3912/sec
     */

    /**
     * GET POST有什么区别
     * GET向服务端获取数据，幂等性，做多少次get的话结果都应该一样，如果不一样那就用错了
     * POST向服务端提交数据，传输信息给服务器，信息写在请求体中。
     * 其实get也可以通过url配合？来传送参数，所以两者很多时候可以混用，但是get方法必须是幂等的
     * 其他区别可见：https://www.cnblogs.com/hyddd/archive/2009/03/31/1426026.html
     * PUT和这两个完全不同，PUT是向服务器提交文件，现在都用ftp协议代替
     */
    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaosha(Model model, MiaoshaUser user,
            @RequestParam("goodsId") long goodsId) {

        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //内存标记，来减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //防止多次秒杀??
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //入队
        MiaoshaMessage mm = new MiaoshaMessage(user, goodsId);
        sender.sendMiaoshaMessage(mm);
        return Result.success(0); //排队中

        /*//判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //防止多次秒杀
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goods.getId());
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //减库存 下订单 写入秒杀订单 必须在一个事务里面
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        return Result.success(orderInfo);*/
    }

    /**
     * 客户端将要调用的轮询方法，通过http调用
     * 成功，返回orderId
     * 失败，返回-1
     * 排队中，返回0
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, MiaoshaUser user,
            @RequestParam("goodsId") long goodsId) {

        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }
}
