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

package com.cyou.sticker.business.a.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cyou.sticker.R;
import com.cyou.sticker.base.BaseFragment;
import com.cyou.sticker.business.a.data.bean.ABean;
import com.cyou.sticker.business.a.ui.viewbinder.AViewBinder;
import com.cyou.sticker.databinding.FragmentAListBinding;
import com.cyou.sticker.base.BaseListAdapter;
import com.cyou.sticker.view.refresh.RefreshListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AListFragment extends BaseFragment {
    /** extrars */
    /** views */
    /** adapters */
    /** data */
    /**
     * logic
     */
    private RefreshListView mRefreshLayout;
    private ListView mListView;
    private List<ABean> mValues = new ArrayList<ABean>();
    private BaseListAdapter mAdapter;

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


        mRefreshLayout.setFooterView(getActivity(), mListView, R.layout.listview_footer);

        mAdapter = new BaseListAdapter(getActivity(), new AViewBinder());
        mListView.setAdapter(mAdapter);

        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mValues = getRandomSublist(Cheeses.sCheeseStrings, 30);
                        mAdapter.setDataList(mValues);
                        mAdapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        mRefreshLayout.setOnLoadListener(new RefreshListView.OnLoadListener() {
            @Override
            public void onLoad() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mValues.addAll(getRandomSublist(Cheeses.sCheeseStrings, 30));
                        mAdapter.setDataList(mValues);
                        mAdapter.notifyDataSetChanged();
                        mRefreshLayout.setLoading(false);
                    }
                }, 3000);

            }
        });
        mRefreshLayout.setRefreshing(true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mValues = getRandomSublist(Cheeses.sCheeseStrings, 30);
                mAdapter.setDataList(mValues);
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        }, 3000);
        return mRefreshLayout;
    }

    private List<ABean> getRandomSublist(String[] array, int amount) {

        ArrayList<ABean> list = new ArrayList<ABean>(amount);
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            list.add(new ABean("title=" + i, Cheeses.getRandomIcon(), "content=" + array[random.nextInt(array.length)]));
        }
        return list;
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
    public void bindEvent() {

    }

    @Override
    public void onClick(View view) {

    }


}
