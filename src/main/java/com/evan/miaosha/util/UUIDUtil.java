package com.evan.miaosha.util;

import java.util.UUID;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-08-03
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
