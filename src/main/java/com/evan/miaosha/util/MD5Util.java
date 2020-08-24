package com.evan.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-08-01
 */
public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    /**
     * 用户的明文密码和salt进行拼接之后做一次md5，再放到网络上传输到服务器
     * 这样md5被人截获，别人也只能查到拼接之后的密码，保障安全
     */
    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 第二次md5，这次的salt是随机的，转换之后将salt和DBPass都存入数据库
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {

        System.out.println(inputPassToDBPass("123456", "1a2b3c4d"));

    }
}
