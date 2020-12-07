package com.kkumsoft.gogoroutine.fragment_routines;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.kkumsoft.gogoroutine.R;

public class FragmentRoutines_DayDialog  {

    Context context;
    FragmentRoutines fragmentRoutines;

    Button btnToday,btnWhole;

    public FragmentRoutines_DayDialog (Context context,FragmentRoutines fragmentRoutines){
        this.context = context;
        this.fragmentRoutines = fragmentRoutines;
    }

    public void ShowDialog(){

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_routines_dialog_day);

        dialog.show();

        btnToday = dialog.findViewById(R.id.fragmentdialog_btn_today);
        btnWhole = dialog.findViewById(R.id.fragmentdialog_btn_whole);

        btnToday.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentRoutines.selectedDay =1;
                fragmentRoutines.ChangeDay();
                dialog.dismiss();
            }
        });

        btnWhole.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentRoutines.selectedDay = 2;
                fragmentRoutines.ChangeDay();
                dialog.dismiss();
            }
        });
    }
}
