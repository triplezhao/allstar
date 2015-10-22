package com.potato.sticker.main.data.request;

import com.potato.chips.base.BaseRequestBuilder;
import com.potato.library.net.DefaultRequest;
import com.potato.library.net.Request;
import com.potato.sticker.main.data.bean.PicBean;
import com.potato.sticker.main.data.bean.TagBean;
import com.potato.sticker.main.data.bean.TopicBean;
import com.potato.sticker.main.data.bean.UserBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.PlatformDb;

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
    public static Request login(String uid, String nickname) {
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
    //登录，只传递一个uid就ok。第三方的openid
    public static Request login( PlatformDb platformDb) {
        Request request = new DefaultRequest();
        request.reqMethod = Request.REQ_METHOD_POST;
        request.url = StickerRequestUrls.USER;
//        request.params = new HashMap<String, Object>();
//        request.params.put("uid", uid);

        JSONObject body = new JSONObject();
        try {
            body.put("uid", platformDb.getUserId());
            body.put("nickname", platformDb.getUserName());
            body.put("headImg", URLEncoder.encode(platformDb.getUserIcon()));
            body.put("sex", platformDb.getUserGender().equals("m")?"0":"1");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.body = body.toString();
        return request;
    }

    //跟登录用一个接口，与修改个人资料也是一个接口。 如果登录后返回没有绑定手机号，则调用这个进行注册流程。
    public static Request updataUser(UserBean bean) {
        Request request = new DefaultRequest();
        request.reqMethod = Request.REQ_METHOD_POST;
        request.url = StickerRequestUrls.USER;
//        request.params = new HashMap<String, Object>();

        JSONObject body = new JSONObject();
        try {
            body.put("uid", bean.getUid());
            body.put("headImg", bean.getProvince());
            body.put("nickname", bean.getNickname());
            body.put("sex", bean.getSex());
            body.put("description", bean.getDescription());
            body.put("phone", bean.getPhone());
            body.put("country", bean.getCountry());
            body.put("province", bean.getProvince());
            body.put("city", bean.getCity());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.body = body.toString();
        return request;

    }


    //获取用户发帖列表
    public static Request topic(String uid, String page, String size) {
        Request request = new DefaultRequest();
        request.reqMethod = Request.REQ_METHOD_POST;
        request.url = getRealRequestUrl(StickerRequestUrls.TOPIC,new String[]{uid});

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
    //获取所有帖子列表
    public static Request allTopic(String title, String page, String size) {
        Request request = new DefaultRequest();
        request.reqMethod = Request.REQ_METHOD_POST;
        request.url = StickerRequestUrls.ALLTOPIC;

        JSONObject body = new JSONObject();
        try {
            body.put("page", page);
            body.put("size", size);
            body.put("title", title);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.body = body.toString();

        return request;
    }

    //发帖子
    /*
    * {
      “picLabel”:{ //多组图片地址:图片标签列表
               “picUrl1”:[
                        {
                        “label”:””, //标签
                        “usage”:, //标签类型
                        “x”:, //横坐标
                        “y”:, //纵坐标
                        “dir”: //标签方向
                        },
                        ……
                        ],
                         ……
                  },
        “topic”:{
               “userId”:, //用户id
               “title”:””, //帖子标题
               “content”:”” //帖子内容
        }
       }
    */
    public static Request create(TopicBean bean) {
        Request request = new DefaultRequest();
        request.reqMethod = Request.REQ_METHOD_POST;
        request.url = StickerRequestUrls.CREATE;
        JSONObject body = new JSONObject();
        try {

            JSONObject topic = new JSONObject();

            topic.put("userId", bean.getUserId());
            topic.put("title", bean.getTitle());
            topic.put("content", bean.getContent());

            ArrayList<PicBean> picBeans = bean.picBeans;
            JSONObject picLabel = new JSONObject();
            for (PicBean pic : picBeans) {
                JSONArray tagArray = new JSONArray();
                for (TagBean tagBean : pic.getTagBeans()) {
                    JSONObject tagobj = new JSONObject();
                    tagobj.put("genre", tagBean.getType());
                    tagobj.put("label", tagBean.getName());
                    tagobj.put("x", tagBean.getX());
                    tagobj.put("y", tagBean.getY());
                    tagobj.put("dir", tagBean.getLeft());
                    tagArray.put(tagobj);
                }
                picLabel.put(pic.getImgPath(), tagArray);
            }

            body.put("topic", topic);
            body.put("picLabel", picLabel);

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
