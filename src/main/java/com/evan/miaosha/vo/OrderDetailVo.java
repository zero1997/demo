package com.evan.miaosha.vo;

import com.evan.miaosha.domain.Goods;
import com.evan.miaosha.domain.OrderInfo;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-08-09
 */
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo order;

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }

    public OrderDetailVo(GoodsVo goods, OrderInfo order) {
        this.goods = goods;
        this.order = order;
    }

    public OrderDetailVo() {
    }
}
