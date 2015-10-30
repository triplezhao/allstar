package com.potato.sticker.main.data.parser;

import com.potato.chips.base.BaseParser;
import com.potato.sticker.main.data.bean.UserBean;

import org.json.JSONObject;

public class UserParser extends BaseParser {

    public UserBean user;

    public UserParser(String jsonStr) {
        super(jsonStr);
        try {
            if (root.optJSONObject("obj") != null) {
                JSONObject obj = root.optJSONObject("obj");
            }
            JSONObject minfo = root.optJSONObject("user");
            if (minfo != null) {
                user = UserBean.createFromJSON(minfo);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMsg() {
        return null;
    }

    @Override
    public boolean isSucc() {
        return user != null;
    }

}
