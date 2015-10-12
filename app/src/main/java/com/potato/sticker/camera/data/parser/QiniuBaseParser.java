package com.potato.sticker.camera.data.parser;

import com.potato.chips.base.BaseParser;

/**
 * Created by fgh on 2015/10/10.
 */
public class QiniuBaseParser extends BaseParser {
    // 错误信息

    public QiniuBaseParser(String jsonStr) {
        super(jsonStr);

    }

    @Override
    public String getCode() {
        return "0";
    }

    @Override
    public String getMsg() {
        return "cucc";
    }

    @Override
    public boolean isSucc() {
        if (root != null) {
            return true;
        }
        return false;
    }

}
