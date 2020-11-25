package com.example.gogoroutine.fragment_routines;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.gogoroutine.ActivityMain;
import com.example.gogoroutine.R;
import com.example.gogoroutine.activity_routinemanager.ActivityRoutineManager;
import com.example.gogoroutine.others.RoutineDAO;


public class FragmentRoutines_ItemDialog {

    FragmentRoutines fragmentRoutines;
    ActivityMain activityMain;


    RoutineDAO routineDAO;
    TextView tvTitle;
    Button btnModify,btnDup,btnDelete;

    Context context;

    int iRoutineNum = 0;

    private final static int ROUTINEMANAGER_REQUEST_CODE = 2;

    public FragmentRoutines_ItemDialog(Context context,FragmentRoutines fragmentRoutines,ActivityMain activityMain) {
        this.context = context;
        this.fragmentRoutines = fragmentRoutines;
        this.activityMain = activityMain;
    }

    public void showDialog(int routineNum){
        iRoutineNum = routineNum;
        routineDAO = new RoutineDAO(context);

        Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_routines_dialog);


        setViewActions(dialog,fragmentRoutines,activityMain);


        dialog.show();

    }




    void setViewActions(final Dialog dialog,final FragmentRoutines parent,final ActivityMain activityMain){
        tvTitle = (TextView)dialog.findViewById(R.id.fragmentdialog_tv_title);
        btnModify = (Button)dialog.findViewById(R.id.routinedialog_btn_modify);
        btnDup = (Button)dialog.findViewById(R.id.routinedialog_btn_dup);
        btnDelete = (Button)dialog.findViewById(R.id.routinedialog_btn_delete);


        tvTitle.setText(routineDAO.getRoutineName(iRoutineNum));

        btnModify.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ActivityRoutineManager.class);

                intent.putExtra("mode",2);

                activityMain.startActivityForResult(intent,ROUTINEMANAGER_REQUEST_CODE);

                dialog.dismiss();


            }
        });

        btnDup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnDelete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                routineDAO.deleteRoutine(iRoutineNum);
                parent.DisplayRoutineList();

                dialog.dismiss();

            }
        });


    }





}
