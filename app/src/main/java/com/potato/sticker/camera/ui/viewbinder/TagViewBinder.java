package com.potato.sticker.camera.ui.viewbinder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.chips.base.BaseViewBinder;
import com.potato.library.adapter.BaseViewHolder;
import com.potato.sticker.R;
import com.potato.sticker.camera.customview.LabelView;
import com.potato.sticker.camera.data.bean.FeedItem;
import com.potato.sticker.camera.data.bean.TagItem;
import com.potato.sticker.databinding.ItemPictureBinding;

import java.util.List;

/**
 * Created by ztw on 2015/7/22.
 */
public class TagViewBinder extends BaseViewBinder<TagViewBinder.ViewHolder> {

    public TagViewBinder() {
    }

    @Override
    public ViewHolder onCreateViewHolder(int position, int type, ViewGroup parent) {

        ItemPictureBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_picture,
                parent,
                false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, Object object, int position, int type) {
        final ItemPictureBinding binding = (ItemPictureBinding) holder.getBinding();
        FeedItem feedItem = (FeedItem) object;
        binding.setBean(feedItem);

        final List<TagItem> list = feedItem.getTagList();

        binding.picture.setImageBitmap(BitmapFactory.decodeFile(feedItem.getImgPath()));

//        binding.setList(list);
        // 将标签移除,避免回收使用时标签重复
        binding.pictureLayout.removeViews(1, binding.pictureLayout.getChildCount() - 1);

        // 这里可能有问题 延迟200毫秒加载是为了等pictureLayout已经在屏幕上显示getWidth才为具体的值
//        ImageLoaderUtil.displayImage(bean.getBigCover(), binding.ivPic, R.drawable.def_gray_big);
        // 这里可能有问题 延迟200毫秒加载是为了等pictureLayout已经在屏幕上显示getWidth才为具体的值

        for (TagItem feedImageTag : list) {
            LabelView tagView = new LabelView(binding.getRoot().getContext());
            tagView.init(feedImageTag);
            tagView.draw(binding.pictureLayout,
                    (int) (feedImageTag.getX() ),
                    (int) (feedImageTag.getY() ),
                    feedImageTag.isLeft());
            tagView.wave();
    }
}

public static class ViewHolder extends BaseViewHolder {

    private ViewDataBinding binding;

    public ViewHolder(View itemView) {
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
