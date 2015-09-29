package com.potato.sticker.camera.ui.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.potato.chips.base.BaseActivity;
import com.potato.sticker.R;
import com.potato.sticker.camera.common.ImageUtils;
import com.potato.sticker.camera.data.bean.Album;
import com.potato.sticker.camera.ui.fragment.CameraAblumFragment;
import com.potato.sticker.databinding.ActivityAlbumCameraBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ztw on 2015/7/3.
 */
public class CameraAlbumActivity extends BaseActivity {

    public static final String TAG = "CameraAlbumActivity";
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
    private Map<String, Album> albums;
    private List<String> paths = new ArrayList<String>();
    ActivityAlbumCameraBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_album_camera);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        bindData();

    }

    public void bindData() {
        albums = ImageUtils.findGalleries(this, paths, 0);
        adapter = new HeaderPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class HeaderPageAdapter extends FragmentStatePagerAdapter {

        public HeaderPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            //新建一个Fragment来展示ViewPager item的内容，并传递参数
//            return AlbumFragment.newInstance(albums.get(paths.get(position)).getPhotos());

            Bundle args = new Bundle();

            args.putParcelableArrayList(CameraAblumFragment.EXTRARS_LIST, albums.get(paths.get(position)).getPhotos());

            CameraAblumFragment pageFragement = (CameraAblumFragment) Fragment.instantiate(mContext, CameraAblumFragment.class.getName(), args);
            return pageFragement;
        }

        @Override
        public int getCount() {
            return paths == null ? 0 : paths.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return albums.get(paths.get(position)).getTitle();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
