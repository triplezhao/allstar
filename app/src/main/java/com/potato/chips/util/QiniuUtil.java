package com.potato.chips.util;

import android.content.Context;
import android.text.TextUtils;

import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.sticker.camera.data.parser.QiniuTokenParser;
import com.potato.sticker.camera.data.request.QiniuRequestBuilder;
import com.qiniu.android.storage.UploadManager;

/**
 * Created by ztw on 2015/9/30.
 */
public class QiniuUtil {

    public static String def_token = "";

    public static UploadManager uploadManager;

    public static void init(final Context context) {
        uploadManager = new UploadManager();

        Request request = QiniuRequestBuilder.getToken_simple_upload_with_key_path();
        RequestManager.requestData(request,new RequestManager.DataLoadListener(){

            @Override
            public void onCacheLoaded(String content) {

            }

            @Override
            public void onSuccess(int statusCode, String content) {
                if(TextUtils.isEmpty(content)){
                    UIUtils.toast(context,"token fail");
                    return;
                }
                QiniuTokenParser parser =  new QiniuTokenParser(content);
                if(parser.isSucc()){
                    def_token= parser.token;
                    if(TextUtils.isEmpty(def_token)){
                        UIUtils.toast(context,content);
                    }
                }else{
                    UIUtils.toast(context,content);
                }
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {

            }
        },RequestManager.CACHE_TYPE_NOCACHE);

    }

}
