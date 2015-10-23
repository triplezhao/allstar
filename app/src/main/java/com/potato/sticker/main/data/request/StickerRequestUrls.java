package com.potato.sticker.main.data.request;

import com.potato.chips.base.BaseRequestUrls;

/**
 * 这里通常放置一些常量
 * 
 */
public class StickerRequestUrls extends BaseRequestUrls{

	//public final static String REMOTE_SERVICE_SERVER = "http://115.231.183.102:9090";

	public final static String UPTOKEN = BaseStickerURL + "/support/qiniu/upToken";

	//用户
	public final static String USER = BaseStickerURL + "/user";

	//评论
	public final static String COMMENTLIST = BaseStickerURL + "/content/topic/{0}/comment";
	public final static String COMMENT = BaseStickerURL + "/content/topic/comment";

	//相册
	public final static String USERPIC = BaseStickerURL + "/user/{0}/pic";

	//帖子接口地址
	public final static String CREATE = BaseStickerURL + "/content/topic/create";
	public final static String USERTOPIC = BaseStickerURL + "/user/{0}/topic";
	public final static String ALLTOPIC = BaseStickerURL + "/content/topic";
	public final static String TOPIC_DETAIL = BaseStickerURL + "/content/topic/{0}";




}
