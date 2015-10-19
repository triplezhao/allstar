package com.potato.sticker.main.data.bean;

//import

import android.content.ContentValues;
import android.database.Cursor;

import com.potato.chips.base.BaseBean;
import com.potato.sticker.main.data.db.TopicBeanProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class TopicBean extends BaseBean implements Serializable {

    //propslist
    private String laudCount;
    private String id;
    private String title;
    private String userId;
    private String content;
    private String createDate;
    private String status;

    public ArrayList<PicBean> picBeans;

    public ArrayList<PicBean> getPicBeans() {
        return picBeans;
    }

    public void setPicBeans(ArrayList<PicBean> picBeans) {
        this.picBeans = picBeans;
    }

    //set get list
    public void setLaudCount(String laudCount) {
        this.laudCount = laudCount;
    }

    public String getLaudCount() {
        return this.laudCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }


    //other
    //createFromCursor
    public static TopicBean createFromCursor(Cursor cursor) {
        if (cursor == null) return null;
        TopicBean bean = new TopicBean();
        bean.setLaudCount(cursor.getString(cursor.getColumnIndex(TopicBeanProvider.Columns.laudCount)));
        bean.setId(cursor.getString(cursor.getColumnIndex(TopicBeanProvider.Columns.id)));
        bean.setTitle(cursor.getString(cursor.getColumnIndex(TopicBeanProvider.Columns.title)));
        bean.setUserId(cursor.getString(cursor.getColumnIndex(TopicBeanProvider.Columns.userId)));
        bean.setContent(cursor.getString(cursor.getColumnIndex(TopicBeanProvider.Columns.content)));
        bean.setCreateDate(cursor.getString(cursor.getColumnIndex(TopicBeanProvider.Columns.createDate)));
        bean.setStatus(cursor.getString(cursor.getColumnIndex(TopicBeanProvider.Columns.status)));
        return bean;
    }

    //createFromJSON
    public static TopicBean createFromJSON(JSONObject json) throws JSONException {
        if (json == null) return null;
        TopicBean bean = new TopicBean();
        bean.setLaudCount(json.optString("laudCount"));
        bean.setId(json.optString("id"));
        bean.setTitle(json.optString("title"));
        bean.setUserId(json.optString("userId"));
        bean.setContent(json.optString("content"));
        bean.setCreateDate(json.optString("createDate"));
        bean.setStatus(json.optString("status"));
//        bean.setPicBeans(PicBean.createFromJSONArray(json.optJSONArray("piclist")));
        return bean;
    }

    /*
    [
    { picLabl{[{picurl:xxxx,[{tag:"",...},...]}]},
      topic {id:"",name:""]}
    }],
    * */
    public static TopicBean createFromJSON(JSONObject jsonParant, String key, String lableKey) throws JSONException {
        if (jsonParant == null) return null;
        JSONObject json = jsonParant.getJSONObject(key);
        TopicBean bean = createFromJSON(json);
        JSONArray jsonLable = jsonParant.getJSONArray(lableKey);
        ArrayList<PicBean> tagsbean = PicBean.createFromJSONArray(jsonLable);
        bean.setPicBeans(tagsbean);
//        bean.setPicBeans(PicBean.createFromJSONArray(json.optJSONArray("piclist")));
        return bean;
    }

    //createFromJSONArray
    public static ArrayList<TopicBean> createFromJSONArray(JSONArray jsonArray) throws JSONException {

        if (jsonArray == null) return null;

        ArrayList<TopicBean> list = new ArrayList<TopicBean>();

        int count = jsonArray.length();
        for (int i = 0; i < count; i++) {
            JSONObject jsonObj = jsonArray.optJSONObject(i);
            TopicBean entity = TopicBean.createFromJSON(jsonObj,"topic","picLabel");
            list.add(entity);
        }
        return list;
    }
    //createFromJSONArray
    public static ArrayList<TopicBean> createFromJSONArray(JSONArray jsonArray, String key, String lableKey) throws JSONException {

        if (jsonArray == null) return null;

        ArrayList<TopicBean> list = new ArrayList<TopicBean>();

        int count = jsonArray.length();
        for (int i = 0; i < count; i++) {
            JSONObject jsonObj = jsonArray.optJSONObject(i);
            TopicBean entity = TopicBean.createFromJSON(jsonObj,key,lableKey);
            list.add(entity);
        }
        return list;
    }

    //buildContentValues
    public static ContentValues buildContentValues(BaseBean baseBean) {
        TopicBean bean = new TopicBean();

        if (baseBean != null) {
            bean = (TopicBean) baseBean;
        }
        ContentValues values = new ContentValues();
        values.put(TopicBeanProvider.Columns.laudCount, bean.getLaudCount());
        values.put(TopicBeanProvider.Columns.id, bean.getId());
        values.put(TopicBeanProvider.Columns.title, bean.getTitle());
        values.put(TopicBeanProvider.Columns.userId, bean.getUserId());
        values.put(TopicBeanProvider.Columns.content, bean.getContent());
        values.put(TopicBeanProvider.Columns.createDate, bean.getCreateDate());
        values.put(TopicBeanProvider.Columns.status, bean.getStatus());
        return values;
    }

}