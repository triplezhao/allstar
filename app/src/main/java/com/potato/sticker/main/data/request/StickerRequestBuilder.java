package com.potato.sticker.main.data.request;

import com.potato.chips.base.BaseRequestBuilder;
import com.potato.library.net.DefaultRequest;
import com.potato.library.net.Request;
import com.potato.sticker.main.data.bean.LoginBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class StickerRequestBuilder extends BaseRequestBuilder {

    /**
     * 获取一次上传用的token。 是否能一次获取，永久使用，暂时不清楚
     */
    public static Request getUpToken() {
        Request request = new DefaultRequest();
        request.reqMethod = Request.REQ_METHOD_GET;
        request.url = StickerRequestUrls.UPTOKEN;
        request.params = new HashMap<String, Object>();
        return request;
    }
    //登录，只传递一个uid就ok。第三方的openid
    public static Request login(String uid,String nickname) {
        Request request = new DefaultRequest();
        request.reqMethod = Request.REQ_METHOD_POST;
        request.url = StickerRequestUrls.USER;
//        request.params = new HashMap<String, Object>();
//        request.params.put("uid", uid);

        JSONObject body = new JSONObject();
        try {
            body.put("uid", uid);
            body.put("nickname", nickname);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.body = body.toString();
        return request;
    }

    //跟登录用一个接口，与修改个人资料也是一个接口。 如果登录后返回没有绑定手机号，则调用这个进行注册流程。
    public static Request register(LoginBean bean) {
        Request request = new DefaultRequest();
        request.reqMethod = Request.REQ_METHOD_POST;
        request.url = StickerRequestUrls.USER;
//        request.params = new HashMap<String, Object>();

        JSONObject body = new JSONObject();
        try {
            body.put("uid", bean.uid);
            body.put("headImg", bean.province);
            body.put("nickname", bean.nickname);
            body.put("sex", bean.sex);
            body.put("description", bean.description);
            body.put("phone", bean.phone);
            body.put("country", bean.country);
            body.put("province", bean.province);
            body.put("city", bean.city);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.body = body.toString();
        return request;

    }


    //获取用户发帖列表
    public static Request topic(String uid,String page,String size) {
        Request request = new DefaultRequest();
        request.reqMethod = Request.REQ_METHOD_POST;
        request.url = StickerRequestUrls.TOPIC;

        JSONObject body = new JSONObject();
        try {
            body.put("page", page);
            body.put("size", size);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.body = body.toString();

        return request;
    }

    //发帖子
    public static Request create(String uid,String title,String content,String json/*lable array*/) {
        Request request = new DefaultRequest();
        request.reqMethod = Request.REQ_METHOD_POST;
        request.url = StickerRequestUrls.CREATE;

        JSONObject body = new JSONObject();
        try {
            body.put("uid", uid);
            body.put("title", title);
            body.put("content", content);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.body = body.toString();
        return request;
    }

    public static Request getLoadImgRequest() {
        Request request = new DefaultRequest();
        request.reqMethod = Request.REQ_METHOD_GET;
        request.url = StickerRequestUrls.UPTOKEN;
        request.params = new HashMap<String, Object>();
        return request;
    }
}
