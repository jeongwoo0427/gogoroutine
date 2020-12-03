package com.example.gogoroutine.others;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RoutineTaskDAO {

    Context context;

    DbOpenHelper dbOpenHelper;
    SQLiteDatabase db;
    Cursor cursor;


    public RoutineTaskDAO(Context context){
        this.context = context;
    }

    public Cursor GetRoutineTaskList(int iRoutineNum){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getReadableDatabase();

        String qry = "SELECT R.routineNum,T.taskNum,T.name,T.hour,T.minute,T.second,T.emoji,T.summary,T.category,R.taskOrder " +
                "FROM routineTask R " +
                "INNER JOIN task T ON R.taskNum= T.taskNum " +
                "WHERE R.routineNum = "+iRoutineNum+" " +
                "ORDER BY R.taskOrder ASC";

        return db.rawQuery(qry, null);
    }


    public void InsertNewRoutineTask(int routineNum,int taskNum,int order){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();

        String qry = "INSERT INTO routineTask (routineNum,taskNum,taskOrder) VALUES ("+routineNum+","+taskNum+","+order+")";

        db.execSQL(qry);

    }

    //초기화 전용 루틴 업데이트시 항상 실행되어야 됨
    public void InitRoutineTask(int routineNum){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();

        String qry = "DELETE FROM routineTask WHERE routineNum = "+routineNum;

        db.execSQL(qry);
    }



}
