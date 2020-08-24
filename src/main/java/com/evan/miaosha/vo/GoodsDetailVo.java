package com.evan.miaosha.vo;

import com.evan.miaosha.domain.MiaoshaUser;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-08-09
 */
public class GoodsDetailVo {

    private int miaoshaStatus = 0;
    private int remainSeconds = 0; //秒杀活动还有多久开始
    private GoodsVo goods;
    private MiaoshaUser user;

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public GoodsDetailVo(int miaoshaStatus, int remainSeconds, GoodsVo goods, MiaoshaUser user) {
        this.miaoshaStatus = miaoshaStatus;
        this.remainSeconds = remainSeconds;
        this.goods = goods;
        this.user = user;
    }

    public GoodsDetailVo() {
    }
}
