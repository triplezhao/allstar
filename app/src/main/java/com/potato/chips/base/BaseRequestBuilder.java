package com.potato.chips.base;

import android.text.TextUtils;

import java.text.MessageFormat;
import java.util.Map;

public class BaseRequestBuilder {


    protected static void addParam(Map<String, Object> params, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            params.put(key, value);
        }
    }

    protected static void addParam(Map<String, Object> params, String key, int value) {
        if (value >= 0) {
            params.put(key, value);
        }
    }

    protected static void addParam(Map<String, Object> params, String key, long value) {
        if (value >= 0) {
            params.put(key, value);
        }
    }

    /**
     * @Title: getRealRequestUrl
     * @Description: 将url中的占位符替换成真实的数据,
     * @param: @param baseUrl 基础url
     * @param: @param args url里面的参数数组
     * @param: @return 设定文件
     * @return: String 返回类型
     * @date: 2014-4-14 下午1:57:22
     */
    public static String getRealRequestUrl(String baseUrl, Object args[]) {
        String url = "";
        if (args != null && !TextUtils.isEmpty(baseUrl)) {
            url = MessageFormat.format(baseUrl, args);
        }
        return url;
    }


}
