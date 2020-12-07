package com.kkumsoft.gogoroutine.fragment_routines;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.kkumsoft.gogoroutine.activity_main.ActivityMain;
import com.kkumsoft.gogoroutine.R;
import com.kkumsoft.gogoroutine.activity_routinemanager.ActivityRoutineManager;
import com.kkumsoft.gogoroutine.others.RoutineDAO;
import com.kkumsoft.gogoroutine.others.RoutineDO;
import com.kkumsoft.gogoroutine.others.RoutineTaskDAO;


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

        final String sRoutineName = routineDAO.getRoutineName(iRoutineNum);
        tvTitle.setText(sRoutineName);

        btnModify.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ActivityRoutineManager.class);

                intent.putExtra("mode",2);
                intent.putExtra("num",iRoutineNum);

                activityMain.startActivityForResult(intent,ROUTINEMANAGER_REQUEST_CODE);

                dialog.dismiss();


            }
        });

        btnDup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle(sRoutineName);
                builder.setMessage("해당 루틴을 복제하시겠습니까? \n하위 할 일들도 복제됩니다.");
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setPositiveButton("복제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        RoutineDO routineDO = routineDAO.getRoutineDetails(iRoutineNum);
                        int cloneNum = routineDAO.insertNewRoutine(routineDO);

                        new RoutineTaskDAO(context).CloneRoutineTasks(iRoutineNum,cloneNum);

                        parent.DisplayRoutineList();

                    }
                });

                builder.show();
                dialog.dismiss();


            }
        });

        btnDelete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(sRoutineName);
                builder.setMessage("해당 루틴을 정말로 삭제하시겠습니까?");
                builder.setPositiveButton("삭제",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        routineDAO.deleteRoutine(iRoutineNum);
                        parent.DisplayRoutineList();
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
                dialog.dismiss();

            }
        });


    }





}
