package com.potato.sticker.camera.data.parser;

public class QiniuTokenParser extends QiniuBaseParser {

    public String token = "";

    public QiniuTokenParser(String jsonStr) {
        super(jsonStr);
        try {
            if (root.has("uptoken")) {
                token = root.optString("uptoken");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
