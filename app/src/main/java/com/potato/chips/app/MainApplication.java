package com.potato.chips.app;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.loopj.android.http.AsyncHttpClient;
import com.potato.chips.common.ChannelUtil;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.PhoneUtils;
import com.potato.chips.util.QiniuUtil;
import com.potato.library.net.RequestHttpClient;
import com.potato.library.net.RequestManager;
import com.tendcloud.tenddata.TCAgent;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by zhaobingfeng on 14-12-22.
 */
public class MainApplication extends Application {
    /**
     * 获取屏幕的宽和高
     */
    public static int screenHight = 0;
    public static int screenWidth = 0;
    public static DisplayMetrics displayMetrices;
    /**
     * 设备的 IMEI
     */
    public static String IMEI = "";
    /**
     * 获取全局的上下文
     */
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        init();
    }

    /**
     */
    private void init() {
        context = getApplicationContext();

        initDeviceWidthAndHeight();

        ShareSDK.initSDK(context);
        //获取imei
        PhoneUtils.getIMEI(context);
        //请求缓存管理
        RequestManager.init(context);
        //请求初始化
        AsyncHttpClient instence = RequestHttpClient.getInstence(context);
//        RequestConfig.addHttpClientRASHead(instence);
//        instence.setUserAgent(PhoneUtils.getDeviceUA(context));
        initPicasso();
        initUIL();
        QiniuUtil.init(context);
        initTD();
    }


    private void initPicasso() {
//            final String imageCacheDir = /* 自定义目录 */ +"image";
//        Picasso picasso = new Picasso.Builder(context).memoryCache(
//                new LruCache(1024*1024*20)).downloader();
//            Picasso picasso = new Picasso.Builder(this).downloader(new OkHttpDownloader(new File(imageCacheDir))).build();
//            Picasso.setSingletonInstance(picasso);
    }

    public void initUIL() {
        ImageLoaderUtil.init(context);
    }

    /**
     * 获取设备的宽和高 WangQing 2013-8-12 void
     */
    private void initDeviceWidthAndHeight() {
        displayMetrices = PhoneUtils.getAppWidthAndHeight(this);
        screenHight = displayMetrices.heightPixels;
        screenWidth = displayMetrices.widthPixels;
    }

    private void initTD() {
        TCAgent.init(context, "8B0A3B07DBE0F5BC749F49033E01767B", ChannelUtil.getChannel(context));
    }

   /* @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/
}
