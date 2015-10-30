package com.potato.sticker.main.data.db;

import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.potato.chips.base.BaseProvider;

/**
 * create by freemaker
 */
public class ClassifyBeanProvider extends BaseProvider {
    public static final String AUTHORITY = "com.potato.sticker.main.data.db.ClassifyBeanProvider";
    public static final String TABLE_PATH = "ClassifyBeanTB";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(Uri.parse("content://" + AUTHORITY), TABLE_PATH);
    
    public static class Columns implements BaseColumns{
			public static final String level = "level";
			public static final String name = "name";
			public static final String orderNum = "orderNum";
			public static final String optional = "optional";
			public static final String id = "id";
			public static final String parentId = "parentId";
			public static final String status = "status";
    }
    
    /**
     * Use this static method to create the table
     * It will be called by {@link DatabaseHelper} during first launch time to create DB.
     * @param db
     */
    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PATH + "(" + Columns._ID + " integer primary key autoincrement, "
	                + Columns.level + " text, "
	                + Columns.name + " text, "
	                + Columns.orderNum + " text, "
	                + Columns.optional + " text, "
	                + Columns.id + " text, "
	                + Columns.parentId + " text, "
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

