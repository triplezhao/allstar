package com.potato.sticker.camera.ui.act;

import android.os.Bundle;

import com.potato.chips.base.BaseActivity;
import com.potato.sticker.camera.util.CameraManager;


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

}
