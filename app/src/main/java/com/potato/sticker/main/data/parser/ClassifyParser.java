package com.potato.sticker.main.data.parser;

import com.potato.chips.base.BaseParser;
import com.potato.sticker.main.data.bean.ClassifyBean;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class ClassifyParser extends BaseParser {


    public List<ClassifyBean> list = new ArrayList<ClassifyBean>();

    public ClassifyParser(String jsonStr) {
        super(jsonStr);
        try {
            if (root.optJSONObject("classify") != null) {
                JSONArray array = root.optJSONArray("classify");
                if (array != null) {
                    list = ClassifyBean.createFromJSONArray(array);
                }
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
