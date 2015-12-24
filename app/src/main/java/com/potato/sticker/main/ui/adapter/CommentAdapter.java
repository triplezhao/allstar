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
import com.potato.sticker.databinding.ItemCommentBinding;
import com.potato.sticker.main.data.bean.CommentBean;

import java.net.URLDecoder;

/**
 * Created by ztw on 2015/9/24.
 */
public class CommentAdapter extends PotatoBaseRecyclerViewAdapter {

    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCommentBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_comment,
                parent,
                false);
        VH holder = new VH(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemCommentBinding binding = (ItemCommentBinding) ((VH) holder).getBinding();
        final CommentBean bean = (CommentBean) mData.get(position);

        final Context context = binding.getRoot().getContext();

        ImageLoaderUtil.displayImage(URLDecoder.decode(bean.getUserBean().getHeadImg()), binding.ivAvatar, R.drawable.def_gray_small);
        binding.tvName.setText(bean.getUserBean().getNickname());
        binding.tvTime.setText(bean.getCreateDate());
        binding.tvContent.setText(bean.getContent());
        binding.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PageCtrl.start2UserInfoActivity(context,bean.getUserBean(),binding.getRoot());
            }
        });
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
