package com.potato.sticker.camera.data.request;

import com.potato.chips.base.BaseRequestBuilder;
import com.potato.library.net.DefaultRequest;
import com.potato.library.net.Request;

import java.util.HashMap;

public class QiniuRequestBuilder extends BaseRequestBuilder {

    /**
     * 获取一次上传用的token。 是否能一次获取，永久使用，暂时不清楚
     */
    public static Request getToken_simple_upload_with_key_path() {
        Request request = new DefaultRequest();
        request.reqMethod = Request.REQ_METHOD_GET;
        request.url = QiniuRequestUrls.SIMPLE_UPLOAD_WITH_KEY_PATH;
        request.params = new HashMap<String, Object>();
        return request;
    }

}
