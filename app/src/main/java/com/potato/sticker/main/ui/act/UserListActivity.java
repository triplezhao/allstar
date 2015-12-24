package com.potato.sticker.main.ui.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.potato.chips.base.BaseActivity;
import com.potato.chips.util.UIUtils;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.view.refresh.PotatoRecyclerSwipeLayout;
import com.potato.sticker.R;
import com.potato.sticker.databinding.ActivityUserListBinding;
import com.potato.sticker.main.data.bean.UserBean;
import com.potato.sticker.main.data.parser.UserListParser;
import com.potato.sticker.main.data.request.StickerRequestBuilder;
import com.potato.sticker.main.ui.adapter.UserAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 * Created by sky on 2015/7/20.
 * Weibo: http://weibo.com/2030683111
 * Email: 1132234509@qq.com
 */
public class UserListActivity extends BaseActivity {


    public static final String EXTRA_IS_FANS = "EXTRA_IS_FANS";
    public static final String EXTRA_UID= "EXTRA_UID";
    //是否为粉丝列表，true为粉丝列表。
    public boolean isFans = false;
    public String uid;
    List<UserBean> list = new ArrayList<UserBean>();
    private UserAdapter mAdapter;
    private int mTotal = 1;
    private int mPage = 1;
    private ActivityUserListBinding binding;
    private int mSize = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_user_list);

        isFans = getIntent().getBooleanExtra(EXTRA_IS_FANS, false);
        uid = getIntent().getStringExtra(EXTRA_UID);
        if(TextUtils.isEmpty(uid)){
            UIUtils.toast(mContext,"uid is null");
            finish();
        }

        mAdapter = new UserAdapter(mContext);
        setSupportActionBar(binding.toolbar);

        binding.swipeContainer.setRecyclerView(binding.list, mAdapter);
        binding.swipeContainer.setLayoutManager(new LinearLayoutManager(mContext));
        binding.swipeContainer.setFooterView( binding.list, R.layout.potato_listview_footer);

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
        binding.swipeContainer.setScrollStateLisener(new PotatoRecyclerSwipeLayout.ScrollStateLisener() {
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
        binding.swipeContainer.setOnLoadListener(new PotatoRecyclerSwipeLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                sendRequest2LoadMoreList();
            }
        });

        binding.swipeContainer.setEmptyView(binding.emptyView);
        binding.emptyView.setOnClickListener(this);
        binding.swipeContainer.showProgress();
        sendRequest2RefreshList();
    }


    public void sendRequest2RefreshList() {

        Request request = StickerRequestBuilder.fansList(uid, 1 + "", mSize + "");
        if (!isFans) {
            request = StickerRequestBuilder.focusUserList(uid, 1 + "", mSize + "");
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
        Request request = StickerRequestBuilder.message(uid, "", mPage + 1 + "", mSize + "");
        if (!isFans) {
            request = StickerRequestBuilder.focusUserList(uid, 1 + "", mSize + "");
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
        UserListParser parser = new UserListParser(content);
        if (parser.isSucc()) {
            list = parser.list;
            mPage = Integer.parseInt(parser.curPage);
            binding.swipeContainer.showSucc();
            mAdapter.setDataList(list);
            mAdapter.notifyDataSetChanged();
            if (list != null && list.size() != 0 && list.size() < Integer.parseInt(parser.rowCount)) {
                binding.swipeContainer.setLoadEnable(true);
            }else{
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
        UserListParser parser = new UserListParser(content);
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
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
