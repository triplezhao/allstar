package com.potato.sticker.camera.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.library.adapter.BaseMutiRowAdapter;
import com.potato.library.adapter.BaseViewHolder;
import com.potato.sticker.R;
import com.potato.sticker.camera.data.bean.PhotoItem;
import com.potato.sticker.camera.util.CameraManager;
import com.potato.sticker.databinding.ItemCamaraAblumBinding;

/**
 * Created by ztw on 2015/9/21.
 */
public class CameraAblumAdapter extends BaseMutiRowAdapter {

    public CameraAblumAdapter(Context context) {
        super(context);
    }

    @Override
    public int getRowNum() {
        return 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCamaraAblumBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_camara_ablum,
                parent,
                false);
        VH holder = new VH(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemCamaraAblumBinding binding = (ItemCamaraAblumBinding) ((VH) holder).getBinding();
        final PhotoItem bean = (PhotoItem) mData.get(position);
        binding.setBean(bean);
        binding.ivPic.setTargetWH(1, 1);

        ImageLoaderUtil.displayImage("file://" + bean.getImageUri(), binding.ivPic,R.drawable.def_gray_small, ImageScaleType.EXACTLY);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraManager.getInst().processPhotoItem((Activity) view.getContext(), bean);
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
