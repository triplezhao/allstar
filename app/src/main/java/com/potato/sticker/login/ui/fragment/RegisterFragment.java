package com.potato.sticker.login.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.potato.chips.base.BaseFragment;
import com.potato.chips.util.UIUtils;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.util.StringUtils;
import com.potato.sticker.R;
import com.potato.sticker.databinding.FragmentRegisterBinding;
import com.potato.sticker.main.data.bean.UserBean;
import com.potato.sticker.main.data.db.DBUtil;
import com.potato.sticker.main.data.parser.UserParser;
import com.potato.sticker.main.data.request.StickerRequestBuilder;

public class RegisterFragment extends BaseFragment {
    private static final String TAG = "RegisterFragment";
    /** extrars */
    /** views */
    // header
    /** adapters */
    /**
     * data
     */
    UserBean userBean;
    /**
     * logic
     */
    FragmentRegisterBinding binding;
    private Button register_submit;
    private ImageView iv_back;
    private EditText et_verify_code;
    private EditText register_account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        userBean = (UserBean) getArguments().getSerializable("bean");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.fragment_register,
                container,
                false);
        findViews();
        bindEvent();
        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        // onTabResume();
    }


    void getExtras() {
        // TODO Auto-generated method stub

    }

    void findViews() {
        // TODO Auto-generated method stub
        iv_back = binding.ivClose;
        register_account = binding.registerAccount;
        et_verify_code = binding.etCode;
        register_submit = binding.registerSubmit;

    }

    void bindEvent() {
        // TODO Auto-generated method stub
        iv_back.setOnClickListener(this);
        register_account.setOnClickListener(this);
        register_submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_back:
                if (getFragmentManager().getBackStackEntryCount() == 0) {
                    getActivity().finish();
                } else {
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.register_submit:
                doRegister();
                break;
        }
    }

    public void getVerifyCode() {
        et_verify_code.setText("123");
    }

    public void doRegister() {

        String mobile = register_account.getText().toString();
        String verify_code = et_verify_code.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            UIUtils.toast(mContext, "手机号不允许为空");
            return;
        } else if (!StringUtils.isMobileNO(mobile)) {
            UIUtils.toast(mContext, "手机号格式不对");
            return;
        } else if (TextUtils.isEmpty(verify_code)) {
            UIUtils.toast(mContext, "验证码不允许为空");
            return;
        }

        userBean.setPhone(mobile);

        Request req = StickerRequestBuilder.updataUser(userBean);

        // 显示ProgressDialog
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "Loading...",
                "Please wait...", true, false);
        // 实现回调方法
        RequestManager.DataLoadListener dataloadListner = new RequestManager.DataLoadListener() {
            /*
             * (non-Javadoc)
             *
             * @see
             * com.frame.rrbang.net.RequestManager.DataLoadListener#onSuccess
             * (int, java.lang.String)
             */
            @Override
            public void onSuccess(int statusCode, String content) {
                // TODO Auto-generated method stub
                progressDialog.dismiss();
                // content =
                // "{ \"code\": \"000101\", \"desc\": \"succ\", \"data\": { \"uid\": \"10\", \"uname\": \"西方失败\", \"portrait\": \"s.png\", \"level\": \"1\", \"desc\": \"没有什么可写的啦这家伙很懒\", \"weburl\": \"www.sohu.com\", \"token\": \"908a0dc9580afcffb13600c1492bf6dd\" } }";
                UserParser registerParser = new UserParser(content);
                if (registerParser.isSucc()) {
                    UserBean user = registerParser.user;
                    if (user != null) {
                        user.setIslogined("1");
                        DBUtil.addUser(user);
                        UIUtils.toast(mContext, "login succ");
                        DBUtil.addUser(user);
                        getActivity().setResult(Activity.RESULT_OK);
                        getActivity().finish();
                    } else {
                        getFragmentManager().popBackStack();
                    }
                } else {
                    UIUtils.toast(mContext, registerParser.getMsg());
                    getFragmentManager().popBackStack();
                }

            }

            @Override
            public void onFailure(Throwable error, String errMsg) {
                // TODO Auto-generated method stub
                UIUtils.toastAsync(mContext, error);
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

}
