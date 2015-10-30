package com.potato.sticker.main.data.bean;

//import

import android.content.ContentValues;
import android.database.Cursor;

import com.potato.chips.base.BaseBean;
import com.potato.sticker.main.data.db.ClassifyBeanProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassifyBean extends BaseBean implements Serializable{

	//propslist
	private String level;
	private String name;
	private String orderNum;
	private String optional;
	private String id;
	private String parentId;
	private String status;

	//set get list	
	public void setLevel(String level){
		this.level=level;
	}
	
	public String getLevel(){
		return this.level;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setOrderNum(String orderNum){
		this.orderNum=orderNum;
	}
	
	public String getOrderNum(){
		return this.orderNum;
	}
	
	public void setOptional(String optional){
		this.optional=optional;
	}
	
	public String getOptional(){
		return this.optional;
	}
	
	public void setId(String id){
		this.id=id;
	}
	
	public String getId(){
		return this.id;
	}
	
	public void setParentId(String parentId){
		this.parentId=parentId;
	}
	
	public String getParentId(){
		return this.parentId;
	}
	
	public void setStatus(String status){
		this.status=status;
	}
	
	public String getStatus(){
		return this.status;
	}
	

	//other
	//createFromCursor
    public static ClassifyBean  createFromCursor(Cursor cursor){
     if (cursor == null) return null;
        ClassifyBean bean = new ClassifyBean();
			bean.setLevel(cursor.getString(cursor.getColumnIndex(ClassifyBeanProvider.Columns.level)));
			bean.setName(cursor.getString(cursor.getColumnIndex(ClassifyBeanProvider.Columns.name)));
			bean.setOrderNum(cursor.getString(cursor.getColumnIndex(ClassifyBeanProvider.Columns.orderNum)));
			bean.setOptional(cursor.getString(cursor.getColumnIndex(ClassifyBeanProvider.Columns.optional)));
			bean.setId(cursor.getString(cursor.getColumnIndex(ClassifyBeanProvider.Columns.id)));
			bean.setParentId(cursor.getString(cursor.getColumnIndex(ClassifyBeanProvider.Columns.parentId)));
			bean.setStatus(cursor.getString(cursor.getColumnIndex(ClassifyBeanProvider.Columns.status)));
		return bean;
	}
	
    //createFromJSON
    public static ClassifyBean  createFromJSON(JSONObject json)throws JSONException{
     if (json == null) return null;
        ClassifyBean bean = new ClassifyBean();
			bean.setLevel(json.optString("level"));
			bean.setName(json.optString("name"));
			bean.setOrderNum(json.optString("orderNum"));
			bean.setOptional(json.optString("optional"));
			bean.setId(json.optString("id"));
			bean.setParentId(json.optString("parentId"));
			bean.setStatus(json.optString("status"));
		return bean;
	}
	
	//createFromJSONArray
    public static ArrayList<ClassifyBean> createFromJSONArray(JSONArray jsonArray) throws JSONException {

        if (jsonArray == null) return null;

        ArrayList<ClassifyBean> list = new ArrayList<ClassifyBean>();

        int count = jsonArray.length();
        for (int i = 0; i < count; i++) {
            JSONObject jsonObj = jsonArray.optJSONObject(i);
            ClassifyBean entity = ClassifyBean.createFromJSON(jsonObj);
            list.add(entity);
        }
        return list;
    }
	
    //buildContentValues
    public static ContentValues buildContentValues(BaseBean baseBean) {
        ClassifyBean bean = new ClassifyBean();

        if (baseBean != null) {
            bean = (ClassifyBean) baseBean;
        }
        ContentValues values = new ContentValues();
        values.put(ClassifyBeanProvider.Columns.level, bean.getLevel());
        values.put(ClassifyBeanProvider.Columns.name, bean.getName());
        values.put(ClassifyBeanProvider.Columns.orderNum, bean.getOrderNum());
        values.put(ClassifyBeanProvider.Columns.optional, bean.getOptional());
        values.put(ClassifyBeanProvider.Columns.id, bean.getId());
        values.put(ClassifyBeanProvider.Columns.parentId, bean.getParentId());
        values.put(ClassifyBeanProvider.Columns.status, bean.getStatus());
        return values;
    }

}