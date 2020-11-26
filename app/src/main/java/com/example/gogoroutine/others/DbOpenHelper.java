package com.example.gogoroutine.others;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbOpenHelper extends SQLiteOpenHelper {

    //SQLite 접속을 위한 클래스

    final static String DB_NAME = "gogoroutine.db";
    final static int DB_VERSION = 27;

    public DbOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String qry = "";

        //루틴테이블 기본 설정
        {
            qry = "CREATE TABLE routine(" +
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

            qry = "INSERT INTO routine(routineNum,name,isNoticeEnable,startHour,startMinute,selectedWeeks,alamMode,isSound,isVibration) VALUES(1,'아침루틴',0,9,0,'0',0,0,0)";
            sqLiteDatabase.execSQL(qry);

        }


        //할일테이블 기본 설정
        {
            qry = "CREATE TABLE task(" +
                    "taskNum INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "time INT NOT NULL," +
                    "emoji TEXT NOT NULL)"; //외래키 참조화
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,time,emoji) VALUES (1,'물 마시기',1,'')";
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,time,emoji) VALUES (2,'이불 정리',1,'')";
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,time,emoji) VALUES (3,'스트레칭',3,'')";
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,time,emoji) VALUES (4,'씻기',10,'')";
            sqLiteDatabase.execSQL(qry);
        }


        //루틴에 대한 할일 기본 설정
        {
            qry = "CREATE TABLE routineTask(" +
                    "routineNum INTEGER NOT NULL," +
                    "taskNum INTEGER NOT NULL," +
                    "CONSTRAINT fk_routine FOREIGN KEY (routineNum) REFERENCES routine(routineNum)," +
                    "CONSTRAINT fk_task FOREIGN KEY (taskNum) REFERENCES task(taskNum));";
            sqLiteDatabase.execSQL(qry);


            qry = "INSERT INTO routineTask(routineNum,taskNum) VALUES (1,1)";
            sqLiteDatabase.execSQL(qry);
            qry = "INSERT INTO routineTask(routineNum,taskNum) VALUES (1,2)";
            sqLiteDatabase.execSQL(qry);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String qry = "DROP TABLE IF EXISTS routineTask";
        sqLiteDatabase.execSQL(qry);
        qry = "DROP TABLE IF EXISTS task";
        sqLiteDatabase.execSQL(qry);
        qry = "DROP TABLE IF EXISTS routine";
        sqLiteDatabase.execSQL(qry);
        this.onCreate(sqLiteDatabase);

    }
}
