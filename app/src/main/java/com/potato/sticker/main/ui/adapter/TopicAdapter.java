package com.potato.sticker.main.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.chips.app.MainApplication;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.PhoneUtils;
import com.potato.library.adapter.BaseListAdapter;
import com.potato.library.adapter.BaseViewHolder;
import com.potato.sticker.R;
import com.potato.sticker.camera.customview.LabelView;
import com.potato.sticker.camera.data.bean.TagItem;
import com.potato.sticker.camera.util.EffectUtil;
import com.potato.sticker.databinding.ItemTopicBinding;
import com.potato.sticker.main.data.bean.PicBean;
import com.potato.sticker.main.data.bean.TagBean;
import com.potato.sticker.main.data.bean.TopicBean;
import com.potato.sticker.main.data.request.StickerRequestUrls;

import java.net.URLDecoder;
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
        TopicBean bean = (TopicBean) mData.get(position);
        binding.setBean(bean);

        Context context = binding.getRoot().getContext();
        final List<PicBean> piclist = bean.getPicBeans();
        final List<TagBean> list = piclist.get(0).getTagBeans();

        ImageLoaderUtil.displayImage(URLDecoder.decode(bean.getUserBean().getHeadImg()), binding.ivAvatar, R.drawable.def_gray_small);
        binding.tvName.setText(bean.getUserBean().getNickname());
        binding.tvTime.setText(bean.getCreateDate());

//        binding.picture.setImageBitmap(BitmapFactory.decodeFile(feedItem.getImgPath()));

//        binding.setList(list);

        binding.tvContent.setText(bean.getContent());
        binding.tvHeartCount.setText(bean.getLaudCount());
        // 将标签移除,避免回收使用时标签重复
        binding.pictureLayout.removeViews(1, binding.pictureLayout.getChildCount() - 1);
        binding.picture.setTargetWH(1, 1);
        // 这里可能有问题 延迟200毫秒加载是为了等pictureLayout已经在屏幕上显示getWidth才为具体的值
//        ImageLoaderUtil.displayImage("file://"+feedItem.getImgPath(), binding.picture, R.drawable.def_gray_big);
        ImageLoaderUtil.displayImage(StickerRequestUrls.BaseStickerURL_IMAGE + piclist.get(0).getImgPath(), binding.picture, R.drawable.def_gray_big);
        // 这里可能有问题 延迟200毫秒加载是为了等pictureLayout已经在屏幕上显示getWidth才为具体的值
        int parantWidth = MainApplication.screenWidth- PhoneUtils.dip2px(context, 8);
        for (TagBean tagBean : list) {
            LabelView tagView = new LabelView(binding.getRoot().getContext());
            TagItem tagItem = TagBean.Convert2TagItem(tagBean);
            tagView.init(tagItem);
            tagView.draw(binding.pictureLayout,
                    EffectUtil.getRealDis((float)tagItem.getX(), parantWidth),
                    EffectUtil.getRealDis((float)tagItem.getY(), parantWidth),
                    tagItem.isLeft());
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
