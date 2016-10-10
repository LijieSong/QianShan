package com.example.bjlz.qianshan.tools.ChangeAndGetTools;

import android.text.TextUtils;
import org.apache.commons.codec.binary.Base64;

/**
 * Base64Utils 64加密工具类
 */
public class Base64Utils {

    private static Base64 mBase64 = new Base64();

    /**
     * encode
     * @param source source
     * @return String
     */
    public static String encode(String source) {
        if (!TextUtils.isEmpty(source)) {
            return new String(mBase64.encode(source.getBytes()));
        }
        return "";
    }

    /**
     * decode
     * @param source source
     * @return String
     */
    public static String decode(String source) {
        if (!TextUtils.isEmpty(source)) {
            return new String(mBase64.decode(source.getBytes()));
        }
        return "";
    }
}