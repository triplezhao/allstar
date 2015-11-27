package com.potato.sticker.main.ui.act;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.potato.chips.base.BaseActivity;
import com.potato.chips.common.PageCtrl;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.UIUtils;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.view.hfrecyclerview.HFGridlayoutSpanSizeLookup;
import com.potato.library.view.refresh.HFRecyclerSwipeLayout;
import com.potato.sticker.R;
import com.potato.sticker.databinding.ActivityUserBinding;
import com.potato.sticker.login.ui.act.LoginActivity;
import com.potato.sticker.main.data.bean.PicBean;
import com.potato.sticker.main.data.bean.UserBean;
import com.potato.sticker.main.data.db.DBUtil;
import com.potato.sticker.main.data.parser.PicListParser;
import com.potato.sticker.main.data.parser.UserParser;
import com.potato.sticker.main.data.request.StickerRequestBuilder;
import com.potato.sticker.main.data.request.StickerRequestUrls;
import com.potato.sticker.main.ui.adapter.PicAdapter;

import java.net.URLDecoder;
import java.util.List;

/**
 * Created by ztw on 2015/10/21.
 */
public class UserInfoActivity extends BaseActivity {

    //注意是第三方的uid
    public static final String EXTRARS_UID = "EXTRARS_UID";
    public static final String EXTRARS_USERBEAN = "EXTRARS_USERBEAN";
    private ActivityUserBinding binding;
    String uid;
    UserBean userBean;
    UserBean loginUserBean;
    private int mPage = 1;
    private int mSize = 12;
    private List<PicBean> list;
    private PicAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_user);
        setSupportActionBar(binding.toolbar);

        userBean = (UserBean) getIntent().getSerializableExtra(EXTRARS_USERBEAN);

        if (userBean == null) {
            uid = getIntent().getStringExtra(EXTRARS_UID);
            if (TextUtils.isEmpty(uid)) {
                UIUtils.toast(this, "uid为空");
                finish();
            }
        } else {
            uid = userBean.getUid();
        }


        loginUserBean = DBUtil.getLoginUser();

        if (loginUserBean == null) {
            PageCtrl.start2LoginAct(mContext);
        }

        mAdapter = new PicAdapter(mContext);
        binding.swipeContainer.setRecyclerView(binding.list, mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        layoutManager.setSpanSizeLookup(new HFGridlayoutSpanSizeLookup(binding.swipeContainer.getHFAdapter(), 3));
        binding.swipeContainer.setLayoutManager(layoutManager);

        binding.swipeContainer.setFooterView(binding.list, R.layout.listview_footer);

        binding.swipeContainer.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestRefreshUserPic();
            }
        });
        //滑动时候，不加载图片
        binding.swipeContainer.setScrollStateLisener(new HFRecyclerSwipeLayout.ScrollStateLisener() {
            @Override
            public void pause() {
                //设置为正在滚动
                ImageLoader.getInstance().pause();
            }

            @Override
            public void resume() {
                //设置为停止滚动
                ImageLoader.getInstance().resume();
            }
        });

        binding.swipeContainer.setOnLoadListener(new HFRecyclerSwipeLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                requestMoreUserpic();
            }
        });
        binding.swipeContainer.setEmptyView(binding.emptyView);
        binding.emptyView.setOnClickListener(this);
        binding.llFans.setOnClickListener(this);
        binding.llFocus.setOnClickListener(this);
        binding.swipeContainer.showProgress();

        requestRefreshUserPic();

        updateUserUI();

    }

    private void updateUserUI() {
        if (userBean == null) {
            return;
        }
        ImageLoaderUtil.displayImage(URLDecoder.decode(userBean.getHeadImg()), binding.ivAvatar, R.drawable.def_gray_small);
        binding.collapsingToolbar.setTitle(userBean.getNickname());
        binding.tvTime.setText(userBean.getCreateDate());
        binding.tvTagCount.setText(userBean.getLabelCount());
        binding.tvFansCount.setText(userBean.getFansCount());
        binding.tvFocusCount.setText(userBean.getFocusCount());
        binding.tvZanCount.setText(userBean.getLaudCount());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //requestCode标示请求的标示   resultCode表示有数据
        if (requestCode == LoginActivity.LOGIN && resultCode == RESULT_OK) {
            if (DBUtil.getLoginUser() == null) {
                UIUtils.toast(mContext, "登录失败");
            } else {
                loginUserBean = DBUtil.getLoginUser();
                updateUserUI();
            }
        }
    }

    public void requestRefreshUserBean() {
        if (userBean != null) {
            uid = userBean.getUid();
        }
        if (uid == null) {
            return;
        }
        Request request = StickerRequestBuilder.getUserInfo(uid);
        RequestManager.requestData(request, new RequestManager.DataLoadListener() {

            @Override
            public void onCacheLoaded(String content) {

            }

            @Override
            public void onSuccess(int statusCode, String content) {

                UserParser parser = new UserParser(
                        content);
                if (parser.isSucc()) {
                    userBean = parser.user;
                    updateUserUI();
                }
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {

            }
        }, RequestManager.CACHE_TYPE_NOCACHE);

    }

    public void requestRefreshUserPic() {

        if (userBean == null) {
            return;
        }

        Request request = StickerRequestBuilder.userPic(userBean.getId(), 1 + "", mSize + "");
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
    private void requestMoreUserpic() {
        if (userBean == null) {
            return;
        }
        Request request = StickerRequestBuilder.userPic(userBean.getId(), mPage + 1 + "", mSize + "");
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
        PicListParser parser = new PicListParser(content);
        if (parser.isSucc()) {
            list = parser.list;
            mPage = Integer.parseInt(parser.curPage);
            binding.swipeContainer.showSucc();
            mAdapter.setDataList(list);
            mAdapter.notifyDataSetChanged();
            /*if (list != null && list.size() != 0) {
                ImageLoaderUtil.displayImage(StickerRequestUrls.BaseStickerURL_IMAGE + list.get(0).getUrl(), binding.backdrop, R.color.sticker_blue);
            }*/

            if (list != null && list.size() != 0 && list.size() < Integer.parseInt(parser.rowCount)) {
                binding.swipeContainer.setLoadEnable(true);
            } else {
                binding.swipeContainer.setLoadEnable(false);
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
        PicListParser parser = new PicListParser(content);
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
        if (userBean == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.ll_focus:
                PageCtrl.start2UserListActivity(mContext, userBean.getId(), false);
                break;
            case R.id.ll_fans:
                PageCtrl.start2UserListActivity(mContext, userBean.getId(), true);
                break;
            case R.id.fab:
                Request request = StickerRequestBuilder.focus(userBean.getId(), loginUserBean.getId());
                RequestManager.requestData(request, new RequestManager.DataLoadListener() {

                    @Override
                    public void onCacheLoaded(String content) {

                    }

                    @Override
                    public void onSuccess(int statusCode, String content) {
                        binding.fab.setBackgroundResource(R.drawable.ic_fav_ed);
                    }

                    @Override
                    public void onFailure(Throwable error, String errMsg) {

                    }
                }, RequestManager.CACHE_TYPE_NOCACHE);
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requestRefreshUserBean();
    }
}
