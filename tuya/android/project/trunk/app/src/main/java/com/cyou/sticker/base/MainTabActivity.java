package com.cyou.sticker.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyou.sticker.R;
import com.cyou.sticker.business.a.ui.act.AActivity;
import com.cyou.sticker.business.a.ui.act.BActivity;
import com.cyou.sticker.business.jiongtu.ui.act.JiongtuActivity;

public class MainTabActivity extends BaseTabHostActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected TabItem getTabItemView(int position) {

        TabItem tabItem = new TabItem();
        View tabItemView = mLayoutflater.inflate(R.layout.item_main_tab, null);
        TextView textView = (TextView) tabItemView.findViewById(R.id.tab_item_tv);
        ImageView iv_icon = (ImageView) tabItemView.findViewById(R.id.iv_icon);
        switch (position) {
            case 0:
                textView.setPadding(3, 3, 3, 3);
                textView.setText("" + position);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_tab_home), null, null);

                tabItem.setIcon(R.drawable.icon_tab_home);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), JiongtuActivity.class));
                break;

            case 1:

                textView.setPadding(3, 3, 3, 3);
                textView.setText("" + position);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.icon_tab_home), null, null);

                tabItem.setIcon(R.drawable.icon_tab_home);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), AActivity.class));
                break;
            case 2:

                textView.setPadding(3, 3, 3, 3);
                textView.setText("" + position);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_launcher), null, null);

                tabItem.setIcon(R.drawable.icon_tab_home);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), AActivity.class));
                break;
            case 3:

                textView.setPadding(3, 3, 3, 3);
                textView.setText("" + position);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_launcher), null, null);

                tabItem.setIcon(R.drawable.icon_tab_home);
                tabItem.setTitle("" + position);
                tabItem.setView(tabItemView);
                tabItem.setIntent(new Intent(getApplication(), BActivity.class));
                break;
        }

        return tabItem;
    }

    @Override
    protected int getTabItemCount() {
        return 4;
    }

    @Override
    public void onTabChanged(String s) {

    }
}
