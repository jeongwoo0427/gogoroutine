package com.example.gogoroutine.others;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbOpenHelper extends SQLiteOpenHelper {

    //SQLite 접속을 위한 클래스

    final static String DB_NAME = "gogoroutine.db";
    final static int DB_VERSION = 29;

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
                    "emoji TEXT NOT NULL," +
                    "summary TEXT NOT NULL)"; //외래키 참조화
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,time,emoji,summary) VALUES (1,'물 마시기',1,'','물을 마시심으로써 건강과 젊음을 유지하세요! 물은 모든 생명의 필수템이랍니다.')";
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,time,emoji,summary) VALUES (2,'잠자리 정리',1,'','세상을 바꾸고싶다면? 잠자리 정리부터 해야겠죠!')";
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,time,emoji,summary) VALUES (3,'스트레칭',3,'','아침의 준비를 시작하기 전에, 가볍게 스트레칭을 해주도록 해요! 혈액순환에 좋습니다.')";
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,time,emoji,summary) VALUES (4,'씻기',10,'','다른사람들과 어울리는 장소를 가기전에 항상 자신의 모습을 청결하게 유지할 필요가 있죠!')";
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
            qry = "INSERT INTO routineTask(routineNum,taskNum) VALUES (1,3)";
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
