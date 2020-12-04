package com.example.gogoroutine.activity_routinemanager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;


import com.example.gogoroutine.R;
import com.example.gogoroutine.activity_routinemanager.Recycler.ActivityRoutineManagerAdapterDO;



public class RoutineTaskManagerDialog {

    Button btnCancel, btnComplete;
    EditText etEmoji, etName, etSummary;
    NumberPicker npHour, npMinute, npSecond;
    ImageView ivDelete;

    Context context;

    ActivityRoutineManagerAdapterDO rdo;

    public RoutineTaskManagerDialog(Context context){
        this.context = context;
    }

    public void ShowDialog(ActivityRoutineManagerAdapterDO routineTaskDO,int position){

        this.rdo = routineTaskDO;

        Dialog dialog = new Dialog(context, View.SYSTEM_UI_FLAG_FULLSCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.activity_task_manager);
        dialog.getWindow().setWindowAnimations(R.style.AnimationPopupStyle);
        dialog.show();

        ViewSetting(dialog,position);

        etEmoji.setText(rdo.getsEmoji());
        etName.setText(rdo.getsName());
        npHour.setValue(rdo.getHour());
        npMinute.setValue(rdo.getMinute());
        npSecond.setValue(rdo.getSecond());
        etSummary.setText(rdo.getsSummary());
        btnComplete.setText("저장");
        ivDelete.setVisibility(View.GONE);

    }

    void ViewSetting(final Dialog dialog,final int position) {
        btnCancel = (Button) dialog.findViewById(R.id.taskmanager_btn_cancel);
        btnComplete = (Button) dialog.findViewById(R.id.taskmanager_btn_complete);
        etEmoji = (EditText) dialog.findViewById(R.id.taskmanager_emoji);
        etName = (EditText) dialog.findViewById(R.id.taskmanager_name);
        npHour = (NumberPicker) dialog.findViewById(R.id.taskmanager_timehour);
        npMinute = (NumberPicker) dialog.findViewById(R.id.taskmanager_timeminute);
        npSecond = (NumberPicker) dialog.findViewById(R.id.taskmanager_timesecond);
        ivDelete = (ImageView) dialog.findViewById(R.id.taskmanager_iv_delete);
        etSummary = (EditText) dialog.findViewById(R.id.taskmanager_et_summary);

        npHour.setMinValue(0);
        npHour.setMaxValue(23);
        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);
        npSecond.setMinValue(0);
        npSecond.setMaxValue(59);

        btnCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnComplete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                rdo.setsName(etName.getText().toString().trim());
                rdo.setsEmoji(etEmoji.getText().toString().trim());
                rdo.setHour(npHour.getValue());
                rdo.setMinute(npMinute.getValue());
                rdo.setSecond(npSecond.getValue());
                rdo.setsSummary(etSummary.getText().toString().trim());

                ActivityRoutineManager routineManager = (ActivityRoutineManager)context;
                routineManager.ApplyDialogResult(rdo,position);
                dialog.dismiss();

            }
        });

    }

}
