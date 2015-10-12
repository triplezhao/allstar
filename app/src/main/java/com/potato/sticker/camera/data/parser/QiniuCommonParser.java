package com.potato.sticker.camera.data.parser;

import org.json.JSONObject;

public class QiniuCommonParser extends QiniuBaseParser {


    public QiniuCommonParser(String jsonStr) {
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
