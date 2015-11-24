package com.potato.sticker.main.ui.act;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.potato.chips.base.BaseActivity;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.util.L;
import com.potato.sticker.R;
import com.potato.sticker.main.data.bean.ClassifyBean;
import com.potato.sticker.main.data.parser.ClassifyParser;
import com.potato.sticker.main.data.request.StickerRequestBuilder;
import com.potato.sticker.main.ui.fragment.ReTopicListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ztw on 2015/7/3.
 */
public class ClassifyActivity extends BaseActivity {

    public static final String TAG = "ClassifyActivity";
    /** extrars */
    /**
     * views
     */
    private TabLayout tabLayout;
    private ViewPager viewPager;
    /**
     * adapters
     */
    private HeaderPageAdapter adapter;
    /**
     * data
     */
    ClassifyBean allTopic = new ClassifyBean();
    private List<ClassifyBean> mList = new ArrayList<ClassifyBean>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        bindData();
    }

    public void bindData() {

        adapter = new HeaderPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        addLocationClassify();
        adapter.notifyDataSetChanged();
        tabLayout.setTabsFromPagerAdapter(adapter);

        Request request = StickerRequestBuilder.getClassify();
        RequestManager.requestData(request, new RequestManager.DataLoadListener() {
            @Override
            public void onCacheLoaded(String content) {
                updateUI(content);
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                updateUI(content);
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                L.i(TAG, errMsg + "");
            }
        }, RequestManager.CACHE_TYPE_NORMAL);

    }


    private void addLocationClassify(){
        allTopic.setId(ReTopicListFragment.ALL_SECTION_ID);
        allTopic.setName(ReTopicListFragment.ALL_SECTION_TITLE);
        mList.add(allTopic);
    }

    private void updateUI(String content) {
        L.i(TAG, content + "");
        if (TextUtils.isEmpty(content)) {
            return;
        }

        ClassifyParser parser = new ClassifyParser(content);
        List<ClassifyBean> list = parser.list;
        if (list != null && list.size() > 0) {
            mList.clear();
            addLocationClassify();
            mList.addAll(list);
            adapter.notifyDataSetChanged();
            tabLayout.setTabsFromPagerAdapter(adapter);
        }
    }

    private class HeaderPageAdapter extends FragmentStatePagerAdapter {

        public HeaderPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ClassifyBean obj = mList.get(position);
            L.d("In ViewPager#getItem, header: " + obj.getName() + ", position: "
                    + position);
            Bundle args = new Bundle();
            args.putString(ReTopicListFragment.EXTRARS_SECTION_ID, obj.getId());
            args.putString(ReTopicListFragment.EXTRARS_TITLE, obj.getName());
            ReTopicListFragment pageFragement = (ReTopicListFragment) Fragment.instantiate(mContext, ReTopicListFragment.class.getName(), args);
//            ReTopicListFragment pageFragement = (ReTopicListFragment) Fragment.instantiate(mContext, ReTopicListFragment.class.getName(), args);
            return pageFragement;
        }

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mList.get(position).getName();
        }
    }
}
