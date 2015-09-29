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

package com.potato.sticker.camera.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.chips.base.BaseFragment;
import com.potato.library.adapter.BaseMutiRowAdapter;
import com.potato.sticker.R;
import com.potato.sticker.camera.data.bean.PhotoItem;
import com.potato.sticker.camera.ui.adapter.CameraAblumAdapter;
import com.potato.sticker.databinding.FragmentAblumCamaraBinding;

import java.util.ArrayList;

public class CameraAblumFragment extends BaseFragment {
    private static final String TAG = "CameraAblumFragment";
    /**
     * extrars
     */
    public static final String EXTRARS_LIST= "EXTRARS_LIST";
//    private long mSectionId;
//    private String mTitle;
    private ArrayList<PhotoItem> mList = new ArrayList<PhotoItem>();
    private BaseMutiRowAdapter mAdapter;
    private FragmentAblumCamaraBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mList = getArguments().getParcelableArrayList(EXTRARS_LIST);

        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.fragment_ablum_camara,
                container,
                false);

        mAdapter = new CameraAblumAdapter(getActivity());
        mAdapter.setDataList(mList);
        mBinding.list.setAdapter(mAdapter);

        mBinding.swipeContainer.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        mBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest2RefreshList();
            }
        });

        mBinding.swipeContainer.setEmptyView(mBinding.emptyView);
        mBinding.emptyView.setOnClickListener(this);
        mBinding.swipeContainer.showProgress();

        sendRequest2RefreshList();

        return mBinding.getRoot();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.empty_view:
                mBinding.swipeContainer.showProgress();
                sendRequest2RefreshList();
                break;
        }
    }

    /**
     * 刷新图册列表
     */
    private void sendRequest2RefreshList() {
        mAdapter.setDataList(mList);
        mAdapter.notifyDataSetChanged();
        mBinding.swipeContainer.setRefreshing(false);
    }

}
