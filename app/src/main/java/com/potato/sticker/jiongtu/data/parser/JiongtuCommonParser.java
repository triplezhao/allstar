package com.potato.sticker.jiongtu.data.parser;

import com.potato.chips.base.BaseParser;

import org.json.JSONObject;

public class JiongtuCommonParser extends BaseParser {


    public JiongtuCommonParser(String jsonStr) {
        super(jsonStr);
        try {
            if (root.optJSONObject("obj") != null) {
                JSONObject obj = root.optJSONObject("obj");
            }
            JSONObject minfo = root.optJSONObject("user");
            if (minfo != null) {
//                showUserBean = showUserBean.createFromJSON(minfo);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
