package com.potato.sticker.main.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.library.adapter.PotatoBaseRecyclerViewAdapter;
import com.potato.library.adapter.PotatoBaseViewHolder;
import com.potato.sticker.R;
import com.potato.sticker.databinding.ItemMsgBinding;
import com.potato.sticker.main.data.bean.MsgBean;

/**
 * Created by ztw on 2015/9/24.
 */
public class MsgAdapter extends PotatoBaseRecyclerViewAdapter {

    public MsgAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMsgBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_msg,
                parent,
                false);
        VH holder = new VH(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemMsgBinding binding = (ItemMsgBinding) ((VH) holder).getBinding();
        MsgBean bean = (MsgBean) mData.get(position);

        Context context = binding.getRoot().getContext();

        binding.tvName.setText(bean.getType());
        binding.tvTime.setText(bean.getCreateDate());

//        binding.picture.setImageBitmap(BitmapFactory.decodeFile(feedItem.getImgPath()));

//        binding.setList(list);

        binding.tvContent.setText(bean.getContent());

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
