package com.kkumsoft.gogoroutine.activity_taskmanager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.kkumsoft.gogoroutine.R;
import com.kkumsoft.gogoroutine.activity_routinemanager.AddTaskDialog.TaskAddDialog;

public class DialogTaskManager {

    Button btnCancel;
    Button btnComplete;

    Context context;

    public DialogTaskManager(Context context){
        this.context = context;
    }

    public void ShowDialog(final TaskAddDialog parent){

        final Dialog dialog = new Dialog(context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_task_manager);
        dialog.show();

        btnCancel = (Button)dialog.findViewById(R.id.taskmanager_btn_cancel);
        btnComplete = (Button)dialog.findViewById(R.id.taskmanager_btn_complete);

        btnCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.ShowToastMessage(1);
                dialog.dismiss();
            }
        });

    }


}
