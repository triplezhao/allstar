package com.potato.sticker.main.data.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.potato.chips.app.MainApplication;
import com.potato.sticker.main.data.bean.UserBean;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by ztw on 2015/10/12.
 */
public class DBUtil {

    public static Context context = MainApplication.context;

    public static void addUser(UserBean user) {

        // public static void addUser(UserBean user) {
        // userDao.insert(user);
        addUser(user, true);
    }

    public static void updateUser(UserBean user) {

        // public static void addUser(UserBean user) {
        // userDao.insert(user);
        user.setIslogined("1");
        addUser(user, true);
    }

    public static void addUser(UserBean user, boolean isnotify) {

        // public static void addUser(UserBean user) {
        // userDao.insert(user);
        ContentResolver cr = context.getContentResolver();
        ContentValues values = UserBean.buildContentValues(user);
        cr.insert(UserBeanProvider.CONTENT_URI, values);
        if (isnotify) {
            cr.notifyChange(UserBeanProvider.CONTENT_URI, null);
        }
    }

    /**
     * 判断是否登陆，登陆的则获取user
     *
     * @return
     */
    public static UserBean getLoginUser() {

        // CharityUserBeanDao charityUserBeanDao =
        // getDaoSession().getCharityUserBeanDao();
        //
        // QueryBuilder<UserBean> qb = charityUserBeanDao.queryBuilder();
        // qb.where(CharityUserBeanDao.Properties.Islogined.eq("1"));
        // if(qb.list().size()>0){
        // return qb.list().get(0);
        UserBean bean = null;
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr
                .query(UserBeanProvider.CONTENT_URI, null,
                        UserBeanProvider.Columns.islogined + " = 1 ",
                        null, null);
        if (cursor == null)
            return null;
        while (cursor.moveToNext()) {
            bean = UserBean.createFromCursor(cursor);
        }
        cursor.close();
        return bean;
    }


    /**
     * 退出登陆后，则修改登陆状态为0
     */
    public static void loginOut() {


        ContentValues values = new ContentValues();
        values.put(UserBeanProvider.Columns.islogined, "0");
        ContentResolver cr = context.getContentResolver();
        cr.update(UserBeanProvider.CONTENT_URI, values,
                UserBeanProvider.Columns.islogined + " = 1", null);
        cr.notifyChange(UserBeanProvider.CONTENT_URI, null);

        Platform plat = new Wechat(context);
        plat.removeAccount();
        plat = new QQ(context);
        plat.removeAccount();
        plat = new SinaWeibo(context);
        plat.removeAccount();
    }

}
