package com.potato.sticker.main.ui.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.potato.chips.base.BaseTabHostActivity;
import com.potato.chips.util.UIUtils;
import com.potato.sticker.R;


public class MainTabActivity extends BaseTabHostActivity {

    private long lastBackPressedTime = 0;
    private static final int DoubleBackPressedInterval = 2000;
    public static Activity mainActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        mainActivity = this;
    }

    @Override
    public TabItem getTabItemView(int position) {

        TabItem tabItem = new TabItem();
        View tabItemView = mLayoutflater.inflate(R.layout.item_main_tab, null);
        ImageView iv_icon = (ImageView) tabItemView.findViewById(R.id.iv_icon);
        switch (position) {
            case 0:
                iv_icon.setImageResource(R.drawable.selector_nav_home);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), AllTopicActivity.class));
                break;

            case 1:

                iv_icon.setImageResource(R.drawable.selector_nav_explore);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), ClassifyActivity.class));
                break;
            case 2:

                iv_icon.setImageResource(R.drawable.selector_nav_workout);

                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), PersenalActivity.class));
                break;
            case 3:

                iv_icon.setImageResource(R.drawable.selector_nav_contact);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), MsgActivity.class));
                break;
            case 4:

                iv_icon.setImageResource(R.drawable.selector_nav_profile);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), MyActivity.class));
                break;
        }

        return tabItem;
    }

    @Override
    public int getTabItemCount() {
        return 5;
    }

    @Override
    public void onTabChanged(String s) {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - lastBackPressedTime > DoubleBackPressedInterval) {
                    lastBackPressedTime = currentTimeMillis;
                    UIUtils.toast(getBaseContext(),getResources().getString(R.string.clickAgain2Leave));
                } else {
                    finish();
                }
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
