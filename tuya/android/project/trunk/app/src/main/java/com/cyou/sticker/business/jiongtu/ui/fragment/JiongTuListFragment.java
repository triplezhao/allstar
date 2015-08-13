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
import android.widget.ListView;

import com.cyou.model.library.net.Request;
import com.cyou.model.library.net.RequestManager;
import com.cyou.model.library.util.L;
import com.cyou.sticker.R;
import com.cyou.sticker.base.BaseFragment;
import com.cyou.sticker.business.jiongtu.data.bean.JiongtuAlbum;
import com.cyou.sticker.business.jiongtu.data.parser.JiongtuAlbumListParser;
import com.cyou.sticker.business.jiongtu.data.request.JiongtuRequestBuilder;
import com.cyou.sticker.business.jiongtu.ui.viewbinder.JiongTuViewBinder;
import com.cyou.sticker.databinding.FragmentAListBinding;
import com.cyou.sticker.base.BaseListAdapter;
import com.cyou.sticker.view.refresh.RefreshListView;

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
    private RefreshListView mRefreshLayout;
    private ListView mListView;
    private ArrayList<JiongtuAlbum> albumList = new ArrayList<JiongtuAlbum>();
    private BaseListAdapter mAdapter;
    private JiongtuAlbumListParser albumParser;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getExtras();
        findViews();
        bindEvent();
        bindData();

        FragmentAListBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.fragment_a_list,
                container,
                false);

        mListView = binding.list;
        mRefreshLayout = binding.swipeContainer;

        albumParser = new JiongtuAlbumListParser();
        mRefreshLayout.setFooterView(getActivity(), mListView, R.layout.listview_footer);

        mAdapter = new BaseListAdapter(getActivity(), new JiongTuViewBinder());
        mListView.setAdapter(mAdapter);

        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest2RefreshList();
            }
        });
        mRefreshLayout.setOnLoadListener(new RefreshListView.OnLoadListener() {
            @Override
            public void onLoad() {
                sendRequest2LoadMoreList();
            }
        });
        mRefreshLayout.setRefreshing(true);
        sendRequest2RefreshList();
        return mRefreshLayout;
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
                mRefreshLayout.setRefreshing(false);
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
                RequestManager.CACHE_TYPE_NORMAL
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
                mRefreshLayout.setLoading(false);
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
        albumList = albumParser.parseToAlbumList(content);
        mAdapter.setDataList(albumList);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
        if (albumList!=null||albumList.size()!= 0) {
            mRefreshLayout.setLoadEnable(true);
        }
    }
    private void onLoadSucc(String content) {
        ArrayList<JiongtuAlbum> moreData = albumParser.parseToAlbumList(content);
        if (moreData==null||moreData.size()==0){
            mRefreshLayout.setLoadEnable(false);
            return ;
        }
        albumList.addAll(moreData);
        mAdapter.setDataList(albumList);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setLoading(false);
    }

}
