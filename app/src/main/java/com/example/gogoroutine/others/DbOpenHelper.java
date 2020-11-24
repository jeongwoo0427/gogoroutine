package com.example.gogoroutine.others;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbOpenHelper extends SQLiteOpenHelper {

    //SQLite 접속을 위한 클래스

    final static String DB_NAME = "gogoroutine.db";
    final static int DB_VERSION = 21;

    public DbOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry = "CREATE TABLE routine(" +
                "routineNum INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "isNoticeEnable INTEGER NOT NULL, " + //0또는 1 정수형으로
                "startHour INTERGER NOT NULL," +
                "startMinute INTEGER NOT NULL," +
                "selectedWeeks TEXT NOT NULL," +
                "alamMode INTEGER NOT NULL," +
                "isSound INTERGER NOT NULL," +
                "isVibration INTEGER NOT NULL" +
                ")";

        sqLiteDatabase.execSQL(qry);

        qry = "INSERT INTO routine(name,isNoticeEnable,startHour,startMinute,selectedWeeks,alamMode,isSound,isVibration) VALUES('아침루틴',0,9,0,'0',0,0,0)";
        sqLiteDatabase.execSQL(qry);

        qry = "CREATE TABLE go(" +
                "goNum INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "time TEXT NOT NULL," +
                "entity TEXT NOT NULL," +
                "CONSTRAINT routinenum_fk FOREIGN KEY (routineNum) " +
                "REFERENCES routine(routineNum))"; //외래키 참조화

        sqLiteDatabase.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String qry = "DROP TABLE IF EXISTS go";
        sqLiteDatabase.execSQL(qry);
        qry = "DROP TABLE IF EXISTS routine";
        sqLiteDatabase.execSQL(qry);
        this.onCreate(sqLiteDatabase);

    }
}
