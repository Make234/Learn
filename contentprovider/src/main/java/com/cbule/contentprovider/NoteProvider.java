package com.cbule.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by Administrator on 2016/7/11.
 */
public class NoteProvider extends ContentProvider {
    private SQLiteDatabase db;


    @Override
    public boolean onCreate() {
        try {
            String path = "/sdcard/note.db";
            File file = new File(path);
            db = SQLiteDatabase.openOrCreateDatabase(file, null);
            String sql = "create table if not exists record (_id integer primary key autoincrement,title,content,time)";
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor = db.query("record", strings, s, strings1, null, null, s1);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Long id = db.insert("record", null, contentValues);
        Uri uri2 = ContentUris.withAppendedId(uri, id);
        return uri2;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int i = db.delete("record", s, strings);
        return i;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int i = db.update("record",contentValues,s,strings);
        return i;
    }
}
