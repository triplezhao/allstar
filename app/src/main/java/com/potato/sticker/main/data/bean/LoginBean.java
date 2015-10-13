package com.potato.sticker.main.data.bean;

//import

import com.potato.chips.base.BaseBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginBean extends BaseBean implements Serializable {

    //propslist
    public String uid;
    public String country;
    public String headImg;
    public String province;
    public String city;
    public String phone;
    public String sex;
    public String nickname;
    public String description;

    //set get list
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return this.uid;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return this.country;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getHeadImg() {
        return this.headImg;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return this.province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return this.sex;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }


    //createFromJSON
    public static LoginBean createFromJSON(JSONObject json) throws JSONException {
        if (json == null) return null;
        LoginBean bean = new LoginBean();
        bean.setUid(json.optString("uid"));
        bean.setCountry(json.optString("country"));
        bean.setHeadImg(json.optString("headImg"));
        bean.setProvince(json.optString("province"));
        bean.setCity(json.optString("city"));
        bean.setPhone(json.optString("phone"));
        bean.setSex(json.optString("sex"));
        bean.setNickname(json.optString("nickname"));
        bean.setDescription(json.optString("description"));
        return bean;
    }

    //createFromJSONArray
    public static ArrayList<LoginBean> createFromJSONArray(JSONArray jsonArray) throws JSONException {

        if (jsonArray == null) return null;

        ArrayList<LoginBean> list = new ArrayList<LoginBean>();

        int count = jsonArray.length();
        for (int i = 0; i < count; i++) {
            JSONObject jsonObj = jsonArray.optJSONObject(i);
            LoginBean entity = LoginBean.createFromJSON(jsonObj);
            list.add(entity);
        }
        return list;
    }

}