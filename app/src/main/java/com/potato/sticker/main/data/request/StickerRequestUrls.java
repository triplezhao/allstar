package com.potato.sticker.main.data.request;

import com.potato.chips.base.BaseRequestUrls;

/**
 * 这里通常放置一些常量
 * 
 */
public class StickerRequestUrls extends BaseRequestUrls{

	//public final static String REMOTE_SERVICE_SERVER = "http://115.231.183.102:9090";

	public final static String UPTOKEN = BaseStickerURL + "/support/qiniu/upToken";
	public final static String USER = BaseStickerURL + "/user";
	public final static String TOPIC = BaseStickerURL + "/user/{0}/topic";
	public final static String ALLTOPIC = BaseStickerURL + "/content/topic";
	public final static String CREATE = BaseStickerURL + "/content/topic/create";




}
