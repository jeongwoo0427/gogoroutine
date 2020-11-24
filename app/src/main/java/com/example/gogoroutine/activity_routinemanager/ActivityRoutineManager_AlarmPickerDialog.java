package com.example.gogoroutine.activity_routinemanager;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;

import com.example.gogoroutine.R;

//알람 선택시 띄울 다이얼로그를 정의하는 클래스
public class ActivityRoutineManager_AlarmPickerDialog {

    private Context context;

    public ActivityRoutineManager_AlarmPickerDialog(Context context){
        this.context = context;
    }


    public void showDialogForResult(final Button parentAlarmButton){

        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.activity_routine_manager_alarmdialog);

        dialog.show();

        final Button btnOK = (Button) dialog.findViewById(R.id.alarmdialog_btn_ok);
        final Button btnCancel = (Button) dialog.findViewById(R.id.alarmdialog_btn_cancel);


    }


}
