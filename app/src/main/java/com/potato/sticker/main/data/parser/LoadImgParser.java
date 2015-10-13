package com.potato.sticker.main.data.parser;

import com.potato.chips.base.BaseParser;
import com.potato.sticker.main.data.bean.LoadImgBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoadImgParser extends BaseParser {

    public ArrayList<LoadImgBean> loadImgBeans;

    public LoadImgParser(String jsonStr) {
        super(jsonStr);
        try {
            if (root.optJSONObject("obj") != null) {
                JSONObject obj = root.optJSONObject("obj");
            }
            JSONArray array = root.getJSONArray("list");
            loadImgBeans = LoadImgBean.createFromJSONArray(array);

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
