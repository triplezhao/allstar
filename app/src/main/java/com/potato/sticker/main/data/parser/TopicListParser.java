package com.potato.sticker.main.data.parser;

import com.potato.chips.base.BaseParser;
import com.potato.sticker.main.data.bean.TopicBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
*               “curPage”:, //当前页码
               “pageSize”:, //每页条数
               “rowCount”:, //总记录数
               “content”:[
*/
public class TopicListParser extends BaseParser {

    public String curPage = "";
    public String pageSize = "";
    public String rowCount = "";

    public List<TopicBean> list = new ArrayList<TopicBean>();

    public TopicListParser(String jsonStr) {
        super(jsonStr);
        try {
            if (root.optJSONObject("page") != null) {
                JSONObject obj = root.optJSONObject("obj");
                curPage = obj.optString("curPage");
                pageSize = obj.optString("pageSize");
                rowCount = obj.optString("rowCount");
                JSONArray array = obj.optJSONArray("content");
                if (array != null) {
                    list = TopicBean.createFromJSONArray(array);
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
