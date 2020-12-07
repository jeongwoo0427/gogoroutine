package com.kkumsoft.gogoroutine.activity_routineshow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.kkumsoft.gogoroutine.R;

public class ActivityComplete extends AppCompatActivity {

    Animation anim;
    Animation anim2;
    TextView tvText;
    Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        tvText = (TextView)findViewById(R.id.complete_tv_text);
        btnExit = (Button)findViewById(R.id.complete_btn_exit);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorLight));


        anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.activity_complete_alpha_anim);
        anim2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.activity_complete_alpha_anim2);

        tvText.startAnimation(anim);
        btnExit.startAnimation(anim2);


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}