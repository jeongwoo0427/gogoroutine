package com.kkumsoft.gogoroutine.activity_main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.kkumsoft.gogoroutine.R;

public class ActivityChoose extends AppCompatActivity {

    private final static int CHOOSE_RESULT_ROUTINE = 1;
    private final static int CHOOSE_RESULT_GO =2;




    ImageButton btnCancel,btnRoutine,btnGo;
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorDarkBlue));


            btnCancel = (ImageButton) findViewById(R.id.choose_btn_cancel);
            btnRoutine = (ImageButton) findViewById(R.id.choose_btn_add_routine);
            btnGo = (ImageButton) findViewById(R.id.choose_btn_add_go);


        rl=(RelativeLayout)findViewById(R.id.choose_rl);
        }


        btnCancel.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rl.setOnClickListener(new RelativeLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnRoutine.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(CHOOSE_RESULT_ROUTINE);
                onBackPressed();
            }
        });

        btnGo.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(CHOOSE_RESULT_GO);
                onBackPressed();
            }
        });

    }
}