package com.cyou.sticker.camera.ui.act;

import android.os.Bundle;

import com.cyou.frame.base.BaseActivity;
import com.cyou.sticker.camera.util.CameraManager;


public class CameraBaseActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CameraManager.getInst().addActivity(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CameraManager.getInst().removeActivity(this);
    }

    @Override
    public void getExtras() {

    }

    @Override
    public void findViews() {

    }

    @Override
    public void bindData() {

    }

    @Override
    public void bindEvent() {

    }
}
