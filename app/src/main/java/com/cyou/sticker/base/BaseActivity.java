package com.cyou.sticker.base;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener, Handler.Callback {
    public Context mContext = null;
    public Handler mHandler = null;

    /** extrars */
    /** views */
    /** adapters */
    /** data */
    /**
     * logic
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION);
        mContext = this;
        mHandler = new Handler(this);
//        getExtras();
//        findViews();
//        bindEvent();
//        bindData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean handleMessage(Message message) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //  If null, all callbacks and messages will be removed.
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 获取页面参数
     */
    public abstract void getExtras();

    /**
     * 初始化界面
     */
    public abstract void findViews();

    /**
     * 绑定事件.
     */
    public abstract void bindData();

    /**
     * 绑定数据. UI上的操作事件,更新数据。
     */
    public abstract void bindEvent();

}
