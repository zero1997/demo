package com.evan.miaosha.rabbitmq;

import com.evan.miaosha.domain.MiaoshaUser;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-08-11
 */
public class MiaoshaMessage {

    private MiaoshaUser user;
    private long goodsId;

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodId) {
        this.goodsId = goodId;
    }

    public MiaoshaMessage(MiaoshaUser user, long goodId) {
        this.user = user;
        this.goodsId = goodId;
    }

    public MiaoshaMessage() {
    }
}
