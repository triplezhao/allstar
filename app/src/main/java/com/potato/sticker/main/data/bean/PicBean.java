package com.potato.sticker.main.data.bean;

//import

import android.content.ContentValues;
import android.database.Cursor;

import com.potato.chips.base.BaseBean;
import com.potato.sticker.main.data.db.PicBeanProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class PicBean extends BaseBean implements Serializable{

	//propslist
	private String topicId;
	private String id;
	private String userId;
	private String url;
	private String status;

	//set get list	
	public void setTopicId(String topicId){
		this.topicId=topicId;
	}
	
	public String getTopicId(){
		return this.topicId;
	}
	
	public void setId(String id){
		this.id=id;
	}
	
	public String getId(){
		return this.id;
	}
	
	public void setUserId(String userId){
		this.userId=userId;
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	public void setUrl(String url){
		this.url=url;
	}
	
	public String getUrl(){
		return this.url;
	}
	
	public void setStatus(String status){
		this.status=status;
	}
	
	public String getStatus(){
		return this.status;
	}
	

	//other
	//createFromCursor
    public static PicBean  createFromCursor(Cursor cursor){
     if (cursor == null) return null;
        PicBean bean = new PicBean();
			bean.setTopicId(cursor.getString(cursor.getColumnIndex(PicBeanProvider.Columns.topicId)));
			bean.setId(cursor.getString(cursor.getColumnIndex(PicBeanProvider.Columns.id)));
			bean.setUserId(cursor.getString(cursor.getColumnIndex(PicBeanProvider.Columns.userId)));
			bean.setUrl(cursor.getString(cursor.getColumnIndex(PicBeanProvider.Columns.url)));
			bean.setStatus(cursor.getString(cursor.getColumnIndex(PicBeanProvider.Columns.status)));
		return bean;
	}
	
    //createFromJSON
    public static PicBean  createFromJSON(JSONObject json)throws JSONException{
     if (json == null) return null;
        PicBean bean = new PicBean();
			bean.setTopicId(json.optString("topicId"));
			bean.setId(json.optString("id"));
			bean.setUserId(json.optString("userId"));
			bean.setUrl(json.optString("url"));
			bean.setStatus(json.optString("status"));
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
	
    //buildContentValues
    public static ContentValues buildContentValues(BaseBean baseBean) {
        PicBean bean = new PicBean();

        if (baseBean != null) {
            bean = (PicBean) baseBean;
        }
        ContentValues values = new ContentValues();
        values.put(PicBeanProvider.Columns.topicId, bean.getTopicId());
        values.put(PicBeanProvider.Columns.id, bean.getId());
        values.put(PicBeanProvider.Columns.userId, bean.getUserId());
        values.put(PicBeanProvider.Columns.url, bean.getUrl());
        values.put(PicBeanProvider.Columns.status, bean.getStatus());
        return values;
    }

}