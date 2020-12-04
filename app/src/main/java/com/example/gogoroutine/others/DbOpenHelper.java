package com.example.gogoroutine.others;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbOpenHelper extends SQLiteOpenHelper {

    //SQLite 접속을 위한 클래스

    final static String DB_NAME = "gogoroutine.db";
    final static int DB_VERSION = 41;

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
                    "hour INT NOT NULL," +
                    "minute INT NOT NULL,"+
                    "second INT NOT NULL,"+
                    "emoji TEXT NOT NULL," +
                    "summary TEXT NOT NULL," +
                    "category INT NOT NULL)"; //카테고리는 기본생성인지 사용자가 만든 것인지 구별용 기본:1 사용자:2
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,hour,minute,second,emoji,summary,category) VALUES (1,'물 마시기',0,1,0,'\uD83E\uDD5B','물을 마시심으로써 건강과 젊음을 유지하세요! 물은 모든 생명의 필수템이랍니다.',1)";
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,hour,minute,second,emoji,summary,category) VALUES (2,'잠자리 정리',0,1,0,'\uD83D\uDECF','세상을 바꾸고싶다면? 잠자리 정리부터 해야겠죠!',1)";
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,hour,minute,second,emoji,summary,category) VALUES (3,'스트레칭',0,3,0,'\uD83E\uDDD8','아침의 준비를 시작하기 전에, 가볍게 스트레칭을 해주도록 해요! 혈액순환에 좋습니다.',1)";
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,hour,minute,second,emoji,summary,category) VALUES (4,'씻기',0,10,0,'\uD83D\uDEBF','다른사람들과 어울리는 장소를 가기전에 항상 자신의 모습을 청결하게 유지할 필요가 있죠!',1)";
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,hour,minute,second,emoji,summary,category) VALUES (5,'책상 정리',0,5,0,'\uD83D\uDCDA','책상을 보기좋을 정도로만 정리해 두는게 어때요?',1)";
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,hour,minute,second,emoji,summary,category) VALUES (6,'방 청소',0,10,0,'\uD83E\uDDF9','건장을 좌우하는 것은 환경적 요인이 크답니다. 청결한 방을 만들어 봅시다!',1)";
            sqLiteDatabase.execSQL(qry);

            qry = "INSERT INTO task(taskNum,name,hour,minute,second,emoji,summary,category) VALUES (7,'빨래 하기',0,10,0,'\uD83E\uDDFA','빨래를 합시다!',1)";
            sqLiteDatabase.execSQL(qry);

        }


        //루틴에 대한 할일 기본 설정
        {
            qry = "CREATE TABLE routineTask(" +
                    "routineNum INTEGER," +
                    "name TEXT NOT NULL," +
                    "hour INT NOT NULL," +
                    "minute INT NOT NULL,"+
                    "second INT NOT NULL,"+
                    "emoji TEXT NOT NULL," +
                    "summary TEXT NOT NULL," +
                    "taskOrder INT NOT NULL," +
                    "CONSTRAINT fk_routine FOREIGN KEY (routineNum) REFERENCES routine(routineNum))";
            sqLiteDatabase.execSQL(qry);


            qry = "INSERT INTO routineTask(routineNum,name,hour,minute,second,emoji,summary,taskOrder) VALUES (1,'물마시기',0,10,0,'\uD83E\uDD5B','물을 마시심으로써 건강과 젊음을 유지하세요! 물은 모든 생명의 필수템이랍니다.',1)";
            sqLiteDatabase.execSQL(qry);
            qry = "INSERT INTO routineTask(routineNum,name,hour,minute,second,emoji,summary,taskOrder) VALUES (1,'책상 정리',0,5,0,'\uD83D\uDCDA','책상을 보기좋을 정도로만 정리해 두는게 어때요?',2)";
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
