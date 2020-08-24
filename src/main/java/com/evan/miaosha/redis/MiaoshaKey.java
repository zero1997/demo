package com.evan.miaosha.redis;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-08-01
 */
public class MiaoshaKey extends BasePrefix {
    private MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");
}
