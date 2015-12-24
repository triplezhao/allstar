package com.potato.sticker.main.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.chips.common.PageCtrl;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.library.adapter.PotatoBaseRecyclerViewAdapter;
import com.potato.library.adapter.PotatoBaseViewHolder;
import com.potato.sticker.R;
import com.potato.sticker.databinding.ItemHuodongBinding;
import com.potato.sticker.main.data.bean.TopicBean;
import com.potato.sticker.main.data.bean.TopicPicBean;
import com.potato.sticker.main.data.request.StickerRequestUrls;

import java.util.List;

/**
 * Created by ztw on 2015/9/21.
 */
public class HuodongAdapter extends PotatoBaseRecyclerViewAdapter {

    public HuodongAdapter(Context context) {
        super(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHuodongBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_huodong,
                parent,
                false);
        VH holder = new VH(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemHuodongBinding binding = (ItemHuodongBinding) ((VH) holder).getBinding();
        final TopicBean bean = (TopicBean) mData.get(position);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CameraManager.getInst().processPhotoItem((Activity) view.getContext(), bean);
                PageCtrl.start2TopicDetail(binding.getRoot().getContext(), bean);
            }
        });

        final List<TopicPicBean> piclist = bean.getPicBeans();

        ImageLoaderUtil.displayImage(StickerRequestUrls.BaseStickerURL_IMAGE + piclist.get(0).getImgPath(), binding.ivPic, R.drawable.def_gray_small);
//
    }


    public static class VH extends PotatoBaseViewHolder {

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
