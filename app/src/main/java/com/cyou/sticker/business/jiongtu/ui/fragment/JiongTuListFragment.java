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

package com.cyou.sticker.business.jiongtu.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyou.model.library.net.Request;
import com.cyou.model.library.net.RequestManager;
import com.cyou.model.library.util.L;
import com.cyou.model.library.view.refresh.RefreshListView;
import com.cyou.sticker.R;
import com.cyou.sticker.base.BaseFragment;
import com.cyou.sticker.base.BaseListAdapter;
import com.cyou.sticker.business.jiongtu.data.bean.JiongtuAlbum;
import com.cyou.sticker.business.jiongtu.data.parser.JiongtuAlbumListParser;
import com.cyou.sticker.business.jiongtu.data.request.JiongtuRequestBuilder;
import com.cyou.sticker.business.jiongtu.ui.viewbinder.JiongTuViewBinder;
import com.cyou.sticker.databinding.FragmentJiongtuListBinding;

import java.util.ArrayList;

public class JiongTuListFragment extends BaseFragment {
    private static final String TAG = "ListFragmentJiongtu";
    /**
     * extrars
     */
    public static final String EXTRARS_SECTION_ID = "EXTRARS_SECTION_ID";
    public static final String EXTRARS_TITLE = "EXTRARS_TITLE";
    /** views */
    /** adapters */
    /**
     * data
     */
    private long mSectionId;
    private String mTitle;
    /**
     * logic
     */
    private ArrayList<JiongtuAlbum> albumList = new ArrayList<JiongtuAlbum>();
    private BaseListAdapter mAdapter;
    private JiongtuAlbumListParser albumParser;
    private FragmentJiongtuListBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getExtras();
        findViews();
        bindEvent();
        bindData();

        binding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.fragment_jiongtu_list,
                container,
                false);

        albumParser = new JiongtuAlbumListParser();
        binding.swipeContainer1.setFooterView(getActivity(), binding.list1, R.layout.listview_footer);

        mAdapter = new BaseListAdapter(getActivity(), new JiongTuViewBinder());
        binding.list1.setAdapter(mAdapter);

        binding.swipeContainer1.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        binding.swipeContainer1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest2RefreshList();
            }
        });
        binding.swipeContainer1.setOnLoadListener(new RefreshListView.OnLoadListener() {
            @Override
            public void onLoad() {
                sendRequest2LoadMoreList();
            }
        });

        binding.swipeContainer1.setEmptyView(binding.emptyView);
        binding.emptyView.setOnClickListener(this);
        binding.swipeContainer1.showProgress();
        sendRequest2RefreshList();

        return binding.getRoot();
    }

    @Override
    public void getExtras() {
        mSectionId = getArguments() == null ? 0 : getArguments().getLong(EXTRARS_SECTION_ID);
        mTitle = getArguments() == null ? "" : getArguments().getString(EXTRARS_TITLE);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.empty_view:
                binding.swipeContainer1.showProgress();
                sendRequest2RefreshList();
                break;
        }
    }

    /**
     * 刷新图册列表
     */
    private void sendRequest2RefreshList() {
        L.e(TAG, "请求图册列表:sectionId=" + mSectionId);

        Request request = JiongtuRequestBuilder.getAlbumListRequest(mSectionId, 0);
        RequestManager.DataLoadListener dataLoadListener = new RequestManager.DataLoadListener() {

            @Override
            public void onSuccess(int statusCode, String content) {
                L.e(TAG, "onSuccess" + this);
                onRefreshSucc(content);
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                L.e("拉取图册数据失败!!!,EMPTY_TYPE_ERROR" + this);
                binding.swipeContainer1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.swipeContainer1.showEmptyViewFail();
                    }
                }, 2000);

            }

            @Override
            public void onCacheLoaded(String content) {
                L.e(TAG, "onCacheLoaded," + this);
                onRefreshSucc(content);
            }
        };
        RequestManager.requestData(
                request,
                dataLoadListener,
                RequestManager.CACHE_TYPE_NOCACHE
        );
    }

    /**
     * 刷新图册列表
     */
    private void sendRequest2LoadMoreList() {
        L.e(TAG, "请求图册列表:sectionId=" + mSectionId);

        Request request = JiongtuRequestBuilder.getAlbumListRequest(mSectionId, albumParser.maxPublicDate);
        RequestManager.DataLoadListener dataLoadListener = new RequestManager.DataLoadListener() {

            @Override
            public void onSuccess(int statusCode, String content) {
                L.e(TAG, "onSuccess" + this);
                onLoadSucc(content);

            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                L.e("拉取图册数据失败!!!,EMPTY_TYPE_ERROR" + this);
                binding.swipeContainer1.setLoading(false);
            }

            @Override
            public void onCacheLoaded(String content) {
                L.e(TAG, "onCacheLoaded," + this);
            }
        };
        RequestManager.requestData(
                request,
                dataLoadListener,
                RequestManager.CACHE_TYPE_NOCACHE
        );
    }

    private void onRefreshSucc(String content) {
        binding.swipeContainer1.showSucc();
        albumList = albumParser.parseToAlbumList(content);
        mAdapter.setDataList(albumList);
        mAdapter.notifyDataSetChanged();
        binding.swipeContainer1.setRefreshing(false);
        if (albumList != null || albumList.size() != 0) {
            binding.swipeContainer1.setLoadEnable(true);
        }

    }

    private void onLoadSucc(String content) {
        binding.swipeContainer1.setLoading(false);
        ArrayList<JiongtuAlbum> moreData = albumParser.parseToAlbumList(content);
        if (moreData == null || moreData.size() == 0) {
            binding.swipeContainer1.setLoadEnable(false);
            return;
        }
        albumList.addAll(moreData);
        mAdapter.setDataList(albumList);
        mAdapter.notifyDataSetChanged();
    }

}
