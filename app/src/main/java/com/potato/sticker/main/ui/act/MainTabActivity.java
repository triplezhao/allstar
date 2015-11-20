package com.potato.sticker.main.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.potato.chips.base.BaseTabHostActivity;
import com.potato.sticker.R;


public class MainTabActivity extends BaseTabHostActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
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
                tabItem.setIntent(new Intent(getApplication(), HomeActivity.class));
                break;

            case 1:

                iv_icon.setImageResource(R.drawable.selector_nav_explore);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), PersenalActivity.class));
                break;
            /*case 2:

                iv_icon.setImageResource(R.drawable.selector_nav_workout);

                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), UserTopicActivity.class));
                break;*/
            case 2:

                iv_icon.setImageResource(R.drawable.selector_nav_contact);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), MsgActivity.class));
                break;
            case 3:

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
        return 4;
    }

    @Override
    public void onTabChanged(String s) {

    }
}
