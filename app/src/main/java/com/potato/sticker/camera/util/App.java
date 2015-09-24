package com.potato.sticker.camera.util;

import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.potato.chips.app.MainApplication;

/**
 * Created by sky on 2015/7/6.
 */
public class App {

    protected static App mInstance;
    private DisplayMetrics displayMetrics = null;

    public App() {
        mInstance = this;
    }

    public static App getApp() {
        if (mInstance != null && mInstance instanceof App) {
            return (App) mInstance;
        } else {
            mInstance = new App();
            return (App) mInstance;
        }
    }


    public float getScreenDensity() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(MainApplication.displayMetrices);
        }
        return this.displayMetrics.density;
    }

    public int getScreenHeight() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(MainApplication.displayMetrices);
        }
        return this.displayMetrics.heightPixels;
    }

    public int getScreenWidth() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(MainApplication.displayMetrices);
        }
        return this.displayMetrics.widthPixels;
    }

    public void setDisplayMetrics(DisplayMetrics DisplayMetrics) {
        this.displayMetrics = DisplayMetrics;
    }

    public int dp2px(float f) {
        return (int) (0.5F + f * getScreenDensity());
    }

    public int px2dp(float pxValue) {
        return (int) (pxValue / getScreenDensity() + 0.5f);
    }

    //获取应用的data/data/....File目录
    public String getFilesDirPath() {
        return MainApplication.context.getFilesDir().getAbsolutePath();
    }

    //获取应用的data/data/....Cache目录
    public String getCacheDirPath() {
        return MainApplication.context.getCacheDir().getAbsolutePath();
    }

    public AssetManager getAssets() {
        return MainApplication.context.getAssets();
    }

    public ContentResolver getContentResolver() {
        return MainApplication.context.getContentResolver();
    }
    public Resources getResources() {
        return MainApplication.context.getResources();
    }

}
