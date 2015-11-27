package com.potato.sticker.main.ui.act;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.potato.chips.app.MainApplication;
import com.potato.chips.base.BaseActivity;
import com.potato.chips.common.PageCtrl;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.SPUtils;
import com.potato.chips.util.UIUtils;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.view.dialog.DialogUtil;
import com.potato.library.view.refresh.HFRecyclerSwipeLayout;
import com.potato.sticker.R;
import com.potato.sticker.camera.customview.LabelView;
import com.potato.sticker.camera.data.bean.TagItem;
import com.potato.sticker.camera.util.EffectUtil;
import com.potato.sticker.databinding.ActivityTopicDetailBinding;
import com.potato.sticker.databinding.ItemTopicBinding;
import com.potato.sticker.main.data.bean.CommentBean;
import com.potato.sticker.main.data.bean.TagBean;
import com.potato.sticker.main.data.bean.TopicBean;
import com.potato.sticker.main.data.bean.TopicPicBean;
import com.potato.sticker.main.data.bean.UserBean;
import com.potato.sticker.main.data.db.DBUtil;
import com.potato.sticker.main.data.parser.CommentListParser;
import com.potato.sticker.main.data.parser.TopicParser;
import com.potato.sticker.main.data.request.StickerRequestBuilder;
import com.potato.sticker.main.data.request.StickerRequestUrls;
import com.potato.sticker.main.ui.adapter.CommentAdapter;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 * Created by sky on 2015/7/20.
 * Weibo: http://weibo.com/2030683111
 * Email: 1132234509@qq.com
 */
public class TopicDetailActivity extends BaseActivity {

    /**
     * 页面入参
     */
    public static final String TOPIC_ID = "TOPIC_ID";
    public static final String TOPIC_BEAN = "TOPIC_BEAN";
    public String mTopic_id = "TOPIC_ID";
    public TopicBean topicBean;

