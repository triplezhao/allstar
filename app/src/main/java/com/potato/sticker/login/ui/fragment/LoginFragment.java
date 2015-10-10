package com.potato.sticker.login.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.potato.chips.base.BaseFragment;
import com.potato.chips.util.UIUtils;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.util.L;
import com.potato.sticker.R;
import com.potato.sticker.databinding.FragmentLoginBinding;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginFragment extends BaseFragment{
    private static final String TAG = "LoginFragment";
    protected Context mContext;
    /** extrars */
    /** views */
    FragmentLoginBinding binding;
    // header
    private ImageView iv_weixin;
    private ImageView iv_qq;
    private ImageView iv_weibo;
   
    /** adapters */
    /** data */
    /** logic */

    public static String mplatkey = "";
    public static String mother_uid = "";
    public static String mother_uname = "";
    public static String mother_portrait = "";
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mplatkey = "";
        mplatkey = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.fragment_login,
                container,
                false);
        findViews();
        bindEvent();
        return binding.getRoot();
    }


    @Override
    void findViews() {
        // TODO Auto-generated method stub
        iv_weixin = binding.ivWeixin;
        iv_qq =  binding.ivQq;
        iv_weibo =  binding.ivWeibo;
        
    }

    void bindEvent() {
        // TODO Auto-generated method stub
        iv_weixin.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        iv_weibo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        switch (v.getId()) {
        case R.id.iv_back:
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                getActivity().finish();
            } else {
                getFragmentManager().popBackStack();
            }
            break;
        case R.id.iv_weixin:
            third_authorize(new Wechat(mContext));
            break;
        case R.id.iv_qq:
            third_authorize(new QQ(mContext));
            break;
        case R.id.iv_weibo:
            third_authorize(new SinaWeibo(mContext));
            break;
        
        }
    }


    private void third_authorize(Platform plat) {
        
        String platkey = "qquid";
        if (plat.getName().equals(SinaWeibo.NAME)) {
            platkey = "weibouid";
        } else if (plat.getName().equals(Wechat.NAME)) {
            platkey = "weixinuid";
        } else if (plat.getName().equals(QQ.NAME)) {
            platkey = "qquid";
        }
        if (plat.isValid()) {
            String userId = plat.getDb().getUserId();
            if (!TextUtils.isEmpty(userId)) {
                L.i("login","allreaddy="+userId);
                mother_uname = plat.getDb().getUserName();
                mother_portrait = plat.getDb().getUserIcon();
                third_login(userId, platkey);
                return;
            }
        }
        plat.setPlatformActionListener(this);
        plat.SSOSetting(false);
        plat.showUser(null);
    }

    private void third_login() {
        // 1.登录验证是否绑定过这个 uid
        // 2.没绑定过，则去注册流程，绑定这个uid，到新注册的账号。
        L.i("login", "third_login=" + uid + platkey);

        Request req = RequestBuilder.checkThirdLoginRequest(platkey, uid);

        // 实现回调方法
        RequestManager.DataLoadListener dataloadListner = new RequestManager.DataLoadListener() {

            @Override
            public void onSuccess(int statusCode, String content) {
                // TODO Auto-generated method stub
                L.i("login","checkThirdLoginRequest:onSuccess="+content);
                progressDialog.dismiss();
                // content =
                // "{ \"code\": \"000101\", \"desc\": \"succ\", \"data\": { \"uid\": \"10\", \"uname\": \"西方失败\", \"portrait\": \"s.png\", \"level\": \"1\", \"desc\": \"没有什么可写的啦这家伙很懒\", \"weburl\": \"www.sohu.com\", \"token\": \"908a0dc9580afcffb13600c1492bf6dd\" } }";
                CheckLoginParser checkLoginParser = new CheckLoginParser(
                        content);
                if (checkLoginParser.isSucc()) {
                    CharityUserBean user = checkLoginParser.charityUserBean;
                    if (user != null) {
                        user.setIslogined("1");
                        DaoUtil.addUser(user);
                        
                        // 登陆成功，则返回到我的页面
                        if (getFragmentManager().getBackStackEntryCount() == 0) {
                            getActivity().finish();
                        } else {
                            getFragmentManager().popBackStack();
                        }
                    }
                } else {
//                    UIHelper.toast(mContext, checkLoginParser.getDesc());
                    //登录失败去注册流程
                    mplatkey = platkey;
                    mother_uid = uid;
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager
                            .beginTransaction();
                    RegisterFragment1 fragment = new RegisterFragment1();
                    fragmentTransaction.add(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                }

            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                // TODO Auto-generated method stub
                L.i("login","checkThirdLoginRequest:onFailure="+errMsg);
                UIHelper.toastAsync(mContext, error);
                progressDialog.dismiss();
            }

            @Override
            public void onCacheLoaded(final String content) {
                progressDialog.dismiss();

            }
        };

        RequestManager.requestData(req, dataloadListner,
                RequestManager.CACHE_TYPE_NOCACHE);
    }

    @Override
    public void onCancel(Platform arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onComplete(Platform plat, int action,
            HashMap<String, Object> arg2) {
        // TODO Auto-generated method stub
        if (action == Platform.ACTION_USER_INFOR) {
            String platkey = "qquid";
            if (plat.getName().equals(SinaWeibo.NAME)) {
                platkey = "weibouid";
            } else if (plat.getName().equals(Wechat.NAME)) {
                platkey = "weixinuid";
            } else if (plat.getName().equals(QQ.NAME)) {
                platkey = "qquid";
            }
            final String platkeyfinal = platkey;
            final String userId = plat.getDb().getUserId();
            if (!TextUtils.isEmpty(userId)) {
                L.i("login","onComplete="+userId);
                mother_uname = plat.getDb().getUserName();
                mother_portrait = plat.getDb().getUserIcon();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        third_login();
                    }
                });
                
            }
        }
    }


}
