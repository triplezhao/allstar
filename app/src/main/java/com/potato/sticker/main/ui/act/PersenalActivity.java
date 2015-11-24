package com.potato.sticker.main.ui.act;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.potato.chips.base.BaseActivity;
import com.potato.library.util.L;
import com.potato.sticker.R;
import com.potato.sticker.main.data.bean.ClassifyBean;
import com.potato.sticker.main.ui.fragment.TopicListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ztw on 2015/7/3.
 */
public class PersenalActivity extends BaseActivity {

    public static final String TAG = "PersenalActivity";
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
    ClassifyBean myTopic = new ClassifyBean();
    ClassifyBean myFocusTopic = new ClassifyBean();
    ClassifyBean myFavTopic = new ClassifyBean();
    ClassifyBean mycommetedTopic = new ClassifyBean();
    ClassifyBean myLaudedTopic = new ClassifyBean();
    private List<ClassifyBean> mList = new ArrayList<ClassifyBean>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persenal);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bindData();
    }

    public void bindData() {

        adapter = new HeaderPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        addLocationClassify();
        adapter.notifyDataSetChanged();
        tabLayout.setTabsFromPagerAdapter(adapter);

    }


    private void addLocationClassify(){
        myTopic.setId(TopicListFragment.MY_SECTION_ID);
        myTopic.setName(TopicListFragment.MY_SECTION_TITLE);
        myFocusTopic.setId(TopicListFragment.FOCUS_SECTION_ID);
        myFocusTopic.setName(TopicListFragment.FOCUS_SECTION_TITLE);
        myFavTopic.setId(TopicListFragment.FAV_SECTION_ID);
        myFavTopic.setName(TopicListFragment.FAV_SECTION_TITLE);
        mycommetedTopic.setId(TopicListFragment.COMMETED_SECTION_ID);
        mycommetedTopic.setName(TopicListFragment.COMMETED_SECTION_TITLE);
        myLaudedTopic.setId(TopicListFragment.LAUDED_SECTION_ID);
        myLaudedTopic.setName(TopicListFragment.LAUDED_SECTION_TITLE);
        mList.add(myTopic);
        mList.add(myFocusTopic);
        mList.add(myFavTopic);
        mList.add(mycommetedTopic);
        mList.add(myLaudedTopic);
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
            args.putString(TopicListFragment.EXTRARS_SECTION_ID, obj.getId());
            args.putString(TopicListFragment.EXTRARS_TITLE, obj.getName());
            TopicListFragment pageFragement = (TopicListFragment) Fragment.instantiate(mContext, TopicListFragment.class.getName(), args);
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