    List<CommentBean> list = new ArrayList<CommentBean>();
    private CommentAdapter mAdapter;
    private int mTotal = 1;
    private int mPage = 1;
    private ActivityTopicDetailBinding binding;
    private int mSize = 10;
    private View headerView;
    private ItemTopicBinding topicBinding;
    private boolean isFromTopic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_topic_detail);
        topicBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.item_topic,
                null,
                false);

        topicBean = (TopicBean) getIntent().getSerializableExtra(TOPIC_BEAN);
        if (topicBean == null) {
            mTopic_id = getIntent().getStringExtra(TOPIC_ID);
            if (TextUtils.isEmpty(mTopic_id)) {
                UIUtils.toast(this, "帖子为空");
                finish();
            }
        } else {
            isFromTopic = true;
            mTopic_id = topicBean.getId();
        }



        setSupportActionBar(binding.toolbar);

        mAdapter = new CommentAdapter(mContext);

        binding.ivSendComent.setOnClickListener(this);

        binding.swipeContainer.setRecyclerView(binding.list, mAdapter);
        binding.swipeContainer.setLayoutManager(new LinearLayoutManager(mContext));

        binding.swipeContainer.setFooterView(binding.list, R.layout.listview_footer);
        binding.swipeContainer.setHeaderView(binding.list,topicBinding.getRoot());

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
        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest2RefreshList();
            }
        });
        binding.swipeContainer.setOnLoadListener(new HFRecyclerSwipeLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                sendRequest2LoadMoreList();
            }
        });

        binding.swipeContainer.setEmptyView(binding.emptyView);
        binding.emptyView.setOnClickListener(this);
        binding.swipeContainer.showProgress();
        sendRequest2RefreshList();


        if (isFromTopic) {
            bindHeaderView(topicBean);

        } else {
            //用topicid去请求帖子详情，获取成功后，再用userid请求用户详情
            sendRequestTopicDetail();
        }
    }


    public void sendRequest2RefreshList() {

        Request request = StickerRequestBuilder.commentList(mTopic_id + "", 1 + "", mSize + "");
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
     * 更多
     */
    private void sendRequest2LoadMoreList() {
        Request request = StickerRequestBuilder.commentList(mTopic_id + "", mPage + 1 + "", mSize + "");
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
        CommentListParser parser = new CommentListParser(content);
        if (parser.isSucc()) {
            if (topicBean != null) {
                topicBean.setCommentCount(parser.rowCount);
                bindHeaderView(topicBean);
            }

            list = parser.list;
            mPage = Integer.parseInt(parser.curPage);
            binding.swipeContainer.showSucc();
            mAdapter.setDataList(list);
            if (list != null && list.size() != 0 && list.size() < Integer.parseInt(parser.rowCount)) {
                binding.swipeContainer.setLoadEnable(true);
            } else {
                binding.swipeContainer.setLoadEnable(false);
            }
            mAdapter.notifyDataSetChanged();
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
        CommentListParser parser = new CommentListParser(content);
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
            case R.id.iv_send_coment:
                sendRequestComment();
                break;
        }
    }


    public void bindHeaderView(final TopicBean bean) {
        topicBinding.setBean(bean);
        final Context context = topicBinding.getRoot().getContext();
        final List<TopicPicBean> piclist = bean.getPicBeans();
        final List<TagBean> list = piclist.get(0).getTagBeans();

        if (bean.getUserBean() != null) {
            ImageLoaderUtil.displayImage(URLDecoder.decode(bean.getUserBean().getHeadImg()), topicBinding.ivAvatar, R.drawable.def_gray_small);
            topicBinding.tvName.setText(bean.getUserBean().getNickname());
            topicBinding.tvTime.setText(bean.getCreateDate());
        }

        topicBinding.tvContent.setText(bean.getContent());
        // 将标签移除,避免回收使用时标签重复
        topicBinding.pictureLayout.removeViews(1, topicBinding.pictureLayout.getChildCount() - 1);
        topicBinding.picture.setTargetWH(1, 1);
        // 这里可能有问题 延迟200毫秒加载是为了等pictureLayout已经在屏幕上显示getWidth才为具体的值
//        ImageLoaderUtil.displayImage("file://"+feedItem.getImgPath(), topicBinding.picture, R.drawable.def_gray_big);
        ImageLoaderUtil.displayImage(StickerRequestUrls.BaseStickerURL_IMAGE + piclist.get(0).getImgPath(), topicBinding.picture, R.drawable.def_gray_big);
        // 这里可能有问题 延迟200毫秒加载是为了等pictureLayout已经在屏幕上显示getWidth才为具体的值
//        int parantWidth = MainApplication.screenWidth - PhoneUtils.dip2px(context, 8);
        int parantWidth = MainApplication.screenWidth;
        for (TagBean tagBean : list) {
            LabelView tagView = new LabelView(topicBinding.getRoot().getContext());
            TagItem tagItem = TagBean.Convert2TagItem(tagBean);
            tagView.init(tagItem);
            tagView.draw(topicBinding.pictureLayout,
                    EffectUtil.getRealDis((float) tagItem.getX(), parantWidth),
                    EffectUtil.getRealDis((float) tagItem.getY(), parantWidth),
                    tagItem.isLeft());
            tagView.wave();
        }
        //显示评论数
        topicBinding.tvCommentCount.setText(bean.getCommentCount());
        //赞
        topicBinding.tvHeartCount.setText(bean.getLaudCount());
        //赞的状态，如果点过赞，则实心
        if (SPUtils.read(context, SPUtils.SP_NAME_DEFAULT, bean.getId() + bean.getUserId() + DBUtil.getLoginUser().getId(), false)) {
            topicBinding.ivHeart.setImageResource(R.drawable.ic_heart_selected);
            //点赞
            topicBinding.ivHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    SPUtils.write(view.getContext(), SPUtils.SP_NAME_DEFAULT, bean.getId() + bean.getUserId() + DBUtil.getLoginUser().getId(), false);
                    bindHeaderView(topicBean);
                }
            });
        } else {
            topicBinding.ivHeart.setImageResource(R.drawable.ic_heart_normal);
            //点赞
            topicBinding.ivHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    Request request = StickerRequestBuilder.topicLaud(bean.getId(), bean.getUserId(), DBUtil.getLoginUser().getId());
                    RequestManager.requestData(request, new RequestManager.DataLoadListener() {

                        @Override
                        public void onCacheLoaded(String content) {

                        }

                        @Override
                        public void onSuccess(int statusCode, String content) {

                            SPUtils.write(view.getContext(), SPUtils.SP_NAME_DEFAULT, bean.getId() + bean.getUserId() + DBUtil.getLoginUser().getId(), true);
//                            mAdapter.notifyDataSetChanged();
                            bindHeaderView(topicBean);
                        }

                        @Override
                        public void onFailure(Throwable error, String errMsg) {

                        }
                    }, RequestManager.CACHE_TYPE_NOCACHE);
                }
            });
        }

        topicBinding.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PageCtrl.start2UserInfoActivity(context, bean.userBean);
            }
        });

    }

    public void sendRequestTopicDetail() {

        Request request = StickerRequestBuilder.topicDetail(mTopic_id);
        RequestManager.requestData(request, new RequestManager.DataLoadListener() {

            @Override
            public void onCacheLoaded(String content) {
                TopicParser parser = new TopicParser(content);
                if (parser.isSucc()) {
                    if (topicBean == null) {
                        topicBean = new TopicBean();
                        topicBean = parser.topicBean;
                    } else {
                        topicBean.setLaudCount(parser.topicBean.getLaudCount());
                        topicBean.setId(parser.topicBean.getId());
                        topicBean.setTitle(parser.topicBean.getTitle());
                        topicBean.setUserId(parser.topicBean.getUserId());
                        topicBean.setContent(parser.topicBean.getContent());
                        topicBean.setCreateDate(parser.topicBean.getCreateDate());
                        topicBean.setStatus(parser.topicBean.getStatus());
                        topicBean.setPicBeans(parser.topicBean.getPicBeans());
                    }
                    bindHeaderView(topicBean);
                    sendRequestUserDetail();
                }

            }

            @Override
            public void onSuccess(int statusCode, String content) {

                TopicParser parser = new TopicParser(content);
                if (parser.isSucc()) {
                    if (topicBean == null) {
                        topicBean = new TopicBean();
                        topicBean = parser.topicBean;
                    } else {
                        topicBean.setLaudCount(parser.topicBean.getLaudCount());
                        topicBean.setId(parser.topicBean.getId());
                        topicBean.setTitle(parser.topicBean.getTitle());
                        topicBean.setUserId(parser.topicBean.getUserId());
                        topicBean.setContent(parser.topicBean.getContent());
                        topicBean.setCreateDate(parser.topicBean.getCreateDate());
                        topicBean.setStatus(parser.topicBean.getStatus());
                        topicBean.setPicBeans(parser.topicBean.getPicBeans());
                    }
                    bindHeaderView(topicBean);
                    sendRequestUserDetail();
                }

            }

            @Override
            public void onFailure(Throwable error, String errMsg) {

                UIUtils.toast(mContext, errMsg);
            }
        }, RequestManager.CACHE_TYPE_NORMAL);

    }

    public void sendRequestUserDetail() {

        if (topicBean != null) {

            UserBean userBean = DBUtil.getLoginUser();
            if (userBean != null) {
                topicBean.setUserBean(userBean);
                bindHeaderView(topicBean);
            }
        }

    }

    public void sendRequestComment() {
        String comment_content = binding.etSendComent.getText().toString();
        if (TextUtils.isEmpty(comment_content)) {
            UIUtils.toast(mContext, "请编辑评论内容");
            return;
        }
        if (topicBean != null) {
            final Dialog dialog = DialogUtil.createProgressDialog(mContext);
            dialog.show();


            Request request = StickerRequestBuilder.comment(topicBean.getId(), topicBean.getUserId(), DBUtil.getLoginUser().getId(), comment_content);
            RequestManager.requestData(request, new RequestManager.DataLoadListener() {

                @Override
                public void onCacheLoaded(String content) {

                }

                @Override
                public void onSuccess(int statusCode, String content) {
                    dialog.dismiss();
                    TopicParser parser = new TopicParser(content);
                    if (parser.isSucc()) {
                        binding.etSendComent.setText("");
                        UIUtils.toast(mContext, "评论成功");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(binding.etSendComent.getWindowToken(), 0);
                        sendRequest2RefreshList();
                    }

                }

                @Override
                public void onFailure(Throwable error, String errMsg) {
                    dialog.dismiss();
                    UIUtils.toast(mContext, errMsg);
                }
            }, RequestManager.CACHE_TYPE_NOCACHE);

        } else {

            UIUtils.toast(mContext, "帖子内容还没加载完成");

        }

    }

}
