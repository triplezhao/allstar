package com.potato.sticker.main.data.parser;

import com.potato.chips.base.BaseParser;

public class TokenParser extends BaseParser {

    public String token = "";

    public TokenParser(String jsonStr) {
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
        return true;
    }

}
