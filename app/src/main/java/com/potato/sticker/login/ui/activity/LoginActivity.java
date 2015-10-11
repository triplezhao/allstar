package com.potato.sticker.login.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.potato.chips.base.BaseActivity;
import com.potato.sticker.login.ui.fragment.LoginFragment;


public class LoginActivity extends BaseActivity {
    
    public static boolean isForget_pwd = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isForget_pwd = false;
        setContentView(R.layout.activity_login_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // 然后，你能够使用add()方法把Fragment添加到指定的视图中，如：

        LoginFragment fragment = new LoginFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }

}
