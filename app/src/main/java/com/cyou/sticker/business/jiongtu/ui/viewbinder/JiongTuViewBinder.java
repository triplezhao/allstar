package com.cyou.sticker.business.jiongtu.ui.viewbinder;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyou.sticker.R;
import com.cyou.sticker.base.BaseViewBinder;
import com.cyou.sticker.base.BaseViewHolder;
import com.cyou.sticker.common.PageCtrl;
import com.cyou.sticker.business.jiongtu.data.bean.JiongtuAlbum;
import com.cyou.sticker.databinding.ItemJiongtuListBinding;
import com.cyou.sticker.util.ImageLoaderUtil;

/**
 * Created by ztw on 2015/7/22.
 */
public class JiongTuViewBinder extends BaseViewBinder<JiongTuViewBinder.ViewHolder> {

    public JiongTuViewBinder() {
    }

    @Override
    public ViewHolder onCreateViewHolder(int position, int type, ViewGroup parent) {

        ItemJiongtuListBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_jiongtu_list,
                parent,
                false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Object object, int position, int type) {
        ItemJiongtuListBinding binding = (ItemJiongtuListBinding) holder.getBinding();
        final JiongtuAlbum bean = (JiongtuAlbum)object;
        binding.setBean(bean);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                PageCtrl.startJiongTuDetailActivity(context, bean);
            }
        });
//        Picasso.with(binding.getRoot().getContext())
//                .load(bean.getBigCover())
//                .placeholder(R.drawable.def_gray_big)
////                .resize(100,100)
//                .into(binding.ivPic);
        ImageLoaderUtil.displayImage(bean.getBigCover(), binding.ivPic, R.drawable.def_gray_big);
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
