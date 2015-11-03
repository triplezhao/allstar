package com.potato.sticker.main.data.parser;

import com.potato.chips.base.BaseParser;
import com.potato.sticker.main.data.bean.MsgBean;

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
public class MsgListParser extends BaseParser {

    public String curPage = "";
    public String pageSize = "";
    public String rowCount = "0";

    public List<MsgBean> list = new ArrayList<MsgBean>();

    public MsgListParser(String jsonStr) {
        super(jsonStr);
        try {
            if (root.optJSONObject("page") != null) {
                JSONObject obj = root.optJSONObject("page");
                curPage = obj.optString("curPage");
                pageSize = obj.optString("pageSize");
                rowCount = obj.optString("rowCount");
                JSONArray array = obj.optJSONArray("content");
                if (array != null) {
                    list = MsgBean.createFromJSONArray(array);
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
