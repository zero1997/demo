package com.evan.miaosha.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.evan.miaosha.domain.Goods;
import com.evan.miaosha.domain.MiaoshaUser;
import com.evan.miaosha.domain.OrderInfo;
import com.evan.miaosha.redis.RedisService;
import com.evan.miaosha.result.CodeMsg;
import com.evan.miaosha.result.Result;
import com.evan.miaosha.service.GoodsService;
import com.evan.miaosha.service.MiaoshaUserService;
import com.evan.miaosha.service.OrderService;
import com.evan.miaosha.vo.GoodsVo;
import com.evan.miaosha.vo.OrderDetailVo;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-07-31
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    RedisService redisService;

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;


    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser user,
            @RequestParam("orderId") long orderId) {

        /**
         * 每个controller方法都需要判断user是否为空，这里我们可以搭建一个拦截器来代替这项劳动
         * @NeedLogin 类似于快手的 @LoginRequired
         */
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo(goods, order);
        return Result.success(vo);

    }

}
