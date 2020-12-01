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

    public Cursor getTaskList(int selectedCategory){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getReadableDatabase();

        //String qry = "SELECT * FROM task";
        String qry = "SELECT * FROM task WHERE category="+selectedCategory;

        return db.rawQuery(qry, null);
    }



}
