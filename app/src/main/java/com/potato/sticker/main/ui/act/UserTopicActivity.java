package com.potato.sticker.main.ui.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;

import com.potato.chips.base.BaseActivity;
import com.potato.chips.util.UIUtils;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.sticker.R;
import com.potato.sticker.camera.ui.adapter.TopicAdapter;
import com.potato.sticker.camera.util.CameraManager;
import com.potato.sticker.databinding.ActivityUserTopicBinding;
import com.potato.sticker.main.data.bean.TopicBean;
import com.potato.sticker.main.data.db.DBUtil;
import com.potato.sticker.main.data.parser.TopicListParser;
import com.potato.sticker.main.data.request.StickerRequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 * Created by sky on 2015/7/20.
 * Weibo: http://weibo.com/2030683111
 * Email: 1132234509@qq.com
 */
public class UserTopicActivity extends BaseActivity {

    List<TopicBean> list = new ArrayList<TopicBean>();
    private TopicAdapter mAdapter;
    private int mTotal = 0;
    private int mPage = 0;
    private ActivityUserTopicBinding binding;
    private int mSize = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_user_topic);
        binding.fab.setOnClickListener(this);

        mAdapter = new TopicAdapter(mContext);
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

//        binding.getRoot().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                String str = DataUtils.getStringPreferences(getBaseContext(), AppConstants.FEED_INFO);
//                if (StringUtils.isNotEmpty(str)) {
//                    onRefreshSucc(str);
//                } else {
//                    UIUtils.toast(mContext, "还没有发布过图片");
//                    binding.swipeContainer.showEmptyViewNoContent();
//                }
//            }
//        }, 2000);
        Request request = StickerRequestBuilder.topic(DBUtil.getLoginUser().getId() + "", mPage + "", mSize + "");
        RequestManager.requestData(request, new RequestManager.DataLoadListener() {

            @Override
            public void onCacheLoaded(String content) {
                if (TextUtils.isEmpty(content)) {
                    UIUtils.toast(mContext, "token fail");
                    return;
                }
                TopicListParser parser = new TopicListParser(content);
                if (parser.isSucc()) {
                    list = parser.list;
                    binding.swipeContainer.showSucc();
                    mAdapter.setDataList(list);
                    mAdapter.notifyDataSetChanged();

                } else {
                }
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                if (TextUtils.isEmpty(content)) {
                    UIUtils.toast(mContext, "token fail");
                    return;
                }
                TopicListParser parser = new TopicListParser(content);
                if (parser.isSucc()) {
                    list = parser.list;
                    binding.swipeContainer.showSucc();
                    mAdapter.setDataList(list);
                    mAdapter.notifyDataSetChanged();

                } else {
                    binding.swipeContainer.showEmptyViewNoContent();
                }
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                binding.swipeContainer.setRefreshing(false);
                binding.swipeContainer.showEmptyViewFail();

            }
        }, RequestManager.CACHE_TYPE_NOCACHE);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.empty_view:
                binding.swipeContainer.showProgress();
                sendRequest2RefreshList();
                break;
            case R.id.fab:
                CameraManager.getInst().openCamera(mContext);
                break;
        }
    }


}
