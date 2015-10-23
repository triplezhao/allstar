package com.potato.sticker.main.data.bean;

//import

import android.content.ContentValues;
import android.database.Cursor;

import com.potato.chips.base.BaseBean;
import com.potato.sticker.main.data.db.CommentBeanProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class CommentBean extends BaseBean implements Serializable {

    //propslist
    private String topicId;
    private String id;
    private String reviewer;
    private String userId;
    private String content;
    private String createDate;
    private String status;

    UserBean userBean;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    //set get list
    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicId() {
        return this.topicId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewer() {
        return this.reviewer;
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
    public static CommentBean createFromCursor(Cursor cursor) {
        if (cursor == null) return null;
        CommentBean bean = new CommentBean();
        bean.setTopicId(cursor.getString(cursor.getColumnIndex(CommentBeanProvider.Columns.topicId)));
        bean.setId(cursor.getString(cursor.getColumnIndex(CommentBeanProvider.Columns.id)));
        bean.setReviewer(cursor.getString(cursor.getColumnIndex(CommentBeanProvider.Columns.reviewer)));
        bean.setUserId(cursor.getString(cursor.getColumnIndex(CommentBeanProvider.Columns.userId)));
        bean.setContent(cursor.getString(cursor.getColumnIndex(CommentBeanProvider.Columns.content)));
        bean.setCreateDate(cursor.getString(cursor.getColumnIndex(CommentBeanProvider.Columns.createDate)));
        bean.setStatus(cursor.getString(cursor.getColumnIndex(CommentBeanProvider.Columns.status)));
        return bean;
    }

    //createFromJSON
    public static CommentBean createFromJSON(JSONObject json) throws JSONException {
        if (json == null) return null;
        CommentBean bean = new CommentBean();
        bean.setTopicId(json.optString("topicId"));
        bean.setId(json.optString("id"));
        bean.setReviewer(json.optString("reviewer"));
        bean.setUserId(json.optString("userId"));
        bean.setContent(json.optString("content"));
        bean.setCreateDate(json.optString("createDate"));
        bean.setStatus(json.optString("status"));
        return bean;
    }

    //createFromJSON
    public static CommentBean createFromJSON(JSONObject jsonParant, String commentKey, String userKey) throws JSONException {
        if (jsonParant == null) return null;
        //comment 属性
        JSONObject json = jsonParant.getJSONObject(commentKey);
        CommentBean bean = createFromJSON(json);

        //user属性
        JSONObject userObj = jsonParant.getJSONObject(userKey);
        UserBean userBean = UserBean.createFromJSON(userObj);

        bean.userBean = userBean;

        return  bean;
    }

    //createFromJSONArray
    public static ArrayList<CommentBean> createFromJSONArray(JSONArray jsonArray) throws JSONException {

        if (jsonArray == null) return null;

        ArrayList<CommentBean> list = new ArrayList<CommentBean>();

        int count = jsonArray.length();
        for (int i = 0; i < count; i++) {
            JSONObject jsonObj = jsonArray.optJSONObject(i);
            CommentBean entity = CommentBean.createFromJSON(jsonObj);
            list.add(entity);
        }
        return list;
    }
    //createFromJSONArray
    public static ArrayList<CommentBean> createFromJSONArray(JSONArray jsonArray,String commentKey,String userKey) throws JSONException {

        if (jsonArray == null) return null;

        ArrayList<CommentBean> list = new ArrayList<CommentBean>();

        int count = jsonArray.length();
        for (int i = 0; i < count; i++) {
            JSONObject jsonObj = jsonArray.optJSONObject(i);
            CommentBean entity = CommentBean.createFromJSON(jsonObj,commentKey,userKey);
            list.add(entity);
        }
        return list;
    }

    //buildContentValues
    public static ContentValues buildContentValues(BaseBean baseBean) {
        CommentBean bean = new CommentBean();

        if (baseBean != null) {
            bean = (CommentBean) baseBean;
        }
        ContentValues values = new ContentValues();
        values.put(CommentBeanProvider.Columns.topicId, bean.getTopicId());
        values.put(CommentBeanProvider.Columns.id, bean.getId());
        values.put(CommentBeanProvider.Columns.reviewer, bean.getReviewer());
        values.put(CommentBeanProvider.Columns.userId, bean.getUserId());
        values.put(CommentBeanProvider.Columns.content, bean.getContent());
        values.put(CommentBeanProvider.Columns.createDate, bean.getCreateDate());
        values.put(CommentBeanProvider.Columns.status, bean.getStatus());
        return values;
    }

}