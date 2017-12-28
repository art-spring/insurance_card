package com.jcwenhua.card.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by caichunyi on 2017/3/30.
 */
public class MD5Util {

    public static String encode(String sourceStr) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//        //确定计算方法
//        MessageDigest md5 = MessageDigest.getInstance("MD5");
//        BASE64Encoder base64en = new BASE64Encoder();
//        //加密后的字符串
//        return base64en.encode(md5.digest(sourceStr.getBytes("utf-8")));

        // 生成一个MD5加密计算摘要
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 计算md5函数
        md.update(sourceStr.getBytes());
        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
        return new BigInteger(1, md.digest()).toString(16);
    }

    public static boolean equals(String source, String old) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        return encode(source).equals(old);
    }
}
