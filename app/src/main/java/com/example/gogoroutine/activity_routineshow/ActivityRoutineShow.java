package com.example.gogoroutine.activity_routineshow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gogoroutine.R;
import com.example.gogoroutine.activity_main.ActivityMain;
import com.example.gogoroutine.others.RoutineTaskDAO;
import com.example.gogoroutine.others.RoutineTaskDO;
import com.example.gogoroutine.services.UndeadService;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ActivityRoutineShow extends AppCompatActivity {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    public static final String NOTIFICATION_CHANNEL_FIN = "10002";

    private int iRoutineNum = 0;
    ArrayList<RoutineTaskDO> list = new ArrayList<RoutineTaskDO>();
    private RoutineTaskDO currentRdo;
    private int iLastIndex = 0;
    private int iCurrentIndex = 0;

    private long lStartTime = 10* 1000; //10초
    private long lCurrentTime = lStartTime;
    private int iOverTime = 0;

    private boolean isOverTime= false;

    private enum TimerStatus {
        STARTED, PAUSED
    }

    private TimerStatus timerStatus = TimerStatus.PAUSED;

    private ProgressBar progressBar;
    private TextView tvName,tvSummary,tvTime,tvEmoji,tvPage;
    private ImageView ivCenterBtn,ivLeftBtn,ivRightBtn, ivExit;
    private CountDownTimer countDownTimer;
    private TimerTask timerTask;
    private Timer timer = new Timer();


    private Intent foregroundServiceIntent;

    NotificationManager notificationManager;
    NotificationCompat.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_show);

        ViewSetting();
        ListenerStting();
        StopForeGround();
        GetRoutineTasks();
        ApplyCurrentTask(iCurrentIndex);
    }

    private void GetRoutineTasks(){
        Intent intent = getIntent();
        iRoutineNum = intent.getIntExtra("num",0);
        Cursor cursor = new RoutineTaskDAO(this).GetRoutineTaskList(iRoutineNum);

        while(cursor.moveToNext()){
            AddItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7)
            );
        }

        iLastIndex = list.size()-1;

    }

    private void ApplyCurrentTask(int currentindex){
        currentRdo = (RoutineTaskDO)list.get(currentindex);

        int convertedTime = currentRdo.getHour()*60*60 + currentRdo.getMinute()*60 + currentRdo.getSecond();

        tvPage.setText((iCurrentIndex+1)+"/"+(iLastIndex+1));
        lStartTime = convertedTime * 1000;
        lCurrentTime = lStartTime;
        iOverTime = 0;
        isOverTime = false;
        tvName.setText(currentRdo.getName());
        tvSummary.setText(currentRdo.getSummary());
        tvEmoji.setText(currentRdo.getEmoji());
        tvTime.setText(hmsTimeFormatter(lStartTime));
        progressBar.setBackground(getDrawable(R.drawable.drawable_circle_light_gray));
        tvTime.setTextColor(getResources().getColor(R.color.colorDarkBlue));
        StopCountDownTimer();
        StopStopWatch();
        SetProgressBarValues();

    }

    private void AddItem(int routineNum,String name,int hour,int minute,int second,String emoji, String summary, int order){
        RoutineTaskDO rdo = new RoutineTaskDO();
        rdo.setRoutineNum(routineNum);
        rdo.setName(name);
        rdo.setHour(hour);
        rdo.setMinute(minute);
        rdo.setSecond(second);
        rdo.setEmoji(emoji);
        rdo.setSummary(summary);
        rdo.setOrder(order);

        list.add(rdo);
    }

    private void ViewSetting() {
        progressBar = findViewById(R.id.show_progress);
        tvTime = findViewById(R.id.show_tv_time);
        ivCenterBtn = findViewById(R.id.show_iv_centerbtn);
        ivLeftBtn = findViewById(R.id.show_iv_leftbtn);
        ivRightBtn = findViewById(R.id.show_iv_rightbtn);
        ivExit = findViewById(R.id.show_iv_exit);
        tvName = findViewById(R.id.show_tv_name);
        tvSummary = findViewById(R.id.show_tv_summary);
        tvEmoji = findViewById(R.id.show_tv_emoji);
        tvPage = findViewById(R.id.show_tv_page);

    }

    private void ListenerStting() {
        ivCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetStartStop();
            }
        });

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowExitDialog();
            }
        });

        ivRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iCurrentIndex<iLastIndex){
                    iCurrentIndex++;
                    ApplyCurrentTask(iCurrentIndex);

                    if(timerStatus == TimerStatus.STARTED){ //타이머가 계속 움직이고 있다면.. 다음껏도 계속 재생된 상태로
                        StartCountDownTimer();
                    }

                    Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibe.vibrate(100);


                }else if(iCurrentIndex >=iLastIndex){
                    ShowExitDialog();
                }
            }
        });

        ivLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iCurrentIndex>0){

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityRoutineShow.this);
                    builder.setTitle("이전으로");
                    builder.setMessage("현재상태가 초기화 됩니다.\n정말 이전으로 돌아가시겠습니까?");
                    builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            iCurrentIndex--;
                            ApplyCurrentTask(iCurrentIndex);
                            ivCenterBtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                            timerStatus = TimerStatus.PAUSED;
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.show();
                }else if(iCurrentIndex <=0){
                    ShowExitDialog();
                }
            }
        });

    }


    private void SetStartStop() {
        if (timerStatus == TimerStatus.PAUSED) {
            SetProgressBarValues();
            ivCenterBtn.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            timerStatus = TimerStatus.STARTED;

            if(!isOverTime) {
                StartCountDownTimer();
                TimeNotification(); //최초 노티피케이션 등록
            }else{
                StartStopWatch();
            }

        } else {
            ivCenterBtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
            timerStatus = TimerStatus.PAUSED;

            if(!isOverTime) {
                StopCountDownTimer();
            }else{
                StopStopWatch();
            }
        }
    }


    private void StartCountDownTimer() {
        countDownTimer = new CountDownTimer(lCurrentTime, 1000) {
            @Override
            public void onTick(long l) {
                tvTime.setText(hmsTimeFormatter(l));
                progressBar.setProgress((int) (l / 1000));
                lCurrentTime = l;

                builder.setContentTitle(hmsTimeFormatter(l));
                notificationManager.notify(1, builder.build());
            }

            @Override
            public void onFinish() {
               // lCurrentTime = lStartTime;
                tvTime.setText(hmsTimeFormatter(lCurrentTime));
                SetProgressBarValues();

                progressBar.setBackground(getDrawable(R.drawable.drawable_circle_light_pink));
                tvTime.setTextColor(getResources().getColor(R.color.colorLightPink));
                FinishNotification();
                StartStopWatch();
                isOverTime = true;

                cancel();
            }
        }.start();

        countDownTimer.start();
    }


    private void StopCountDownTimer() {
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


    private void StartStopWatch(){

        timerTask = new TimerTask(){


            @Override
            public void run() {
                iOverTime ++;
                tvTime.post(new Runnable() {
                    @Override
                    public void run() {
                        long lOverTime = iOverTime*1000;
                        tvTime.setText("+"+hmsTimeFormatter(lOverTime));
                        builder.setContentTitle(hmsTimeFormatter(lOverTime)+" 초과");
                        notificationManager.notify(1, builder.build());
                    }
                });
            }
        };
        timer.schedule(timerTask,0,1000);

    }

    private void StopStopWatch(){
        if(timerTask != null){
            timerTask.cancel();
            timerTask = null;
        }
    }


    private void SetProgressBarValues() {
        progressBar.setMax((int) lStartTime / 1000);
        progressBar.setProgress((int) lCurrentTime / 1000);
    }

    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;


    }

    @Override
    public void onBackPressed() {
        ShowExitDialog();
    }

    private void ShowExitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityRoutineShow.this);
        builder.setTitle("종료");
        builder.setMessage("정말로 종료하시겠어요?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                startActivity(new Intent(ActivityRoutineShow.this, ActivityMain.class)
                        .setAction(Intent.ACTION_MAIN)
                        .addCategory(Intent.CATEGORY_LAUNCHER)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );


                StopCountDownTimer();
                StopStopWatch();
                if(notificationManager != null) {
                    notificationManager.cancelAll();
                }
                finish();

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        builder.show();
    }

    private void TimeNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.add_green)) //BitMap 이미지 요구
                .setContentTitle("고고루틴")
                .setContentText(currentRdo.getEmoji()+currentRdo.getName())
                // 더 많은 내용이라서 일부만 보여줘야 하는 경우 아래 주석을 제거하면 setContentText에 있는 문자열 대신 아래 문자열을 보여줌
                //.setStyle(new NotificationCompat.BigTextStyle().bigText("더 많은 내용을 보여줘야 하는 경우..."))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true);

        builder.setOngoing(true);

        builder.setWhen(0);
        builder.setShowWhen(false);




        Intent intent = new Intent(this, ActivityRoutineShow.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //OREO API 26 이상에서는 채널 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남
            CharSequence channelName = "노티페케이션 채널";
            String description = "오레오 이상";
            int importance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
            channel.setDescription(description);

            // 노티피케이션 채널을 시스템에 등록
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);

        } else
            builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

        assert notificationManager != null;

        //notificationManager.notify(1234, builder.build()); // 고유숫자로 노티피케이션 동작시킴


    }

    private void FinishNotification(){

        NotificationCompat.Builder finishBuilder;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        finishBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_FIN)
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.add_green)) //BitMap 이미지 요구
                .setContentTitle(currentRdo.getEmoji()+currentRdo.getName()+"(이)가 끝났어요")
                .setContentText("여기를 클릭해서 다음 습관으로 넘어가세요~!")// 더 많은 내용이라서 일부만 보여줘야 하는 경우 아래 주석을 제거하면 setContentText에 있는 문자열 대신 아래 문자열을 보여줌
                //.setStyle(new NotificationCompat.BigTextStyle().bigText("더 많은 내용을 보여줘야 하는 경우..."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);


        Intent intent = new Intent(this, ActivityRoutineShow.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        finishBuilder.setContentIntent(pendingIntent);

        //OREO API 26 이상에서는 채널 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            finishBuilder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남
            CharSequence channelName = "노티페케이션 채널";
            String description = "오레오 이상";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_FIN, channelName, importance);
            channel.setDescription(description);

            // 노티피케이션 채널을 시스템에 등록
            assert manager != null;
            manager.createNotificationChannel(channel);

        } else
            finishBuilder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

        assert manager != null;

        manager.notify(2, finishBuilder.build()); // 고유숫자로 노티피케이션 동작시킴


    }


    private void StartForeGround() {
        if (null == UndeadService.serviceIntent) {
            foregroundServiceIntent = new Intent(this, UndeadService.class);
            // startService(foregroundServiceIntent);
            //Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show();
        } else {
            foregroundServiceIntent = UndeadService.serviceIntent;
            // Toast.makeText(this,"already",Toast.LENGTH_SHORT).show();
        }

    }

    private void StopForeGround() {
        if (null != foregroundServiceIntent) {
            //  stopService(foregroundServiceIntent);
            foregroundServiceIntent = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        StopForeGround();

    }
}