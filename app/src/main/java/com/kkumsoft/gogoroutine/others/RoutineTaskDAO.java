package com.kkumsoft.gogoroutine.others;

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

        String qry = "SELECT routineNum,name,hour,minute,second,emoji,summary,taskOrder " +
                "FROM routineTask " +
                "WHERE routineNum = "+iRoutineNum+" " +
                "ORDER BY taskOrder ASC";

        return db.rawQuery(qry, null);
    }

    public RoutineTaskDO GetRoutineTaskDetail(int routineNum,int order){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getReadableDatabase();

        String qry = "SELECT routineNum,name,hour,minute,second,emoji,summary,taskOrder WHERE routineNum="+routineNum+" AND taskOrder="+order;

        cursor = db.rawQuery(qry,null);
        cursor.moveToNext();

        RoutineTaskDO rdo = new RoutineTaskDO(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getInt(7)
                );

        return rdo;
    }

    public void UpdateRoutineTask(RoutineTaskDO tdo){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();

        String qry = "UPDATE task SET name='"+tdo.getName()+"', hour="+tdo.getHour()+", minute="+tdo.getMinute()+", second="+tdo.getSecond()+", emoji='"+tdo.getEmoji()+"', summary='"+tdo.getSummary()+"', category="+tdo.getOrder()+" WHERE routineNum="+tdo.getRoutineNum();

        db.execSQL(qry);
    }


    public void InsertNewRoutineTask(int routineNum,String name,int hour, int minute, int second, String emoji, String summary,int order){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();

        String qry = "INSERT INTO routineTask (routineNum,name,hour,minute,second,emoji,summary,taskOrder) VALUES ("+routineNum+",'"+name+"',"+hour+","+minute+","+second+",'"+emoji+"','"+summary+"',"+order+")";

        db.execSQL(qry);

    }



    //초기화 전용 루틴 업데이트시 항상 실행되어야 됨
    public void InitRoutineTask(int routineNum){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();

        String qry = "DELETE FROM routineTask WHERE routineNum = "+routineNum;

        db.execSQL(qry);
    }

    public void CloneRoutineTasks(int originalNum, int cloneNum){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();

        String qry = "INSERT INTO routineTask (routineNum,name,hour,minute,second,emoji,summary,taskOrder) " +
                "SELECT "+cloneNum+",name,hour,minute,second,emoji,summary,taskOrder FROM routineTask WHERE routineNum="+originalNum;

        db.execSQL(qry);

    }



}
