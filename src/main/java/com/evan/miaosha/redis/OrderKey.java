package com.evan.miaosha.redis;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-08-01
 */
public class OrderKey extends BasePrefix {
    public OrderKey(String prefix) {

        super(prefix);
    }

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");

}
