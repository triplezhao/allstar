package com.potato.chips.base;

import android.view.View;

/**
 * Created by ztw on 2015/7/22.
 */
public abstract class BaseViewBinder <DATA extends Object>{

    public BaseViewBinder() {

    }
    //将数据设置到itemView上
    public abstract void onBindView(View view,DATA bean,int type);

}

