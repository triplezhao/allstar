package com.potato.sticker.main.data.request;

import com.potato.chips.base.BaseRequestUrls;

/**
 * 这里通常放置一些常量
 */
public class StickerRequestUrls extends BaseRequestUrls {

    //public final static String REMOTE_SERVICE_SERVER = "http://115.231.183.102:9090";

    public final static String UPTOKEN = BaseStickerURL + "/support/qiniu/upToken";

    //第三方登录获取用户基本信息后调用，初次登录新建本地用户并返回用户对象，否则保存变更信息并返回用户对象及用户标签。
    public final static String USER = BaseStickerURL + "/user";
    //分页获取帖子评论列表。
    public final static String COMMENTLIST = BaseStickerURL + "/content/topic/{0}/comment";
    //用户对某帖子进行评论。
    public final static String COMMENT = BaseStickerURL + "/content/topic/comment";
    //相册
    public final static String USERPIC = BaseStickerURL + "/user/{0}/pic";
    //帖子接口地址
    public final static String CREATE = BaseStickerURL + "/content/topic/create";
    //分页获取用户帖子列表。
    public final static String USERTOPIC = BaseStickerURL + "/user/{0}/topic";
    //分页获取不限定发帖人的全部帖子列表
    public final static String ALLTOPIC = BaseStickerURL + "/content/topic";
    //获取帖子详情和帖子图片及图片对应标签。
    public final static String TOPIC_DETAIL = BaseStickerURL + "/content/topic/{0}";
    //十八．	获取分类列表接口
    public final static String TOPIC_CLASSIFY = BaseStickerURL + "/support/classify";
    //十九．	获取分类下帖子列表接口
    public final static String TOPIC_CLASSIFY_RELA = BaseStickerURL + "/classify/{0}/rela";
    //用户对某帖子点赞。
    public final static String TOPIC_LAUD = BaseStickerURL + "/content/topic/laud";
    //用户关注其他用户。
    public final static String FOCUS = BaseStickerURL + "/user/focus";
    //用户取消关注
    public final static String UNFOCUS = BaseStickerURL + "/user/{0}/focus/{1}";

}
