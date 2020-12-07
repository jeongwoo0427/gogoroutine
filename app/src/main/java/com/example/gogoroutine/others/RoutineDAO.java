package com.example.gogoroutine.others;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gogoroutine.activity_routinemanager.ActivityRoutineManager;

public class RoutineDAO {

    Context context;

    DbOpenHelper dbOpenHelper;
    SQLiteDatabase db;
    Cursor cursor;

    public RoutineDAO(Context context) {
        this.context = context;
    }


    public Cursor getRoutineList() {
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getReadableDatabase();

        String qry = "SELECT * FROM routine";

        return db.rawQuery(qry, null);

    }


    public RoutineDO getRoutineDetails(int routineNum) {
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getReadableDatabase();

        String qry = "SELECT * FROM routine WHERE routineNum=" + routineNum;

        cursor = db.rawQuery(qry, null);

        cursor.moveToNext();

        RoutineDO routineDO = new RoutineDO(
                cursor.getInt(0), //루틴번호
                cursor.getString(1), //루틴이름
                cursor.getInt(2), //알림여부
                cursor.getInt(3), //시작시각
                cursor.getInt(4), //시작분
                cursor.getString(5), //선택된요일
                cursor.getInt(6), //알람음
                cursor.getInt(7), //소리여부
                cursor.getInt(8) //진동여부
        );

        return routineDO;
    }


    public String getRoutineName(int routineNum) {
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM routine WHERE routineNum = " + routineNum, null);

        cursor.moveToNext();

        return cursor.getString(1);
    }


    public int insertNewRoutine(RoutineDO routineDO) {
        dbOpenHelper = new DbOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();

        String qry = "INSERT INTO routine(name,isNoticeEnable,startHour,startMinute,selectedWeeks,alamMode,isSound,isVibration) VALUES('" +
                routineDO.getName() + "'," +
                routineDO.getIsAlamEnable() + "," +
                routineDO.getStartHour() + "," +
                routineDO.getStartMinute() + ",'" +
                routineDO.getSelectedWeeks() + "'," +
                routineDO.getAlameMode() + "," +
                routineDO.getIsSound() + "," +
                routineDO.getIsVibration() +
                ");";

        database.execSQL(qry);

        qry = "SELECT last_insert_rowid();";

        cursor =  database.rawQuery(qry,null);
        cursor.moveToFirst();
        int index = cursor.getInt(0);
        return index; //최근 입력한 루틴의 id값을 알아야 routineTask를 추가할 때 사용할 수 있음
    }





    public void updateRoutine(RoutineDO routineDO) {
        dbOpenHelper = new DbOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();

        String qry = "UPDATE routine SET " +
                "name = '"+routineDO.getName()+"', "+
                "isNoticeEnable = "+routineDO.getIsAlamEnable()+", " +
                "startHour = "+routineDO.getStartHour()+", " +
                "startMinute ="+routineDO.getStartMinute()+", " +
                "selectedWeeks = '"+routineDO.getSelectedWeeks()+"', " +
                "alamMode = "+routineDO.getAlameMode()+", " +
                "isSound = "+routineDO.getIsSound()+", " +
                "isVibration = "+routineDO.getIsVibration()+" " +
                " WHERE routineNum="+routineDO.getRoutineNum();

        database.execSQL(qry);

    }

    public void deleteRoutine(int routineNum) {
        dbOpenHelper = new DbOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();

        String qry = "DELETE FROM routineTask WHERE routineNum=" + routineNum;
        database.execSQL(qry);

        qry = "DELETE FROM routine WHERE routineNum=" + routineNum;
        database.execSQL(qry);
    }


}
