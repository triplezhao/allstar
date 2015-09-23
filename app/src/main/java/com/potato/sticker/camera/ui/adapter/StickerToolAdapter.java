package com.potato.sticker.camera.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.potato.sticker.camera.common.ImageLoaderUtils;
import com.potato.sticker.R;
import com.potato.sticker.camera.data.bean.Addon;

import java.util.List;

/**
 * 
 * 贴纸适配器
 * @author tongqian.ni
 */
public class StickerToolAdapter extends BaseAdapter {

    List<Addon> filterUris;
    Context     mContext;

    public StickerToolAdapter(Context context, List<Addon> effects) {
        filterUris = effects;
        mContext = context;
    }

    @Override
    public int getCount() {
        return filterUris.size();
    }

    @Override
    public Object getItem(int position) {
        return filterUris.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EffectHolder holder = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_bottom_tool, null);
            holder = new EffectHolder();
            holder.logo = (ImageView) convertView.findViewById(R.id.effect_image);
            holder.container = (ImageView) convertView.findViewById(R.id.effect_background);
            //holder.navImage.setOnClickListener(holder.clickListener);
            convertView.setTag(holder);
        } else {
            holder = (EffectHolder) convertView.getTag();
        }

        final Addon effect = (Addon) getItem(position);

        return showItem(convertView, holder, effect);
    }

    private View showItem(View convertView, EffectHolder holder, final Addon sticker) {

        holder.container.setVisibility(View.GONE);
        ImageLoaderUtils.displayDrawableImage(sticker.getId() + "", holder.logo, null);

        return convertView;
    }

    class EffectHolder {
        ImageView logo;
        ImageView container;
    }

}
