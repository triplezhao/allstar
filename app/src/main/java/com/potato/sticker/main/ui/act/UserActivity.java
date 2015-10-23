package com.potato.sticker.main.ui.act;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;

import com.potato.chips.base.BaseActivity;
import com.potato.chips.common.PageCtrl;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.UIUtils;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.view.refresh.ListSwipeLayout;
import com.potato.sticker.R;
import com.potato.sticker.databinding.ActivityUserBinding;
import com.potato.sticker.login.ui.act.LoginActivity;
import com.potato.sticker.main.data.bean.PicBean;
import com.potato.sticker.main.data.bean.UserBean;
import com.potato.sticker.main.data.db.DBUtil;
import com.potato.sticker.main.data.parser.PicListParser;
import com.potato.sticker.main.data.request.StickerRequestBuilder;
import com.potato.sticker.main.ui.adapter.PicAdapter;

import java.net.URLDecoder;
import java.util.List;

/**
 * Created by ztw on 2015/10/21.
 */
public class UserActivity extends BaseActivity {

    private ActivityUserBinding binding;
    UserBean userBean;
    private int mPage = 1;
    private int mSize = 10;
    private List<PicBean> list;
    private PicAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_user);

        userBean = DBUtil.getLoginUser();

        if (userBean == null) {
            PageCtrl.start2LoginAct(mContext);
        }

        mAdapter = new PicAdapter(mContext);
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

        init();
    }

    private void init() {
        ImageLoaderUtil.displayImage(URLDecoder.decode(userBean.getHeadImg()), binding.ivAvatar, R.drawable.def_gray_small);
        binding.tvName.setText(userBean.getNickname());
        binding.tvTime.setText(userBean.getCreateDate());
        binding.tvTagCount.setText(userBean.getTopicCount());
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
                userBean = DBUtil.getLoginUser();
                init();
            }
        }
    }
    public void sendRequest2RefreshList() {

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
    private void sendRequest2LoadMoreList() {
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
            if (list != null && list.size() != 0) {
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
        PicListParser parser = new PicListParser(content);
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
}
