package com.potato.chips.base;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.potato.library.adapter.PotatoBaseRecyclerViewAdapter;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.util.L;
import com.potato.library.view.NormalEmptyView;
import com.potato.library.view.refresh.PotatoRecyclerSwipeLayout;

import java.util.ArrayList;

public abstract class BaseDefaultListFragment extends BaseFragment implements View.OnClickListener, Handler.Callback {

    public static final String TAG = BaseDefaultListFragment.class.getSimpleName();

    public PotatoRecyclerSwipeLayout mSwipeContainer;
    public RecyclerView listview;
    public NormalEmptyView mNormalEmptyView;

    public int mTotal = 0;
    public int mPage = 0;
    public ArrayList mList = new ArrayList();


    public void initListView(View view) {

        mSwipeContainer = (PotatoRecyclerSwipeLayout) view.findViewById(com.potato.library.R.id.swipe_container);
        listview = (RecyclerView) mSwipeContainer.findViewById(com.potato.library.R.id.list);
        mNormalEmptyView = (NormalEmptyView) view.findViewById(com.potato.library.R.id.empty_view);
        mSwipeContainer.setRecyclerView(listview, getAdapter());
        mSwipeContainer.setLayoutManager(new LinearLayoutManager(view.getContext()));
       /*
       瀑布流
       * //setLayoutManager
        ExStaggeredGridLayoutManager manager = new ExStaggeredGridLayoutManager (2, StaggeredGridLayoutManager.VERTICAL);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter(), manager.getSpanCount()));
        mSwipeContainer.setLayoutManager(manager);

       网格
        //setLayoutManager
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter(), manager.getSpanCount()));
        mRecyclerView.setLayoutManager(manager);
       * */
        mSwipeContainer.setFooterView(listview, com.potato.library.R.layout.potato_listview_footer);

        mSwipeContainer.setColorSchemeResources(com.potato.library.R.color.google_blue,
                com.potato.library.R.color.google_green,
                com.potato.library.R.color.google_red,
                com.potato.library.R.color.google_yellow);

        mSwipeContainer.setScrollStateLisener(new PotatoRecyclerSwipeLayout.ScrollStateLisener() {
            @Override
            public void pause() {
                ImageLoader.getInstance().pause();
            }

            @Override
            public void resume() {
                ImageLoader.getInstance().resume();
            }
        });
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reqRefresh();
            }
        });
        mSwipeContainer.setOnLoadListener(new PotatoRecyclerSwipeLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                reqLoadMore();
            }
        });

        mSwipeContainer.setEmptyView(mNormalEmptyView);
        mNormalEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSwipeContainer.showProgress();
                reqRefresh();
            }
        });

    }


    public abstract PotatoBaseRecyclerViewAdapter getAdapter();

    public abstract Request getRefreshRequest();

    public abstract Request getLoadMoreRequest();


    public abstract BaseParser getParser(String json);


    public void onRefreshSucc(String json) {

        if (TextUtils.isEmpty(json)) {
            Toast.makeText(mContext, "token fail", Toast.LENGTH_SHORT).show();
            mSwipeContainer.showEmptyViewFail();
            return;
        }
        BaseParser parser = getParser(json);
        if (parser.isSucc()) {
            mList = parser.list;
            mPage = Integer.parseInt(parser.page);
            mTotal = Integer.parseInt(parser.total);
            mSwipeContainer.showSucc();
            getAdapter().setDataList(mList);
            getAdapter().notifyDataSetChanged();
            if (mList != null && mList.size() != 0 && mList.size() < mTotal) {
                mSwipeContainer.setLoadEnable(true);
            } else {
                mSwipeContainer.setLoadEnable(false);
            }
        } else {
            mSwipeContainer.showEmptyViewFail();
        }

    }

    public void onRefreshFail(String err) {
        mSwipeContainer.showEmptyViewFail();
    }

    public void onLoadMoreSucc(String json) {
        BaseParser parser = getParser(json);
        if (parser.isSucc()) {
            mPage = Integer.parseInt(parser.page);
            mTotal = Integer.parseInt(parser.total);
            if (parser.list == null || parser.list.size() == 0) {
                mSwipeContainer.setLoadEnable(false);
                return;
            }
            int lastPosition = mList.size();
            mList.addAll(parser.list);
            if (mList != null && mList.size() != 0 && mList.size() < mTotal) {
                mSwipeContainer.setLoadEnable(true);
            } else {
                mSwipeContainer.setLoadEnable(false);
            }
            getAdapter().setDataList(mList);
            getAdapter().notifyItemInserted(lastPosition);
        } else {

        }

    }

    public void onLoadMoreFail(String err) {
        mSwipeContainer.showEmptyViewFail();
    }

    public void onCacheLoaded(String json) {

    }

    public void reqRefresh() {

        RequestManager.DataLoadListener dataLoadListener = new RequestManager.DataLoadListener() {


            @Override
            public void onSuccess(int statusCode, String content) {
                L.e(TAG, "onSuccess" + this);
                onRefreshSucc(content);
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                L.e("拉取数据失败!!!,EMPTY_TYPE_ERROR" + this);

                onRefreshFail(errMsg);

            }

            @Override
            public void onCacheLoaded(String content) {
                L.e(TAG, "onCacheLoaded," + this);
                BaseDefaultListFragment.this.onCacheLoaded(content);
            }
        };
        RequestManager.requestData(
                getRefreshRequest(),
                dataLoadListener,
                RequestManager.CACHE_TYPE_NOCACHE
        );
    }

    public void reqLoadMore() {
        RequestManager.DataLoadListener dataLoadListener = new RequestManager.DataLoadListener() {


            @Override
            public void onSuccess(int statusCode, String content) {
                L.e(TAG, "onSuccess" + this);
                onLoadMoreSucc(content);
            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                L.e("拉取数据失败!!!,EMPTY_TYPE_ERROR" + this);

                onLoadMoreFail(errMsg);
            }

            @Override
            public void onCacheLoaded(String content) {
                L.e(TAG, "onCacheLoaded," + this);
                BaseDefaultListFragment.this.onCacheLoaded(content);
            }
        };
        RequestManager.requestData(
                getLoadMoreRequest(),
                dataLoadListener,
                RequestManager.CACHE_TYPE_NOCACHE
        );
    }

}
