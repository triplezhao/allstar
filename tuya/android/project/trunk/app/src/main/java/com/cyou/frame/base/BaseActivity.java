package com.cyou.frame.base;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.cyou.model.library.view.dialog.DialogUtil;
import com.cyou.sticker.camera.util.DialogHelper;
import com.cyou.frame.util.UIUtils;

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
    /**
     * 对话框帮助类
     */
    public DialogHelper mDialogHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION);
        mContext = this;
        mHandler = new Handler(this);
        mDialogHelper = new DialogHelper(this);
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


    /**
     * TOAST
     *
     * @param msg
     *            消息
     * @param period
     *            时长
     */
    public void toast(String msg, int period) {
        UIUtils.toast(mContext, msg, period);
    }

    /**
     * 显示进度对话框
     *
     * @param msg
     *            消息
     */
    public void showProgressDialog(String msg) {
        DialogUtil.createProgressDialog(mContext);
    }


    public void dismissProgressDialog() {
        mDialogHelper.dismissProgressDialog();
    }
}
