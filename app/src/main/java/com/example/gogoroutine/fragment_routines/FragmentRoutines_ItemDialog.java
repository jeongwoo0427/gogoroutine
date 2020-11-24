package com.example.gogoroutine.fragment_routines;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.example.gogoroutine.R;


public class FragmentRoutines_ItemDialog {

    Context context;

    public FragmentRoutines_ItemDialog(Context context) {
        this.context = context;
    }

    public void showDialog(){

        Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_routines_dialog);

        dialog.show();

    }

}
