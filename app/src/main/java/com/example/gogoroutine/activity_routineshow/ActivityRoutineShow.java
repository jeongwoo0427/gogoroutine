package com.example.gogoroutine.activity_routineshow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gogoroutine.R;

import java.util.concurrent.TimeUnit;

public class ActivityRoutineShow extends AppCompatActivity {

    private long lStartTime = 3 * 1000;

    private long lCurrentTime = lStartTime;


    private enum TimerStatus {
        STARTED, PAUSED
    }

    private TimerStatus timerStatus = TimerStatus.PAUSED;

    private ProgressBar progressBar;
    private TextView tvTime;
    private ImageView ivCenterBtn;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_show);
        ViewSetting();
        ListenerStting();
    }

    private void ViewSetting() {
        progressBar = findViewById(R.id.show_progress);
        tvTime = findViewById(R.id.show_tv_time);
        ivCenterBtn = findViewById(R.id.show_tv_centerbtn);
    }

    private void ListenerStting(){
        ivCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetStartStop();
            }
        });
    }



    private void SetStartStop(){
        if(timerStatus == TimerStatus.PAUSED){
            SetProgressBarValues();
            ivCenterBtn.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            timerStatus = TimerStatus.STARTED;
            StartCountDownTimer();

        }else{
            ivCenterBtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
            timerStatus = TimerStatus.PAUSED;
            StopCountDownTimer();
        }
    }



    private void StartCountDownTimer(){
        countDownTimer= new CountDownTimer(lCurrentTime,1000) {
            @Override
            public void onTick(long l) {
                tvTime.setText(hmsTimeFormatter(l));
                progressBar.setProgress((int) (l / 1000));

                lCurrentTime = l;
            }

            @Override
            public void onFinish() {
                lCurrentTime = lStartTime;
                tvTime.setText(hmsTimeFormatter(lCurrentTime));
                SetProgressBarValues();

                ivCenterBtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                timerStatus = TimerStatus.PAUSED;
            }
        }.start();

        countDownTimer.start();
    }

    private void StopCountDownTimer(){
        countDownTimer.cancel();
    }

    private void SetProgressBarValues(){
        progressBar.setMax((int) lStartTime / 1000);
        progressBar.setProgress((int) lCurrentTime/ 1000);
    }

    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;


    }

}