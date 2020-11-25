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

    public RoutineDAO(Context context){
        this.context = context;
    }


    public Cursor getRoutineList(){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getReadableDatabase();

        String qry = "SELECT * FROM routine";

        return db.rawQuery(qry, null);

    }

    public String getRoutineName(int routineNum){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM routine WHERE routineNum = " + routineNum, null);

        cursor.moveToNext();

        return cursor.getString(1);
    }

    public void deleteRoutine(int routineNum){
        dbOpenHelper = new DbOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();

        String qry = "DELETE FROM routine WHERE routineNum="+routineNum;

        database.execSQL(qry);
    }


    public void insertNewRoutine(RoutineDO routineDO){
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
                ")";

        database.execSQL(qry);
    }

}
