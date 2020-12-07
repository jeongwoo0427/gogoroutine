package com.kkumsoft.gogoroutine.activity_main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.kkumsoft.gogoroutine.R;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorIconBlue));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivitySplash.this,ActivityMain.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }
}