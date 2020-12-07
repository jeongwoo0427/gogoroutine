package com.kkumsoft.gogoroutine.activity_routinemanager.AddTaskDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kkumsoft.gogoroutine.R;
import com.kkumsoft.gogoroutine.activity_main.ActivityMain;
import com.kkumsoft.gogoroutine.activity_routinemanager.ActivityRoutineManager;
import com.kkumsoft.gogoroutine.activity_routinemanager.OnTaskItemClickListener;
import com.kkumsoft.gogoroutine.activity_taskmanager.ActivityTaskManager;
import com.kkumsoft.gogoroutine.others.TaskDAO;

public class TaskAddDialog {



    RecyclerView recyclerView;
    Button btnDefault, btnCustom;
    TextView tvAddNew;
    Context context;

    int from = 1; //0일때는 메인액티비티에서 호출 1일때는 루틴매니저에서 호출, 아이템에서 +버튼이 보일지 말지를 결정해야됨



    public TaskAddDialog(Context context,int from) {
        this.context = context;
        this.from = from;

    }

    public void showDialog() {
        Dialog dialog = new Dialog(context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.activity_routine_manager_addtaskdialog);

        dialog.show();

        viewSetting(dialog);

        displayList(1);

    }



    void viewSetting(Dialog dialog) {

        recyclerView = (RecyclerView) dialog.findViewById(R.id.addtaskdialog_recycler);
        btnDefault = (Button) dialog.findViewById(R.id.addtaskdialog_tg_default);
        btnCustom = (Button) dialog.findViewById(R.id.addtaskdialog_tg_custom);
        tvAddNew = (TextView)dialog.findViewById(R.id.addtaskdialog_addnew);


        setSwitchButton(1);


        btnDefault.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSwitchButton(1);
            }
        });

        btnCustom.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSwitchButton(2);
            }
        });

        tvAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(from == 1) {
                    Intent intent = new Intent(context, ActivityTaskManager.class);
                    ((ActivityRoutineManager) context).startActivityForResult(intent, ActivityRoutineManager.REQUEST_TASKMANAGER_OF_DIALOG);
                }else if (from ==0){
                    Intent intent = new Intent(context, ActivityTaskManager.class);
                    ((ActivityMain) context).startActivityForResult(intent, ActivityMain.REQUEST_TASKMANAGER_OF_DIALOG);
                }
            }
        });

    }

    void displayList(int selectedCategory){

        TaskAddDialogAdapter adapter = new TaskAddDialogAdapter(context);

        adapter.from = this.from;

        Cursor cursor = new TaskDAO(context).GetTaskList(selectedCategory);

        while(cursor.moveToNext()){
            adapter.addItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7)
            );
        }


        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setAdapter(adapter);

        final TaskAddDialogAdapter fAdapter = adapter;

        if(from ==1) {
            adapter.setOnClickListener(new OnTaskItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(context, ActivityTaskManager.class);
                    intent.putExtra("mode", 2);

                    TaskAddDialogAdapterDO tdo = (TaskAddDialogAdapterDO) fAdapter.getItem(position);
                    intent.putExtra("num", tdo.getTaskNum());
                    ((ActivityRoutineManager) context).startActivityForResult(intent, ActivityRoutineManager.REQUEST_TASKMANAGER_OF_DIALOG);
                }
            });

        }
        else if(from ==0){ //메인액티비티에서 호출한 경우

            adapter.setOnClickListener(new OnTaskItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(context, ActivityTaskManager.class);
                    intent.putExtra("mode", 2);
                    TaskAddDialogAdapterDO tdo = (TaskAddDialogAdapterDO) fAdapter.getItem(position);
                    intent.putExtra("num", tdo.getTaskNum()); //습관관리 액티비티 실행준비

                    ((ActivityMain) context).startActivityForResult(intent, ActivityMain.REQUEST_TASKMANAGER_OF_DIALOG);
                }
            });
        }
    }


    public void setSwitchButton(int index) {
        switch (index) {
            case 1:

                btnDefault.setBackgroundResource(R.drawable.shape_button_darkblue);
                btnDefault.setTextColor(Color.WHITE);

                btnCustom.setBackgroundResource(R.drawable.shape_button_white);
                btnCustom.setTextColor(Color.BLACK);

                break;

            case 2:

                btnDefault.setBackgroundResource(R.drawable.shape_button_white);
                btnDefault.setTextColor(Color.BLACK);

                btnCustom.setBackgroundResource(R.drawable.shape_button_darkblue);
                btnCustom.setTextColor(Color.WHITE);

                break;

        }
        displayList(index);
    }


    public void ShowToastMessage(int i){
        Toast.makeText(context,"태스크관리 다이얼로그가 종료되었음 : "+i,Toast.LENGTH_LONG).show();
    }


}
