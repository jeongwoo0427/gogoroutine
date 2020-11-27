package com.example.gogoroutine.activity_routinemanager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.gogoroutine.R;

public class TaskAddDialog {

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



    }

    void viewSetting(Dialog dialog) {
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
    }


}
