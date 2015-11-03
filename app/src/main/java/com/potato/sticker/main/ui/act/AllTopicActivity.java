package com.potato.sticker.main.ui.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;

import com.potato.chips.base.BaseActivity;
import com.potato.chips.events.TopicSendedEvent;
import com.potato.chips.util.UIUtils;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.view.refresh.ListSwipeLayout;
import com.potato.sticker.R;
import com.potato.sticker.camera.util.CameraManager;
import com.potato.sticker.databinding.ActivityUserTopicBinding;
import com.potato.sticker.main.data.bean.TopicBean;
import com.potato.sticker.main.data.parser.TopicListParser;
import com.potato.sticker.main.data.request.StickerRequestBuilder;
import com.potato.sticker.main.ui.adapter.TopicAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 主界面
 * Created by sky on 2015/7/20.
 * Weibo: http://weibo.com/2030683111
 * Email: 1132234509@qq.com
 */
public class AllTopicActivity extends BaseActivity {

    List<TopicBean> list = new ArrayList<TopicBean>();
    private TopicAdapter mAdapter;
    private int mTotal = 1;
    private int mPage = 1;
    private ActivityUserTopicBinding binding;
    private int mSize = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_user_topic);
        binding.fab.setOnClickListener(this);

        mAdapter = new TopicAdapter(mContext);
        binding.list.setAdapter(mAdapter);

        binding.swipeContainer.setFooterView(this, binding.list, R.layout.listview_footer);

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
        binding.swipeContainer.setOnLoadListener(new ListSwipeLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                sendRequest2LoadMoreList();
            }
        });
        binding.swipeContainer.setEmptyView(binding.emptyView);
        binding.emptyView.setOnClickListener(this);
        binding.swipeContainer.showProgress();

        sendRequest2RefreshList();
    }


    public void sendRequest2RefreshList() {

        Request request = StickerRequestBuilder.allTopic("", 1 + "", mSize + "");
        RequestManager.requestData(request, new RequestManager.DataLoadListener() {

            @Override
            public void onCacheLoaded(String content) {
                onRefreshSucc(content);
            }

            @Override
            public void onSuccess(int statusCode, String content) {

                onRefreshSucc(content);
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                binding.swipeContainer.setRefreshing(false);
                binding.swipeContainer.showEmptyViewFail();

            }
        }, RequestManager.CACHE_TYPE_NOCACHE);

    }

    /**
     * 刷新图册列表
     */
    private void sendRequest2LoadMoreList() {
        Request request = StickerRequestBuilder.allTopic("", mPage + 1 + "", mSize + "");
        RequestManager.requestData(request, new RequestManager.DataLoadListener() {

            @Override
            public void onCacheLoaded(String content) {
                onLoadSucc(content);

            }

            @Override
            public void onSuccess(int statusCode, String content) {
                onLoadSucc(content);
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                binding.swipeContainer.setLoading(false);

            }
        }, RequestManager.CACHE_TYPE_NOCACHE);
    }


    private void onRefreshSucc(String content) {
        if (TextUtils.isEmpty(content)) {
            UIUtils.toast(mContext, "token fail");
            binding.swipeContainer.showEmptyViewFail();
            return;
        }
        TopicListParser parser = new TopicListParser(content);
        if (parser.isSucc()) {
            list = parser.list;
            mPage = Integer.parseInt(parser.curPage);
            binding.swipeContainer.showSucc();
            mAdapter.setDataList(list);
            mAdapter.notifyDataSetChanged();
            if (list != null && list.size() != 0&&list.size() < Integer.parseInt(parser.rowCount)){
                binding.swipeContainer.setLoadEnable(true);
            }
        } else {
            binding.swipeContainer.showEmptyViewFail();
        }

    }

    private void onLoadSucc(String content) {
        binding.swipeContainer.setLoading(false);
        if (TextUtils.isEmpty(content)) {
            UIUtils.toast(mContext, "token fail");
            return;
        }
        TopicListParser parser = new TopicListParser(content);
        if (parser.isSucc()) {
            mPage = Integer.parseInt(parser.curPage);
            if (parser.list == null || parser.list.size() == 0) {
                binding.swipeContainer.setLoadEnable(false);
                return;
            }
            list.addAll(parser.list);
            if (list.size() >= Integer.parseInt(parser.rowCount)) {
                binding.swipeContainer.setLoadEnable(false);
            }
            mAdapter.setDataList(list);
            mAdapter.notifyDataSetChanged();
        } else {

        }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(TopicSendedEvent topicSendedEvent) {
        if (topicSendedEvent != null) {
            binding.swipeContainer.showProgress();
            sendRequest2RefreshList();
        }
    }
}
