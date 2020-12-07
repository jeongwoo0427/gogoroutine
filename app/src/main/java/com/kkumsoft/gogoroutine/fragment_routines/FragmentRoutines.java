package com.kkumsoft.gogoroutine.fragment_routines;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kkumsoft.gogoroutine.activity_main.ActivityMain;
import com.kkumsoft.gogoroutine.R;
import com.kkumsoft.gogoroutine.others.RoutineDAO;

import java.util.Calendar;

public class FragmentRoutines extends Fragment {

    RoutineDAO routineDAO;

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    Button btnDay;

    int selectedDay = 1; //1이면 오늘 2면 전체

    ActivityMain activityMain;


    public FragmentRoutines(ActivityMain activityMain){
        this.activityMain = activityMain;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routines, container, false);

        recyclerView = view.findViewById(R.id.routine_recyclerview);

        btnDay = view.findViewById(R.id.main_btn_day);

        if(selectedDay == 1){
            btnDay.setText("오늘");
        }else{
            btnDay.setText("전체");
        }

        btnDay.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentRoutines_DayDialog dialog = new FragmentRoutines_DayDialog(activityMain,FragmentRoutines.this);
                dialog.ShowDialog();
            }
        });

        DisplayRoutineList();

        return view;
    }

    public void ChangeDay(){

        if(selectedDay ==1){
            //오늘
            btnDay.setText("오늘");
            DisplayRoutineList();
        }else if(selectedDay ==2){
            //전체
            btnDay.setText("전체");
            DisplayRoutineList();
        }

    }

    public void DisplayRoutineList() {

        int iDayOfWeek=0;
        Calendar cal =  Calendar.getInstance();
        iDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        routineDAO = new RoutineDAO(getContext());

        recyclerViewAdapter = new RecyclerViewAdapter(FragmentRoutines.this, activityMain);

        Cursor cursor = routineDAO.getRoutineList();

        while (cursor.moveToNext()) {
            //recyclerViewAdapter.addItem(1,"Morning",0,9,0,"0",0,0,0); 테스트용 코드

            if(cursor.getString(5).contains(Integer.toString(iDayOfWeek))||selectedDay ==2) {
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


        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        recyclerView.setAdapter(recyclerViewAdapter);


    }
}
