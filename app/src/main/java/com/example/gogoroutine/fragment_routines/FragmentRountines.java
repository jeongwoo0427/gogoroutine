package com.example.gogoroutine.fragment_routines;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogoroutine.anothers.DbOpenHelper;
import com.example.gogoroutine.R;

import java.util.ArrayList;

public class FragmentRountines extends Fragment {

    DbOpenHelper dbOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routines,container,false);




        recyclerView = view.findViewById(R.id.routine_recyclerview);

        DisplayRoutineList();

        return view;
    }

    public void DisplayRoutineList(){

        dbOpenHelper = new DbOpenHelper(getContext());
        sqLiteDatabase = dbOpenHelper.getReadableDatabase();
        recyclerViewAdapter = new RecyclerViewAdapter();

        String qry= "SELECT * FROM routine";

        Cursor cursor = sqLiteDatabase.rawQuery(qry,null);

        while(cursor.moveToNext()){
            //recyclerViewAdapter.addItem(1,"Morning",0,9,0,"0",0,0,0); 테스트용 코드
            recyclerViewAdapter.addItem(
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


        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

        recyclerView.setAdapter(recyclerViewAdapter);


    }
}
