package com.potato.chips.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.view.View;

import com.potato.library.util.L;
import com.potato.sticker.R;
import com.potato.sticker.login.ui.act.LoginActivity;
import com.potato.sticker.main.data.bean.TopicBean;
import com.potato.sticker.main.data.bean.UserBean;
import com.potato.sticker.main.ui.act.AllTopicActivity;
import com.potato.sticker.main.ui.act.ClassifyActivity;
import com.potato.sticker.main.ui.act.MainTabActivity;
import com.potato.sticker.main.ui.act.MyActivity;
import com.potato.sticker.main.ui.act.PersenalActivity;
import com.potato.sticker.main.ui.act.TopicDetailActivity;
import com.potato.sticker.main.ui.act.UserInfoActivity;
import com.potato.sticker.main.ui.act.UserListActivity;


/**
 * @ClassName: PageCtrl
 * @Description: 用于处理各模块的跳转
 * @date 2014-8-19 下午4:19:42
 */
public class PageCtrl {

    public static void startForResult(Context from, Class<?> clsAct,
                                      boolean finish, String action, Bundle data, int flags,
                                      int enterAnim, int exitAnim, int requestCode) {
        Intent intent = new Intent(from, clsAct);
        if (null != action) {
            intent.setAction(action);
        }
        if (null != data) {
            intent.putExtras(data);
        }
        if (0 != flags) {
            intent.setFlags(flags);
        }

        if (requestCode >= 0) {
            ((Activity) from).startActivityForResult(intent, requestCode);
        } else {
            from.startActivity(intent);
        }

        if (0 != enterAnim && 0 != exitAnim) {
            ((Activity) from).overridePendingTransition(enterAnim, exitAnim);
        }

        if (finish && from instanceof Activity) {
            ((Activity) from).finish();
        }
    }

    public static void start(Context from, Class<?> clsAct, boolean finish,
                             String action, Bundle data, int flags, int enterAnim, int exitAnim) {
        startForResult(from, clsAct, finish, action, data, flags, enterAnim,
                exitAnim, -1);
    }

    public static void start(Context from, Class<?> clsAct, boolean finish,
                             String action, Bundle data) {
        start(from, clsAct, finish, action, data, 0, 0, 0);
    }

    public static void start(Context from, Class<?> clsAct, boolean finish,
                             String action, Bundle data, int enterAnim, int exitAnim) {
        start(from, clsAct, finish, action, data, 0, enterAnim, exitAnim);
    }

    public static void startForResult(Context from, Class<?> clsAct,
                                      boolean finish, String action, Bundle data, int requestCode) {
        startForResult(from, clsAct, finish, action, data, 0, 0, 0, requestCode);
    }

