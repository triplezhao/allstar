package com.potato.sticker.main.data.parser;

import com.potato.chips.base.BaseParser;
import com.potato.sticker.main.data.bean.TopicBean;

/*
*               “curPage”:, //当前页码
               “pageSize”:, //每页条数
               “rowCount”:, //总记录数
               “content”:[
*/
public class TopicParser extends BaseParser {

    public String curPage = "";
    public String pageSize = "";
    public String rowCount = "";

    public TopicBean topicBean = new TopicBean();

    public TopicParser(String jsonStr) {
        super(jsonStr);
        try {
            topicBean = TopicBean.createFromJSON(root,"topic","picLabel");

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
