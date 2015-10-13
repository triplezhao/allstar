package com.potato.sticker.main.data.db;

import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.potato.chips.base.BaseProvider;

/**
 * create by freemaker
 */
public class UserBeanProvider extends BaseProvider {
    public static final String AUTHORITY = "com.potato.sticker.main.data.db.UserBeanProvider";
    public static final String TABLE_PATH = "UserBeanTB";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(Uri.parse("content://" + AUTHORITY), TABLE_PATH);
    
    public static class Columns implements BaseColumns{
			public static final String country = "country";
			public static final String headImg = "headImg";
			public static final String city = "city";
			public static final String sex = "sex";
			public static final String description = "description";
			public static final String focusCount = "focusCount";
			public static final String fansCount = "fansCount";
			public static final String uid = "uid";
			public static final String province = "province";
			public static final String phone = "phone";
			public static final String nickname = "nickname";
			public static final String labelCount = "labelCount";
			public static final String laudCount = "laudCount";
			public static final String topicCount = "topicCount";
			public static final String id = "id";
			public static final String islogined = "islogined";
			public static final String createDate = "createDate";
			public static final String status = "status";
    }
    
    /**
     * Use this static method to create the table
     * It will be called by {@link DatabaseHelper} during first launch time to create DB.
     * @param db
     */
    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PATH + "(" + Columns._ID + " integer primary key autoincrement, "
	                + Columns.country + " text, "
	                + Columns.headImg + " text, "
	                + Columns.city + " text, "
	                + Columns.sex + " text, "
	                + Columns.description + " text, "
	                + Columns.focusCount + " text, "
	                + Columns.fansCount + " text, "
	                + Columns.uid + " text, "
	                + Columns.province + " text, "
	                + Columns.phone + " text, "
	                + Columns.nickname + " text, "
	                + Columns.labelCount + " text, "
	                + Columns.laudCount + " text, "
	                + Columns.topicCount + " text, "
	                + Columns.id + " text, "
	                + Columns.islogined + " text, "
	                + Columns.createDate + " text, "
	                + Columns.status + " text "
                + ");");
    }
    
    private static final UriMatcher sURLMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURLMatcher.addURI(AUTHORITY, TABLE_PATH, BaseProvider.ITEMS);
        sURLMatcher.addURI(AUTHORITY, TABLE_PATH + "/#", BaseProvider.ITEMS_ID);
    }
    
    @Override
    public String getAuthority() {
        return AUTHORITY;
    }

    @Override
    public String getTablePath() {
        return TABLE_PATH;
    }

    @Override
    public UriMatcher getUriMatcher() {
        return sURLMatcher;
    }
    
     @Override
    public Uri getContentUri() {
        return CONTENT_URI;
    }
    
}

