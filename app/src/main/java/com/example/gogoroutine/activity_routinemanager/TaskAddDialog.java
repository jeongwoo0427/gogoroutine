package com.example.gogoroutine.activity_routinemanager;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogoroutine.R;
import com.example.gogoroutine.others.TaskDAO;

public class TaskAddDialog {

    RecyclerView recyclerView;
    Button btnDefault, btnCustom;

    Context context;


    public TaskAddDialog(Context context) {
        this.context = context;
    }

    public void showDialog(ActivityRoutineManager parentActivity) {
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

    }

    void displayList(int selectedCategory){

        TaskAddDialogAdapter adapter = new TaskAddDialogAdapter();

        Cursor cursor = new TaskDAO(context).getTaskList(selectedCategory);

        while(cursor.moveToNext()){
            adapter.addItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );
        }


        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setAdapter(adapter);

    }

    void setSwitchButton(int index) {
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


}
