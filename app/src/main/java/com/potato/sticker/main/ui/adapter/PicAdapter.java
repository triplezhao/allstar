package com.potato.sticker.main.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.potato.chips.common.PageCtrl;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.library.adapter.BaseRecyclerViewAdapter;
import com.potato.library.adapter.BaseViewHolder;
import com.potato.sticker.R;
import com.potato.sticker.databinding.ItemPicBinding;
import com.potato.sticker.main.data.bean.PicBean;
import com.potato.sticker.main.data.request.StickerRequestUrls;

/**
 * Created by ztw on 2015/9/21.
 */
public class PicAdapter extends BaseRecyclerViewAdapter {

    public PicAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPicBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_pic,
                parent,
                false);
        VH holder = new VH(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemPicBinding binding = (ItemPicBinding) ((VH) holder).getBinding();
        final PicBean bean = (PicBean) mData.get(position);
        binding.ivPic.setTargetWH(1, 1);

        ImageLoaderUtil.displayImage(StickerRequestUrls.BaseStickerURL_IMAGE+ bean.getUrl(), binding.ivPic,R.drawable.def_gray_small, ImageScaleType.EXACTLY);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CameraManager.getInst().processPhotoItem((Activity) view.getContext(), bean);
                PageCtrl.start2TopicDetail(binding.getRoot().getContext(),bean.getTopicId(),binding.getRoot());
            }
        });
//
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
