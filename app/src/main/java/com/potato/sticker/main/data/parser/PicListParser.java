package com.potato.sticker.main.data.parser;

import com.potato.chips.base.BaseParser;
import com.potato.sticker.main.data.bean.PicBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ztw on 2015/10/23.
 */
public class PicListParser extends BaseParser{

    public String curPage = "";
    public String pageSize = "";
    public String rowCount = "";

    public List<PicBean> list = new ArrayList<PicBean>();

    public PicListParser(String jsonStr) {
        super(jsonStr);
        try {
            if (root.optJSONObject("page") != null) {
                JSONObject obj = root.optJSONObject("page");
                curPage = obj.optString("curPage");
                pageSize = obj.optString("pageSize");
                rowCount = obj.optString("rowCount");
                JSONArray array = obj.optJSONArray("content");
                if (array != null) {
                    list = PicBean.createFromJSONArray(array);
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
