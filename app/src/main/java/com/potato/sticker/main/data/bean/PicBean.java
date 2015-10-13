package com.potato.sticker.main.data.bean;

//import

import com.potato.chips.base.BaseBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class PicBean extends BaseBean implements Serializable {

    //propslist
    private String imgPath;
    ArrayList<TagBean> tagBeans;

    //set get list

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public ArrayList<TagBean> getTagBeans() {
        return tagBeans;
    }

    public void setTagBeans(ArrayList<TagBean> tagBeans) {
        this.tagBeans = tagBeans;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    //other
    //createFromCursor
    /*public static PicBean createFromCursor(Cursor cursor) {
        if (cursor == null) return null;
        PicBean bean = new PicBean();
        bean.setImgPath(cursor.getString(cursor.getColumnIndex(PicBeanProvider.Columns.imgPath)));
        bean.setTagBeans(cursor.getString(cursor.getColumnIndex(PicBeanProvider.Columns.tagList)));
        return bean;
    }*/

    //createFromJSON
    public static PicBean createFromJSON(JSONObject json) throws JSONException {
        if (json == null) return null;
        PicBean bean = new PicBean();
        bean.setImgPath(json.optString("picurl"));
        bean.setTagBeans(TagBean.createFromJSONArray(json.optJSONArray("tagList")));
        return bean;
    }

    //createFromJSONArray
    public static ArrayList<PicBean> createFromJSONArray(JSONArray jsonArray) throws JSONException {

        if (jsonArray == null) return null;

        ArrayList<PicBean> list = new ArrayList<PicBean>();

        int count = jsonArray.length();
        for (int i = 0; i < count; i++) {
            JSONObject jsonObj = jsonArray.optJSONObject(i);
            PicBean entity = PicBean.createFromJSON(jsonObj);
            list.add(entity);
        }
        return list;
    }

    /*//buildContentValues
    public static ContentValues buildContentValues(BaseBean baseBean) {
        PicBean bean = new PicBean();

        if (baseBean != null) {
            bean = (PicBean) baseBean;
        }
        ContentValues values = new ContentValues();
        values.put(PicBeanProvider.Columns.imgPath, bean.getImgPath());
        values.put(PicBeanProvider.Columns.tagList, bean.getTagList());
        return values;
    }*/

}