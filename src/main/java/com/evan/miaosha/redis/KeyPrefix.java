package com.evan.miaosha.redis;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-08-01
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
