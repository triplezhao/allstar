package com.potato.sticker.camera.data.parser;

import com.potato.chips.base.BaseParser;

/**
 * Created by fgh on 2015/10/10.
 */
public class QiniuBaseParser extends BaseParser{

    public QiniuBaseParser(String jsonStr) {
        super(jsonStr);
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
