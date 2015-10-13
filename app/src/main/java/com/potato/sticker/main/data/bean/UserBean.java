package com.potato.sticker.main.data.bean;

//import

import android.content.ContentValues;
import android.database.Cursor;

import com.potato.chips.base.BaseBean;
import com.potato.sticker.main.data.db.UserBeanProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class UserBean extends BaseBean implements Serializable{

	//propslist
	private String country;
	private String headImg;
	private String city;
	private String sex;
	private String description;
	private String focusCount;
	private String fansCount;
	private String uid;
	private String province;
	private String phone;
	private String nickname;
	private String labelCount;
	private String laudCount;
	private String topicCount;
	private String id;
	private String islogined;
	private String createDate;
	private String status;

	//set get list	
	public void setCountry(String country){
		this.country=country;
	}
	
	public String getCountry(){
		return this.country;
	}
	
	public void setHeadImg(String headImg){
		this.headImg=headImg;
	}
	
	public String getHeadImg(){
		return this.headImg;
	}
	
	public void setCity(String city){
		this.city=city;
	}
	
	public String getCity(){
		return this.city;
	}
	
	public void setSex(String sex){
		this.sex=sex;
	}
	
	public String getSex(){
		return this.sex;
	}
	
	public void setDescription(String description){
		this.description=description;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setFocusCount(String focusCount){
		this.focusCount=focusCount;
	}
	
	public String getFocusCount(){
		return this.focusCount;
	}
	
	public void setFansCount(String fansCount){
		this.fansCount=fansCount;
	}
	
	public String getFansCount(){
		return this.fansCount;
	}
	
	public void setUid(String uid){
		this.uid=uid;
	}
	
	public String getUid(){
		return this.uid;
	}
	
	public void setProvince(String province){
		this.province=province;
	}
	
	public String getProvince(){
		return this.province;
	}
	
	public void setPhone(String phone){
		this.phone=phone;
	}
	
	public String getPhone(){
		return this.phone;
	}
	
	public void setNickname(String nickname){
		this.nickname=nickname;
	}
	
	public String getNickname(){
		return this.nickname;
	}
	
	public void setLabelCount(String labelCount){
		this.labelCount=labelCount;
	}
	
	public String getLabelCount(){
		return this.labelCount;
	}
	
	public void setLaudCount(String laudCount){
		this.laudCount=laudCount;
	}
	
	public String getLaudCount(){
		return this.laudCount;
	}
	
	public void setTopicCount(String topicCount){
		this.topicCount=topicCount;
	}
	
	public String getTopicCount(){
		return this.topicCount;
	}
	
	public void setId(String id){
		this.id=id;
	}
	
	public String getId(){
		return this.id;
	}
	
	public void setIslogined(String islogined){
		this.islogined=islogined;
	}
	
	public String getIslogined(){
		return this.islogined;
	}
	
	public void setCreateDate(String createDate){
		this.createDate=createDate;
	}
	
	public String getCreateDate(){
		return this.createDate;
	}
	
	public void setStatus(String status){
		this.status=status;
	}
	
	public String getStatus(){
		return this.status;
	}
	

	//other
	//createFromCursor
    public static UserBean  createFromCursor(Cursor cursor){
     if (cursor == null) return null;
        UserBean bean = new UserBean();
			bean.setCountry(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.country)));
			bean.setHeadImg(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.headImg)));
			bean.setCity(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.city)));
			bean.setSex(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.sex)));
			bean.setDescription(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.description)));
			bean.setFocusCount(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.focusCount)));
			bean.setFansCount(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.fansCount)));
			bean.setUid(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.uid)));
			bean.setProvince(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.province)));
			bean.setPhone(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.phone)));
			bean.setNickname(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.nickname)));
			bean.setLabelCount(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.labelCount)));
			bean.setLaudCount(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.laudCount)));
			bean.setTopicCount(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.topicCount)));
			bean.setId(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.id)));
			bean.setIslogined(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.islogined)));
			bean.setCreateDate(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.createDate)));
			bean.setStatus(cursor.getString(cursor.getColumnIndex(UserBeanProvider.Columns.status)));
		return bean;
	}
	
    //createFromJSON
    public static UserBean  createFromJSON(JSONObject json)throws JSONException{
     if (json == null) return null;
        UserBean bean = new UserBean();
			bean.setCountry(json.optString("country"));
			bean.setHeadImg(json.optString("headImg"));
			bean.setCity(json.optString("city"));
			bean.setSex(json.optString("sex"));
			bean.setDescription(json.optString("description"));
			bean.setFocusCount(json.optString("focusCount"));
			bean.setFansCount(json.optString("fansCount"));
			bean.setUid(json.optString("uid"));
			bean.setProvince(json.optString("province"));
			bean.setPhone(json.optString("phone"));
			bean.setNickname(json.optString("nickname"));
			bean.setLabelCount(json.optString("labelCount"));
			bean.setLaudCount(json.optString("laudCount"));
			bean.setTopicCount(json.optString("topicCount"));
			bean.setId(json.optString("id"));
			bean.setIslogined(json.optString("islogined"));
			bean.setCreateDate(json.optString("createDate"));
			bean.setStatus(json.optString("status"));
		return bean;
	}
	
	//createFromJSONArray
    public static ArrayList<UserBean> createFromJSONArray(JSONArray jsonArray) throws JSONException {

        if (jsonArray == null) return null;

        ArrayList<UserBean> list = new ArrayList<UserBean>();

        int count = jsonArray.length();
        for (int i = 0; i < count; i++) {
            JSONObject jsonObj = jsonArray.optJSONObject(i);
            UserBean entity = UserBean.createFromJSON(jsonObj);
            list.add(entity);
        }
        return list;
    }
	
    //buildContentValues
    public static ContentValues buildContentValues(BaseBean baseBean) {
        UserBean bean = new UserBean();

        if (baseBean != null) {
            bean = (UserBean) baseBean;
        }
        ContentValues values = new ContentValues();
        values.put(UserBeanProvider.Columns.country, bean.getCountry());
        values.put(UserBeanProvider.Columns.headImg, bean.getHeadImg());
        values.put(UserBeanProvider.Columns.city, bean.getCity());
        values.put(UserBeanProvider.Columns.sex, bean.getSex());
        values.put(UserBeanProvider.Columns.description, bean.getDescription());
        values.put(UserBeanProvider.Columns.focusCount, bean.getFocusCount());
        values.put(UserBeanProvider.Columns.fansCount, bean.getFansCount());
        values.put(UserBeanProvider.Columns.uid, bean.getUid());
        values.put(UserBeanProvider.Columns.province, bean.getProvince());
        values.put(UserBeanProvider.Columns.phone, bean.getPhone());
        values.put(UserBeanProvider.Columns.nickname, bean.getNickname());
        values.put(UserBeanProvider.Columns.labelCount, bean.getLabelCount());
        values.put(UserBeanProvider.Columns.laudCount, bean.getLaudCount());
        values.put(UserBeanProvider.Columns.topicCount, bean.getTopicCount());
        values.put(UserBeanProvider.Columns.id, bean.getId());
        values.put(UserBeanProvider.Columns.islogined, bean.getIslogined());
        values.put(UserBeanProvider.Columns.createDate, bean.getCreateDate());
        values.put(UserBeanProvider.Columns.status, bean.getStatus());
        return values;
    }

}