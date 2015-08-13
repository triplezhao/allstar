package com.cyou.sticker.base;

import android.text.TextUtils;
import org.json.JSONObject;

/**
 * @author ztw 这个类只提供基础的解析方法，每个接口对应的解析方法在.parse.api包下面。
 */
public class BaseParser {
    public static final String succ = "000000";
    protected JSONObject root;
    // 错误信息
    private String code = ""; // 失败Code示例100101 返回code组成：1失败/成功 001 模块ID 01方法
    private String msg = ""; // "succ", //失败fail

    public BaseParser(String jsonStr) {

        if (TextUtils.isEmpty(jsonStr)) {
            return;
        }
        try {
            root = new JSONObject(jsonStr);
            setCode(root.optString("code"));
            setMsg(root.optString("msg"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSucc() {
        return code.equals(succ);
    }

    ;

}
