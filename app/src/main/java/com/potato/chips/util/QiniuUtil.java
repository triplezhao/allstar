package com.potato.chips.util;

import android.content.Context;
import android.text.TextUtils;

import com.potato.library.net.PotatoRequest;
import com.potato.library.net.PotatoRequestManager;
import com.potato.sticker.main.data.parser.TokenParser;
import com.potato.sticker.main.data.request.StickerRequestBuilder;
import com.qiniu.android.storage.UploadManager;

/**
 * Created by ztw on 2015/9/30.
 */
public class QiniuUtil {

    public static String def_token = "";

    public static UploadManager uploadManager;

    public static void init(final Context context) {
        uploadManager = new UploadManager();

        PotatoRequest request = StickerRequestBuilder.getUpToken();
        PotatoRequestManager.requestData(request, new PotatoRequestManager.DataLoadListener() {

            @Override
            public void onCacheLoaded(String content) {

            }

            @Override
            public void onSuccess(int statusCode, String content) {
                if (TextUtils.isEmpty(content)) {
                    UIUtils.toast(context, "token fail");
                    return;
                }
                TokenParser parser = new TokenParser(content);
                if (parser.isSucc()) {
                    def_token = parser.token;
                    if (TextUtils.isEmpty(def_token)) {
                        UIUtils.toast(context, content);
                    }
                } else {
                    UIUtils.toast(context, content);
                }
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {

            }
        }, PotatoRequestManager.CACHE_TYPE_NOCACHE);

    }

    public String getPicName(String uid, String extfilename) {

        String time = System.currentTimeMillis()+"";

        return uid + "_" + time + extfilename;
    }


}
