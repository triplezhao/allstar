package com.potato.sticker.main.ui.act;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.potato.chips.base.BaseActivity;
import com.potato.chips.common.PageCtrl;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.UIUtils;
import com.potato.sticker.R;
import com.potato.sticker.databinding.ActivityUserBinding;
import com.potato.sticker.login.ui.act.LoginActivity;
import com.potato.sticker.main.data.bean.UserBean;
import com.potato.sticker.main.data.db.DBUtil;

import java.net.URLDecoder;

/**
 * Created by ztw on 2015/10/21.
 */
public class UserActivity extends BaseActivity {

    private ActivityUserBinding binding;
    UserBean userBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_user);

        userBean = DBUtil.getLoginUser();

        if (userBean == null) {
            PageCtrl.start2LoginAct(mContext);
        }

        binding.swipeContainer.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.swipeContainer.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        init();
    }

    private void init() {
        ImageLoaderUtil.displayImage(URLDecoder.decode(userBean.getHeadImg()), binding.ivAvatar, R.drawable.def_gray_small);
        binding.tvName.setText(userBean.getNickname());
        binding.tvTime.setText(userBean.getCreateDate());
        binding.tvTagCount.setText(userBean.getTopicCount());
        binding.tvFansCount.setText(userBean.getFansCount());
        binding.tvFocusCount.setText(userBean.getFocusCount());
        binding.tvZanCount.setText(userBean.getLaudCount());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //requestCode标示请求的标示   resultCode表示有数据
        if (requestCode == LoginActivity.LOGIN && resultCode == RESULT_OK) {
            if (DBUtil.getLoginUser() == null) {
                UIUtils.toast(mContext, "登录失败");
            } else {
                userBean = DBUtil.getLoginUser();
                init();
            }
        }
    }

}
