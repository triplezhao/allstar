package com.potato.sticker.main.data.bean;

//import

import android.content.ContentValues;
import android.database.Cursor;

import com.potato.chips.base.BaseBean;
import com.potato.sticker.main.data.db.MsgBeanProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MsgBean extends BaseBean implements Serializable{

	//propslist
	private String id;
	private String type;
	private String userId;
	private String content;
	private String createDate;

	//set get list	
	public void setId(String id){
		this.id=id;
	}
	
	public String getId(){
		return this.id;
	}
	
	public void setType(String type){
		this.type=type;
	}
	
	public String getType(){
		return this.type;
	}
	
	public void setUserId(String userId){
		this.userId=userId;
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	public void setContent(String content){
		this.content=content;
	}
	
	public String getContent(){
		return this.content;
	}
	
	public void setCreateDate(String createDate){
		this.createDate=createDate;
	}
	
	public String getCreateDate(){
		return this.createDate;
	}
	

	//other
	//createFromCursor
    public static MsgBean  createFromCursor(Cursor cursor){
     if (cursor == null) return null;
        MsgBean bean = new MsgBean();
			bean.setId(cursor.getString(cursor.getColumnIndex(MsgBeanProvider.Columns.id)));
			bean.setType(cursor.getString(cursor.getColumnIndex(MsgBeanProvider.Columns.type)));
			bean.setUserId(cursor.getString(cursor.getColumnIndex(MsgBeanProvider.Columns.userId)));
			bean.setContent(cursor.getString(cursor.getColumnIndex(MsgBeanProvider.Columns.content)));
			bean.setCreateDate(cursor.getString(cursor.getColumnIndex(MsgBeanProvider.Columns.createDate)));
		return bean;
	}
	
    //createFromJSON
    public static MsgBean  createFromJSON(JSONObject json)throws JSONException{
     if (json == null) return null;
        MsgBean bean = new MsgBean();
			bean.setId(json.optString("id"));
			bean.setType(json.optString("type"));
			bean.setUserId(json.optString("userId"));
			bean.setContent(json.optString("content"));
			bean.setCreateDate(json.optString("createDate"));
		return bean;
	}
	
	//createFromJSONArray
    public static ArrayList<MsgBean> createFromJSONArray(JSONArray jsonArray) throws JSONException {

        if (jsonArray == null) return null;

        ArrayList<MsgBean> list = new ArrayList<MsgBean>();

        int count = jsonArray.length();
        for (int i = 0; i < count; i++) {
            JSONObject jsonObj = jsonArray.optJSONObject(i);
            MsgBean entity = MsgBean.createFromJSON(jsonObj);
            list.add(entity);
        }
        return list;
    }
	
    //buildContentValues
    public static ContentValues buildContentValues(BaseBean baseBean) {
        MsgBean bean = new MsgBean();

        if (baseBean != null) {
            bean = (MsgBean) baseBean;
        }
        ContentValues values = new ContentValues();
        values.put(MsgBeanProvider.Columns.id, bean.getId());
        values.put(MsgBeanProvider.Columns.type, bean.getType());
        values.put(MsgBeanProvider.Columns.userId, bean.getUserId());
        values.put(MsgBeanProvider.Columns.content, bean.getContent());
        values.put(MsgBeanProvider.Columns.createDate, bean.getCreateDate());
        return values;
    }

}