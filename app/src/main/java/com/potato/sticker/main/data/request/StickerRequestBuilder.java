package com.potato.sticker.main.data.request;

import com.potato.chips.base.BaseRequestBuilder;
import com.potato.library.net.DefaultRequest;
import com.potato.library.net.Request;
import com.potato.sticker.main.data.bean.LoginBean;
import com.potato.sticker.main.data.bean.PicBean;
import com.potato.sticker.main.data.bean.TagBean;
import com.potato.sticker.main.data.bean.TopicBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
            body.put("sex", "0");
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

            topic.put("uid", bean.getUserId());
            topic.put("title", bean.getTitle());
            topic.put("content", bean.getContent());

            ArrayList<PicBean> picBeans = bean.picBeans;
            for (PicBean pic : picBeans) {
                JSONObject picobj = new JSONObject();
                JSONArray tagArray = new JSONArray();
                for (TagBean tagBean : pic.getTagBeans()) {
                    JSONObject tagobj = new JSONObject();
                    topic.put("usage", tagBean.getType());
                    topic.put("title", tagBean.getName());
                    topic.put("x", tagBean.getX());
                    topic.put("y", tagBean.getY());
                    topic.put("dir", tagBean.getLeft());
                    tagArray.put(tagobj);
                }
                picobj.put(pic.getImgPath(), tagArray);
            }

            body.put("topic", topic);
            body.put("picLabel", picBeans);

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
