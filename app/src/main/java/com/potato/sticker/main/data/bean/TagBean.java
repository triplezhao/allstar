package com.potato.sticker.main.data.bean;

//import

import android.content.ContentValues;
import android.database.Cursor;

import com.potato.chips.base.BaseBean;
import com.potato.library.util.L;
import com.potato.sticker.camera.data.bean.TagItem;
import com.potato.sticker.main.data.db.TagBeanProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class TagBean extends BaseBean implements Serializable {

    //propslist
    private String id = "1";
    private String type;
    private String name;
    private String x;
    private String y;
    private String recordCount;
    private String left;

    //set get list
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getX() {
        return this.x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getY() {
        return this.y;
    }

    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }

    public String getRecordCount() {
        return this.recordCount;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getLeft() {
        return this.left;
    }


    //other
    //createFromCursor
    public static TagBean createFromCursor(Cursor cursor) {
        if (cursor == null) return null;
        TagBean bean = new TagBean();
        bean.setId(cursor.getString(cursor.getColumnIndex(TagBeanProvider.Columns.id)));
        bean.setType(cursor.getString(cursor.getColumnIndex(TagBeanProvider.Columns.type)));
        bean.setName(cursor.getString(cursor.getColumnIndex(TagBeanProvider.Columns.name)));
        bean.setX(cursor.getString(cursor.getColumnIndex(TagBeanProvider.Columns.x)));
        bean.setY(cursor.getString(cursor.getColumnIndex(TagBeanProvider.Columns.y)));
        bean.setRecordCount(cursor.getString(cursor.getColumnIndex(TagBeanProvider.Columns.recordCount)));
        bean.setLeft(cursor.getString(cursor.getColumnIndex(TagBeanProvider.Columns.left)));
        return bean;
    }

    //    {
//        “label”:””, //标签
//        “usage”:, //标签类型
//        “x”:, //横坐标
//        “y”:, //纵坐标
//        “dir”: //标签方向
//    },
    //createFromJSON
    public static TagBean createFromJSON(JSONObject json) throws JSONException {
        if (json == null) return null;
        TagBean bean = new TagBean();
        bean.setId(json.optString("id"));
        bean.setType(json.optString("genre"));
        bean.setName(json.optString("label"));
        bean.setX(json.optString("x"));
        bean.setY(json.optString("y"));
        bean.setRecordCount(json.optString("recordCount"));
        bean.setLeft(json.optString("dir"));
        return bean;
    }

    //createFromJSONArray
    public static ArrayList<TagBean> createFromJSONArray(JSONArray jsonArray) throws JSONException {

        if (jsonArray == null) return null;

        ArrayList<TagBean> list = new ArrayList<TagBean>();

        int count = jsonArray.length();
        for (int i = 0; i < count; i++) {
            JSONObject jsonObj = jsonArray.optJSONObject(i);
            TagBean entity = TagBean.createFromJSON(jsonObj);
            list.add(entity);
        }
        return list;
    }

    //buildContentValues
    public static ContentValues buildContentValues(BaseBean baseBean) {
        TagBean bean = new TagBean();

        if (baseBean != null) {
            bean = (TagBean) baseBean;
        }
        ContentValues values = new ContentValues();
        values.put(TagBeanProvider.Columns.id, bean.getId());
        values.put(TagBeanProvider.Columns.type, bean.getType());
        values.put(TagBeanProvider.Columns.name, bean.getName());
        values.put(TagBeanProvider.Columns.x, bean.getX());
        values.put(TagBeanProvider.Columns.y, bean.getY());
        values.put(TagBeanProvider.Columns.recordCount, bean.getRecordCount());
        values.put(TagBeanProvider.Columns.left, bean.getLeft());
        return values;
    }

    public static TagItem Convert2TagItem(TagBean bean) {
        TagItem tagItem = new TagItem();
        try {
            tagItem.setLeft(bean.getLeft().equals("0"));
            tagItem.setName(bean.getName());
//        tagItem.setRecordCount(Integer.parseInt(bean.getRecordCount()));
            tagItem.setType(Integer.parseInt(bean.getType()));
            tagItem.setX(Integer.parseInt(bean.getX()));
            tagItem.setY(Integer.parseInt(bean.getY()));
            tagItem.setId(Long.parseLong(bean.getId()));
        } catch (Exception e) {
            L.i("Convert2TagItem", e.getMessage());
        }
        return tagItem;

    }

    public static TagBean createFromTagItem(TagItem bean) {
        TagBean tagBean = new TagBean();
        tagBean.setId(bean.getId() + "");
        tagBean.setLeft(bean.isLeft() ? "0" : "1");
        tagBean.setName(bean.getName());
        tagBean.setRecordCount(bean.getRecordCount() + "");
        tagBean.setType(bean.getType() + "");
        tagBean.setX((int) bean.getX() + "");
        tagBean.setY((int) bean.getY() + "");
        return tagBean;
    }


}