package com.potato.sticker.main.data.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class LoadImgBean implements Serializable {

	//propslist
	private String Name;
	private String Url;
	private String Order;
	private String Ctime;
	private String Id;
	private String Pic;

	//set get list	
	public void setName(String Name){
		this.Name=Name;
	}
	
	public String getName(){
		return this.Name;
	}
	
	public void setUrl(String Url){
		this.Url=Url;
	}
	
	public String getUrl(){
		return this.Url;
	}
	
	public void setOrder(String Order){
		this.Order=Order;
	}
	
	public String getOrder(){
		return this.Order;
	}
	
	public void setCtime(String Ctime){
		this.Ctime=Ctime;
	}
	
	public String getCtime(){
		return this.Ctime;
	}
	
	public void setId(String Id){
		this.Id=Id;
	}
	
	public String getId(){
		return this.Id;
	}
	
	public void setPic(String Pic){
		this.Pic=Pic;
	}
	
	public String getPic(){
		return this.Pic;
	}
	

    //createFromJSON
    public static LoadImgBean  createFromJSON(JSONObject json)throws JSONException {
     if (json == null) return null;
        LoadImgBean bean = new LoadImgBean();
			bean.setName(json.optString("Name"));
			bean.setUrl(json.optString("Url"));
			bean.setOrder(json.optString("Order"));
			bean.setCtime(json.optString("Ctime"));
			bean.setId(json.optString("Id"));
			bean.setPic(json.optString("Pic"));
		return bean;
	}

	//createFromJSONArray
	public static ArrayList<LoadImgBean> createFromJSONArray(JSONArray jsonArray) throws JSONException {

		if (jsonArray == null) return null;

		ArrayList<LoadImgBean> list = new ArrayList<LoadImgBean>();

		int count = jsonArray.length();
		for (int i = 0; i < count; i++) {
			JSONObject jsonObj = jsonArray.optJSONObject(i);
			LoadImgBean entity = LoadImgBean.createFromJSON(jsonObj);
			list.add(entity);
		}
		return list;
	}

}