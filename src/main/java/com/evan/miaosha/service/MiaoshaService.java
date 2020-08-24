package com.evan.miaosha.service;

import java.beans.Transient;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evan.miaosha.dao.GoodsDao;
import com.evan.miaosha.domain.Goods;
import com.evan.miaosha.domain.MiaoshaOrder;
import com.evan.miaosha.domain.MiaoshaUser;
import com.evan.miaosha.domain.OrderInfo;
import com.evan.miaosha.redis.MiaoshaKey;
import com.evan.miaosha.redis.RedisService;
import com.evan.miaosha.vo.GoodsVo;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-08-04
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单 必须在一个事务里面
        boolean success = goodsService.reduceStock(goods);
        //order_info miaosha_order
        if (success) {
            return orderService.createOrder(user, goods);
        } else {
            //做一个该id的商品已经被秒杀完毕的标记
            setGoodsOver(goods.getId());
            return null;
        }

    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if (order != null) {
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                //如果卖完了说明排队的能买到的都买到了，那么就肯定没有秒杀到
                return -1;
            } else {
                //如果没卖完，说明很多人都还在排队买，还在排队中
                return 0;
            }
        }
    }


    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, "" + goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, "" + goodsId);
    }
}













