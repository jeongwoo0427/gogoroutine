package com.example.gogoroutine.others;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TaskDAO {

    Context context;

    DbOpenHelper dbOpenHelper;
    SQLiteDatabase db;
    Cursor cursor;


    public TaskDAO(Context context){
        this.context = context;
    }

    public Cursor GetTaskList(int selectedCategory){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getReadableDatabase();

        //String qry = "SELECT * FROM task";
        String qry = "SELECT * FROM task WHERE category="+selectedCategory;

        return db.rawQuery(qry, null);
    }

    public TaskDO GetTaskDetail(int taskNum){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getReadableDatabase();
        TaskDO taskdo =null;

        String qry = "SELECT taskNum,name,hour,minute,second,emoji,summary,category FROM task WHERE taskNum ="+taskNum;
        cursor = db.rawQuery(qry,null);
        if(cursor.moveToNext()) {

            taskdo = new TaskDO(
                    taskNum,
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7)
            );

        }
        return taskdo;
    }


    public void InsertNewTask(TaskDO tdo){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();

        String qry = "INSERT INTO task(name,hour,minute,second,emoji,summary,category) VALUES ('"+tdo.getName()+"',"+tdo.getHour()+","+tdo.getMinute()+","+tdo.getSecond()+",'"+tdo.getEmoji()+"','"+tdo.getSummary()+"',2)";

        db.execSQL(qry);

    }

    public void UpdateTask(TaskDO tdo,int tasknum){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();

        String qry = "UPDATE task SET name='"+tdo.getName()+"', hour="+tdo.getHour()+", minute="+tdo.getMinute()+", second="+tdo.getSecond()+", emoji='"+tdo.getEmoji()+"', summary='"+tdo.getSummary()+"', category="+tdo.getCategory()+" WHERE taskNum="+tasknum;

        db.execSQL(qry);

    }

    public void DeleteTask(int tasknum){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();

        String qry = "DELETE FROM routineTask WHERE taskNum="+tasknum;
        db.execSQL(qry);

        qry = "DELETE FROM task WHERE taskNum = "+tasknum;
        db.execSQL(qry);


    }



}
