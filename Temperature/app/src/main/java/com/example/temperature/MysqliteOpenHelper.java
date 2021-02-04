package com.example.temperature;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MysqliteOpenHelper extends SQLiteOpenHelper {

    public MysqliteOpenHelper(@Nullable Context context) {
        super(context, "temp.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(id INTEGER PRIMARY KEY AUTOINCREMENT,time VARCHAR(40),name VARCHAR(20),gender VARCHAR(20),temperature VARCHAR(20),place VARCHAR(220))");
       // db.execSQL("create table contacter(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),phone VARCHAR(20),pic INTEGER(20))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
