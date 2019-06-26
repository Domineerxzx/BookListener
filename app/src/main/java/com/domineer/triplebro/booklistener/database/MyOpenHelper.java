package com.domineer.triplebro.booklistener.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context, "BookListener", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //用户表
        db.execSQL("create table userInfo(_id integer primary key autoincrement,phone_number varchar(20) unique," +
                "password varchar(20),nickname varchar(20))");

        //音频信息表
        db.execSQL("create table voiceInfo(_id integer primary key autoincrement,voice_type_id integer,voice_name varchar(100),voice_path varchar(500),voice_image varchar(200),author varchar(50),time integer)");

        //音频类别信息表
        db.execSQL("create table voiceTypeInfo(_id integer primary key autoincrement,voice_type_name varchar(500))");

        //音频收藏信息表
        db.execSQL("create table voiceCollectionInfo(_id integer primary key autoincrement,voice_id integer,user_id integer)");

        //播放历史信息表
        db.execSQL("create table historyInfo(_id integer primary key autoincrement,voice_id integer unique,user_id integer)");

        //广告图片信息表
        db.execSQL("create table adPictureInfo(_id integer primary key autoincrement,ad_picture varchar(500) unique)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
