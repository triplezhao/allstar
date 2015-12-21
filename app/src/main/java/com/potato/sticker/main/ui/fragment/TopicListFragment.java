/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.potato.sticker.main.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.potato.chips.base.BaseFragment;
import com.potato.chips.util.UIUtils;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.view.refresh.HFRecyclerSwipeLayout;
import com.potato.sticker.R;
import com.potato.sticker.camera.util.CameraManager;
import com.potato.sticker.databinding.FragmentTopicListBinding;
import com.potato.sticker.main.data.bean.TopicBean;
import com.potato.sticker.main.data.db.DBUtil;
import com.potato.sticker.main.data.parser.TopicListParser;
import com.potato.sticker.main.data.request.StickerRequestBuilder;
import com.potato.sticker.main.ui.adapter.TopicAdapter;

import java.util.ArrayList;
import java.util.List;

public class TopicListFragment extends BaseFragment {
    private static final String TAG = "TopicListFragment";
    /**
     * extrars
     */
    public static final String EXTRARS_SECTION_ID = "EXTRARS_SECTION_ID";
    public static final String EXTRARS_TITLE = "EXTRARS_TITLE";
    private String mSectionId;
    private String mTitle;

    public static final String ALL_SECTION_ID = "ALL_SECTION_ID";
    public static final String ALL_SECTION_TITLE = "所有";

    public static final String MY_SECTION_ID = "MY_SECTION_ID";
    public static final String MY_SECTION_TITLE = "发过";
    public static final String FOCUS_SECTION_ID = "FOCUS_SECTION_ID";
    public static final String FOCUS_SECTION_TITLE = "关注";
    public static final String FAV_SECTION_ID = "FAV_SECTION_ID";
    public static final String FAV_SECTION_TITLE = "藏过";
    public static final String COMMETED_SECTION_ID = "COMMETED_SECTION_ID";
    public static final String COMMETED_SECTION_TITLE = "评过";
    public static final String LAUDED_SECTION_ID = "LAUDED_SECTION_ID";
    public static final String LAUDED_SECTION_TITLE = "赞过";

    List<TopicBean> list = new ArrayList<TopicBean>();
    private TopicAdapter mAdapter;
    private int mTotal = 1;
    private int mPage = 1;
    private FragmentTopicListBinding binding;
    private int mSize = 10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.fragment_topic_list,
                container,
                false);
//        View view = inflater.inflate(
//                R.layout.recycleview, container, false);
        mSectionId = getArguments() == null ? "" : getArguments().getString(EXTRARS_SECTION_ID);
        mTitle = getArguments() == null ? "" : getArguments().getString(EXTRARS_TITLE);

        mAdapter = new TopicAdapter(mContext);
        binding.swipeContainer.setRecyclerView(binding.list, mAdapter);
        binding.swipeContainer.setLayoutManager(new LinearLayoutManager(mContext));
        binding.swipeContainer.setFooterView(binding.list, R.layout.listview_footer);

        binding.swipeContainer.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        binding.swipeContainer.setScrollStateLisener(new HFRecyclerSwipeLayout.ScrollStateLisener() {
            @Override
            public void pause() {
                ImageLoader.getInstance().pause();
            }

            @Override
            public void resume() {
                ImageLoader.getInstance().resume();
            }
        });
        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest2RefreshList();
            }
        });
        binding.swipeContainer.setOnLoadListener(new HFRecyclerSwipeLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                sendRequest2LoadMoreList();
            }
        });

        binding.swipeContainer.setEmptyView(binding.emptyView);
        binding.emptyView.setOnClickListener(this);
        binding.swipeContainer.showProgress();
        sendRequest2RefreshList();
        return binding.getRoot();
    }


    public void sendRequest2RefreshList() {
        Request request = null;
        if (mSectionId.equals(MY_SECTION_ID)) {
            request = StickerRequestBuilder.topic(DBUtil.getLoginUser().getId(), 1 + "", mSize + "");
        } else if (mSectionId.equals(FOCUS_SECTION_ID)) {
            request = StickerRequestBuilder.focusTopic(DBUtil.getLoginUser().getId(), 1 + "", mSize + "");
        } else if (mSectionId.equals(ALL_SECTION_ID)) {
            request = StickerRequestBuilder.allTopic("", 1 + "", mSize + "");
        } else if (mSectionId.equals(FAV_SECTION_ID)) {
            request = StickerRequestBuilder.favoriteList(DBUtil.getLoginUser().getId(), 1 + "", mSize + "");
        } else if (mSectionId.equals(COMMETED_SECTION_ID)) {
            request = StickerRequestBuilder.commentedTopicList(DBUtil.getLoginUser().getId(), 1 + "", mSize + "", "2000-01-01 00:00:00");
        } else if (mSectionId.equals(LAUDED_SECTION_ID)) {
            request = StickerRequestBuilder.laudededTopicList(DBUtil.getLoginUser().getId(), 1 + "", mSize + "", "2000-01-01 00:00:00");
        } else {
            request = StickerRequestBuilder.getClassifyRela(mSectionId, 1 + "", mSize + "");
        }
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
        Request request = null;
        if (mSectionId.equals(MY_SECTION_ID)) {
            request = StickerRequestBuilder.topic(DBUtil.getLoginUser().getId(), mPage + 1 + "", mSize + "");
        } else if (mSectionId.equals(FOCUS_SECTION_ID)) {
            request = StickerRequestBuilder.focusTopic(DBUtil.getLoginUser().getId(), mPage + 1 + "", mSize + "");
        } else if (mSectionId.equals(ALL_SECTION_ID)) {
            request = StickerRequestBuilder.allTopic("", mPage + 1 + "", mSize + "");
        } else if (mSectionId.equals(FAV_SECTION_ID)) {
            request = StickerRequestBuilder.favoriteList(DBUtil.getLoginUser().getId(), mPage + 1 + "", mSize + "");
        } else if (mSectionId.equals(COMMETED_SECTION_ID)) {
            request = StickerRequestBuilder.commentedTopicList(DBUtil.getLoginUser().getId(), mPage + 1 + "", mSize + "", "2000-01-01 00:00:00");
        } else if (mSectionId.equals(LAUDED_SECTION_ID)) {
            request = StickerRequestBuilder.laudededTopicList(DBUtil.getLoginUser().getId(), mPage + 1 + "", mSize + "", "2000-01-01 00:00:00");
        } else {
            request = StickerRequestBuilder.getClassifyRela(mSectionId, mPage + 1 + "", mSize + "");
        }
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
            if (list != null && list.size() != 0 && list.size() < Integer.parseInt(parser.rowCount)) {
                binding.swipeContainer.setLoadEnable(true);
            } else {
                binding.swipeContainer.setLoadEnable(false);
            }
            if (list == null || list.size() == 0) {
                binding.swipeContainer.showEmptyViewNoContent();
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
            int lastPosition = list.size();
            list.addAll(parser.list);
            if (list != null && list.size() != 0 && list.size() < Integer.parseInt(parser.rowCount)) {
                binding.swipeContainer.setLoadEnable(true);
            } else {
                binding.swipeContainer.setLoadEnable(false);
            }
            mAdapter.setDataList(list);
            mAdapter.notifyItemInserted(lastPosition);
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

}