    /**
     * @Title: start2WebViewActivity
     * @Description: 跳转到webview界面
     * @date: 2014-8-11 下午4:06:19
     */
    public static void start2WebViewActivity(Context mContext, String address) {
        L.d("dongxt", "start2WebViewActivity address = " + address);
        if (TextUtils.isEmpty(address)) return;
        if (address.contains("http")) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(WebViewActivity.URL_ADDRESS, address);
            mContext.startActivity(intent);
        } else {
            // schema 处理
            if (address.contains("app.17173cucc") || address.contains("app.17173")) {
                start2SchemaPage(mContext, address);
            }
        }
    }

    /**
     * @Title: start2SchemaPage
     * @Description: 打开schemaHandlerActivity
     * @param: @param context
     * @param: @param String    设定文件
     * @return: void    返回类型
     * @date: 2014-10-20 下午3:45:48
     */
    public static void start2SchemaPage(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static void start2LoginAct(Context context) {
//        start(context, LoginActivity.class,false,null,null);
        startForResult(context, LoginActivity.class, false, null, null, LoginActivity.LOGIN);
    }

    public static void start2TopicDetail(Context context, String topicId) {
//        start(context, LoginActivity.class,false,null,null);
        Bundle bundle = new Bundle();
        bundle.putString(TopicDetailActivity.TOPIC_ID, topicId);
        start(context, TopicDetailActivity.class, false, null, bundle);
    }

    public static void start2TopicDetail(Context context, String topicId, View view) {
//        start(context, LoginActivity.class,false,null,null);
        Bundle bundle = new Bundle();
        bundle.putSerializable(TopicDetailActivity.TOPIC_ID, topicId);
        Intent intent = new Intent(context, TopicDetailActivity.class);
        intent.putExtras(bundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (context instanceof AllTopicActivity || context instanceof ClassifyActivity || context instanceof PersenalActivity || context instanceof MyActivity) {
                context = MainTabActivity.mainActivity;
            }
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) context,
                    // Now we provide a list of Pair items which contain the view we can transitioning
                    // from, and the name of the view it is transitioning to, in the launched activity
                    new Pair<View, String>(view.findViewById(R.id.iv_pic),
                            TopicDetailActivity.VIEW_NAME_PIC));

            // Now we can start the Activity, providing the activity options as a bundle
            ActivityCompat.startActivity((Activity) context, intent, activityOptions.toBundle());
        } else {
            start(context, TopicDetailActivity.class, false, null, bundle);
        }
    }

    public static void start2TopicDetail(Context context, TopicBean bean) {
//        start(context, LoginActivity.class,false,null,null);
        Bundle bundle = new Bundle();
        bundle.putSerializable(TopicDetailActivity.TOPIC_BEAN, bean);
        start(context, TopicDetailActivity.class, false, null, bundle);
        /*Bundle bundle = new Bundle();
        bundle.putSerializable(TopicDetailActivity.TOPIC_BEAN, bean);
        Intent intent = new Intent(context, TopicDetailActivity.class);
        intent.putExtras(bundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity) context).getWindow().setExitTransition(new Explode());
            ((Activity) context).startActivity(intent,
                    ActivityOptions
                            .makeSceneTransitionAnimation((Activity) context).toBundle());
        } else {
            start(context, TopicDetailActivity.class, false, null, bundle);
        }*/

    }

    public static void start2TopicDetail(Context context, TopicBean bean, View view) {
//        start(context, LoginActivity.class,false,null,null);
        /*Bundle bundle = new Bundle();
        bundle.putSerializable(TopicDetailActivity.TOPIC_BEAN, bean);
        start(context, TopicDetailActivity.class, false, null, bundle);*/
        Bundle bundle = new Bundle();
        bundle.putSerializable(TopicDetailActivity.TOPIC_BEAN, bean);
        Intent intent = new Intent(context, TopicDetailActivity.class);
        intent.putExtras(bundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (context instanceof AllTopicActivity || context instanceof ClassifyActivity || context instanceof PersenalActivity || context instanceof MyActivity) {
                context = MainTabActivity.mainActivity;
            }
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) context,
                    // Now we provide a list of Pair items which contain the view we can transitioning
                    // from, and the name of the view it is transitioning to, in the launched activity
                    new Pair<View, String>(view.findViewById(R.id.ll_topic),
                            TopicDetailActivity.VIEW_NAME_TOPIC));

            // Now we can start the Activity, providing the activity options as a bundle
            ActivityCompat.startActivity((Activity) context, intent, activityOptions.toBundle());
        } else {
            start(context, TopicDetailActivity.class, false, null, bundle);
        }
    }

    //用户详情页
    public static void start2UserInfoActivity(Context context, UserBean bean, View view) {
        /*Bundle bundle = new Bundle();
        bundle.putSerializable(UserInfoActivity.EXTRARS_USERBEAN, bean);
        start(context, UserInfoActivity.class, false, null, bundle);*/

        Bundle bundle = new Bundle();
        bundle.putSerializable(UserInfoActivity.EXTRARS_USERBEAN, bean);
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtras(bundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (context instanceof AllTopicActivity || context instanceof ClassifyActivity || context instanceof PersenalActivity || context instanceof MyActivity) {
                context = MainTabActivity.mainActivity;
            }
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) context,
                    // Now we provide a list of Pair items which contain the view we can transitioning
                    // from, and the name of the view it is transitioning to, in the launched activity
                    new Pair<View, String>(view.findViewById(R.id.iv_avatar),
                            UserInfoActivity.VIEW_NAME_AVATAR)
            );

            // Now we can start the Activity, providing the activity options as a bundle
            ActivityCompat.startActivity((Activity) context, intent, activityOptions.toBundle());
        } else {
            start(context, UserInfoActivity.class, false, null, bundle);
        }
    }

    //用户详情页,第三方id
    public static void start2UserInfoActivity(Context context, String uid) {
        Bundle bundle = new Bundle();
        bundle.putString(UserInfoActivity.EXTRARS_UID, uid);
        start(context, UserInfoActivity.class, false, null, bundle);
    }

    //用户list页,true为粉丝列表,uid为自己系统的id
    public static void start2UserListActivity(Context context, String uid, boolean isFans) {
        Bundle bundle = new Bundle();
        bundle.putString(UserListActivity.EXTRA_UID, uid);
        bundle.putBoolean(UserListActivity.EXTRA_IS_FANS, isFans);
        start(context, UserListActivity.class, false, null, bundle);
    }

}

