package com.potato.sticker.camera.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.chips.util.ImageLoaderUtil;
import com.potato.library.adapter.BaseListAdapter;
import com.potato.library.adapter.BaseViewHolder;
import com.potato.sticker.R;
import com.potato.sticker.camera.customview.LabelView;
import com.potato.sticker.camera.data.bean.FeedItem;
import com.potato.sticker.camera.data.bean.TagItem;
import com.potato.sticker.camera.data.request.QiniuRequestUrls;
import com.potato.sticker.databinding.ItemTopicBinding;

import java.util.List;

/**
 * Created by ztw on 2015/9/24.
 */
public class TopicAdapter extends BaseListAdapter {

    public TopicAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTopicBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_topic,
                parent,
                false);
        VH holder = new VH(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemTopicBinding binding = (ItemTopicBinding) ((VH) holder).getBinding();
        FeedItem feedItem = (FeedItem) mData.get(position);
        binding.setBean(feedItem);

        final List<TagItem> list = feedItem.getTagList();

//        binding.picture.setImageBitmap(BitmapFactory.decodeFile(feedItem.getImgPath()));

//        binding.setList(list);
        // 将标签移除,避免回收使用时标签重复
        binding.pictureLayout.removeViews(1, binding.pictureLayout.getChildCount() - 1);
        binding.picture.setTargetWH(1, 1);
        // 这里可能有问题 延迟200毫秒加载是为了等pictureLayout已经在屏幕上显示getWidth才为具体的值
//        ImageLoaderUtil.displayImage("file://"+feedItem.getImgPath(), binding.picture, R.drawable.def_gray_big);
        ImageLoaderUtil.displayImage(QiniuRequestUrls.QINIU_PIC_DOMAIN + feedItem.getImgPath(), binding.picture, R.drawable.def_gray_big);
        // 这里可能有问题 延迟200毫秒加载是为了等pictureLayout已经在屏幕上显示getWidth才为具体的值

        for (TagItem feedImageTag : list) {
            LabelView tagView = new LabelView(binding.getRoot().getContext());
            tagView.init(feedImageTag);
            tagView.draw(binding.pictureLayout,
                    (int) (feedImageTag.getX()),
                    (int) (feedImageTag.getY()),
                    feedImageTag.isLeft());
            tagView.wave();
        }
    }


    public static class VH extends BaseViewHolder {

        private ViewDataBinding binding;

        public VH(View itemView) {
            super(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}
