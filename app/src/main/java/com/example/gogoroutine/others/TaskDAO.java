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

    public void InsertNewTask(TaskDO tdo){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();

        String qry = "INSERT INTO task(name,hour,minute,second,emoji,summary,category) VALUES ('"+tdo.getName()+"',"+tdo.getHour()+","+tdo.getMinute()+","+tdo.getSecond()+",'"+tdo.getEmoji()+"','"+tdo.getSummary()+"',2)";

        db.execSQL(qry);

    }



}
