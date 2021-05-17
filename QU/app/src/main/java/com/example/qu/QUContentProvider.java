package com.example.qu;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class QUContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse("content://com.example.quprovider/quests");

    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_TASK = "task";
    public static final String KEY_CORRECT_ANSWER = "correct_answer";
    public static final String KEY_CREATION_DATE = "creation_date";
    public static final String KEY_QUEST_ID = "quest_id";

    private QUSQLiteOpenHelper openHelper;

    private static final int ALL_ROWS = 1;
    private static final int SINGLE_ROW = 2;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.quprovider", "quests", ALL_ROWS);
        uriMatcher.addURI("com.example.quprovider", "quests/#", SINGLE_ROW);
    }

    @Override
    public boolean onCreate() {
        openHelper = new QUSQLiteOpenHelper(getContext(), QUSQLiteOpenHelper.DATABASE_NAME,
                null, QUSQLiteOpenHelper.DATABASE_VERSION);

        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_ROWS:
                return "vdn.android.cursor.dir/vdn.example.quests";
            case SINGLE_ROW:
                return "vdn.android.cursor.item/vdn.example.quests";
            default:
                return "";
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sort) {
        SQLiteDatabase db = openHelper.getWritableDatabase();

        String groupBy = "";
        String having = "";

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(QUSQLiteOpenHelper.QUESTS_TABLE);

        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowId = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(KEY_ID + "=" + rowId);
            default:
                break;
        }

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs,
                groupBy, having, sort);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = openHelper.getWritableDatabase();

        String nullColumnHack = KEY_CREATION_DATE;
        long id = db.insert(QUSQLiteOpenHelper.QUESTS_TABLE, nullColumnHack, values);

        if (id > -1) {
            Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(insertedId, null);
            return insertedId;
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = openHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowId = uri.getPathSegments().get(1);
                selection = KEY_ID + "=" + rowId + (!TextUtils.isEmpty(selection) ?
                        "AND (" + selection + ")" : "");
            default:
                break;
        }

        if (selection == null) {
            selection = "1";
        }

        int deleteCount = db.delete(QUSQLiteOpenHelper.QUESTS_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = openHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowId = uri.getPathSegments().get(1);
                selection = KEY_ID + "=" + rowId +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
            default:
                break;
        }

        int updateCount = db.update(QUSQLiteOpenHelper.QUESTS_TABLE, values, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }

    public static class QUSQLiteOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "quDatabase.db";
        private static final int DATABASE_VERSION = 1;

        private static final String QUESTS_TABLE = "questsTable";
        private static final String TASKS_TABLE = "tasksTable";

        private static final String CREATE_TABLE_QUESTS = "create table " +
                QUESTS_TABLE + " (" + KEY_ID +
                " integer primary key autoincrement, " +
                KEY_TITLE + " text not null, " +
                KEY_LOCATION + " text not null, " +
                KEY_CREATION_DATE + "long);";

        private static final String CREATE_TABLE_TASKS = "create table " +
                TASKS_TABLE + " (" + KEY_ID +
                " integer primary key autoincrement, " +
                KEY_QUEST_ID + " integer," +
                KEY_TASK + " text not null, " +
                KEY_LOCATION + " text, " +
                KEY_CORRECT_ANSWER + " text not null, " +
                KEY_CREATION_DATE + "long," +
                " FOREIGN KEY ("+KEY_QUEST_ID+") REFERENCES " + QUESTS_TABLE +
                "("+KEY_ID+"));";

        public QUSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_QUESTS);
            db.execSQL(CREATE_TABLE_TASKS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion + " to " +
                    newVersion + ", which will destroy all old data");

            db.execSQL("DROP TABLE IF EXISTS " + QUESTS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE);

            onCreate(db);
        }
    }
}
