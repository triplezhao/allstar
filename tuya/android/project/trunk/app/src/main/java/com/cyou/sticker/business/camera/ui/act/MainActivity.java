package com.cyou.sticker.business.camera.ui.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.cyou.sticker.R;
import com.cyou.sticker.base.BaseActivity;
import com.cyou.sticker.base.BaseListAdapter;
import com.cyou.sticker.business.camera.common.DataUtils;
import com.cyou.sticker.business.camera.common.StringUtils;
import com.cyou.sticker.business.camera.data.bean.FeedItem;
import com.cyou.sticker.business.camera.ui.viewbinder.TagViewBinder;
import com.cyou.sticker.business.camera.util.AppConstants;
import com.cyou.sticker.business.camera.util.CameraManager;
import com.cyou.sticker.databinding.ActivityMainCameraBinding;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 主界面
 * Created by sky on 2015/7/20.
 * Weibo: http://weibo.com/2030683111
 * Email: 1132234509@qq.com
 */
public class MainActivity extends BaseActivity {

    private List<FeedItem> feedList;
    private BaseListAdapter mAdapter;

    private ActivityMainCameraBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_main_camera);
        EventBus.getDefault().register(this);
        binding.fab.setOnClickListener(this);


        mAdapter = new BaseListAdapter(mContext, new TagViewBinder());
        binding.list.setAdapter(mAdapter);

        binding.swipeContainer.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest2RefreshList();
            }
        });

        binding.swipeContainer.setEmptyView(binding.emptyView);
        binding.emptyView.setOnClickListener(this);
        binding.swipeContainer.showProgress();


        sendRequest2RefreshList();
    }


    public void sendRequest2RefreshList() {
        String str = DataUtils.getStringPreferences(getBaseContext(), AppConstants.FEED_INFO);
        if (StringUtils.isNotEmpty(str)) {
            onRefreshSucc(str);
        }
    }

    private void onRefreshSucc(String content) {
        feedList = JSON.parseArray(content, FeedItem.class);
        binding.swipeContainer.showSucc();
        mAdapter.setDataList(feedList);
        mAdapter.notifyDataSetChanged();
        binding.swipeContainer.setRefreshing(false);
    }


    public void onEventMainThread(FeedItem feedItem) {
        if (feedList == null) {
            feedList = new ArrayList<FeedItem>();
        }
        feedList.add(0, feedItem);
        DataUtils.setStringPreferences(getBaseContext(), AppConstants.FEED_INFO, JSON.toJSONString(feedList));
        mAdapter.setDataList(feedList);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                CameraManager.getInst().openCamera(mContext);
                break;
        }
    }

    @Override
    public void bindEvent() {

    }

}
