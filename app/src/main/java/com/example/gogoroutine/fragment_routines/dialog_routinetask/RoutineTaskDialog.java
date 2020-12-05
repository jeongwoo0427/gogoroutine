package com.example.gogoroutine.fragment_routines.dialog_routinetask;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogoroutine.R;
import com.example.gogoroutine.activity_main.ActivityMain;
import com.example.gogoroutine.activity_routineshow.ActivityRoutineShow;
import com.example.gogoroutine.fragment_routines.FragmentRoutines;
import com.example.gogoroutine.fragment_routines.FragmentRoutines_ItemDialog;
import com.example.gogoroutine.others.RoutineDAO;
import com.example.gogoroutine.others.RoutineTaskDAO;

public class RoutineTaskDialog {

    FragmentRoutines fragmentRoutines;
    ActivityMain activityMain;

    RoutineTaskDialogAdapter adapter;
    RoutineTaskDAO routineTaskDAO;

    RecyclerView recyclerView;
    TextView tvName, tvTime;
    Button btnGo;
    ImageView ivMore;

    Context context;

    private int iRoutineNum = 0;

    public RoutineTaskDialog(Context context, FragmentRoutines fragmentRoutines, ActivityMain activityMain) {
        this.context = context;
        this.fragmentRoutines = fragmentRoutines;
        this.activityMain = activityMain;
    }

    public void ShowDialog(int routineNum) {

        this.iRoutineNum = routineNum;

        Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_main_dialog);
        dialog.show();

        ViewSetting(dialog);

    }


    void ViewSetting(final Dialog dialog) {

        adapter = new RoutineTaskDialogAdapter();
        routineTaskDAO = new RoutineTaskDAO(context);
        recyclerView = dialog.findViewById(R.id.main_dialog_recycler);
        tvName = dialog.findViewById(R.id.main_dialog_tv_title);
        tvTime = dialog.findViewById(R.id.main_dialog_tv_time);
        btnGo = dialog.findViewById(R.id.main_dialog_btn_go);
        ivMore = dialog.findViewById(R.id.main_dialog_iv_more);

        tvName.setText(new RoutineDAO(context).getRoutineName(iRoutineNum));

        int iTotalHour = 0; //총 합한시간 구하기
        int iTotalMinute = 0;
        int iTotalSecond = 0;

        Cursor cursor = routineTaskDAO.GetRoutineTaskList(iRoutineNum);

        while (cursor.moveToNext()) {
            adapter.AddItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7)
            );
            iTotalHour += cursor.getInt(2);
            iTotalMinute += cursor.getInt(3);
            iTotalSecond += cursor.getInt(4);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setAdapter(adapter);

        tvTime.setText("총 "+ConvertTimeToString(iTotalHour,iTotalMinute,iTotalSecond));

        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentRoutines_ItemDialog itemDialog = new FragmentRoutines_ItemDialog(context, fragmentRoutines, activityMain);
                itemDialog.showDialog(iRoutineNum);
                dialog.dismiss();
            }
        });

        btnGo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activityMain, ActivityRoutineShow.class);
                intent.putExtra("num",iRoutineNum);
                Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);
                activityMain.startActivity(intent);

                dialog.dismiss();
            }
        });


    }

    public String ConvertTimeToString(int hour,int minute,int second){
        String result ="";

        if(hour>0){
            result += hour+"시간 ";
        }
        if(minute>0){
            result += minute+"분 ";
        }
        if(second>0){
            result += second+"초";
        }

        return result;
    }


}
