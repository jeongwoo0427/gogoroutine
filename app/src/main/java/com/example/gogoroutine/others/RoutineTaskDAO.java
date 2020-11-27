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

    public Cursor getRoutinTaskList(int iRoutineNum){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getReadableDatabase();

        String qry = "SELECT R.routineNum,T.taskNum,T.name,T.time,T.emoji,T.summary,T.category,R.taskOrder " +
                "FROM routineTask R " +
                "INNER JOIN task T ON R.taskNum= T.taskNum " +
                "WHERE R.routineNum = "+iRoutineNum+" " +
                "ORDER BY R.taskOrder ASC";

        return db.rawQuery(qry, null);
    }



}
