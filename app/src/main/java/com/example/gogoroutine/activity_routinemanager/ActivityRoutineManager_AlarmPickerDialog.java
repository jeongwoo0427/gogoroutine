package com.example.gogoroutine.activity_routinemanager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

        //다이얼로그의 실제 백그라운드가 하얀색이면 전체적으로 각진 사각형으로 보이므로 이부분을 투명하게 해주면
        //shape_dialogbackground.xml로 적용한 무딘 사각형이 제대로 보기게 됨
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.activity_routine_manager_alarmdialog);

        dialog.show();

        final Button btnOK = (Button) dialog.findViewById(R.id.alarmdialog_btn_ok);
        final Button btnCancel = (Button) dialog.findViewById(R.id.alarmdialog_btn_cancel);


    }


}
