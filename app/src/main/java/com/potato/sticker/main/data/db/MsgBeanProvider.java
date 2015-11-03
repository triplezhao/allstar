package com.potato.sticker.main.data.db;

import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.potato.chips.base.BaseProvider;

/**
 * create by freemaker
 */
public class MsgBeanProvider extends BaseProvider {
    public static final String AUTHORITY = "com.potato.sticker.main.data.db.MsgBeanProvider";
    public static final String TABLE_PATH = "MsgBeanTB";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(Uri.parse("content://" + AUTHORITY), TABLE_PATH);
    
    public static class Columns implements BaseColumns{
			public static final String id = "id";
			public static final String type = "type";
			public static final String userId = "userId";
			public static final String content = "content";
			public static final String createDate = "createDate";
    }
    
    /**
     * Use this static method to create the table
     * It will be called by {@link DatabaseHelper} during first launch time to create DB.
     * @param db
     */
    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PATH + "(" + Columns._ID + " integer primary key autoincrement, "
	                + Columns.id + " text, "
	                + Columns.type + " text, "
	                + Columns.userId + " text, "
	                + Columns.content + " text, "
	                + Columns.createDate + " text "
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

