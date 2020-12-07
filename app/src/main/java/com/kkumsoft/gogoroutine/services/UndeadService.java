package com.kkumsoft.gogoroutine.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.kkumsoft.gogoroutine.R;
import com.kkumsoft.gogoroutine.activity_routineshow.ActivityRoutineShow;

import java.util.Calendar;

public class UndeadService extends Service {

    public static Intent serviceIntent = null;
    ActivityRoutineShow activityRoutineShow;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceIntent =intent;
        initializeNotification();
        activityRoutineShow = (ActivityRoutineShow)getApplicationContext();
        Toast.makeText(activityRoutineShow,"실행됨",Toast.LENGTH_SHORT).show();

        return START_STICKY;
    }

    public void initializeNotification(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1");
        builder.setSmallIcon(R.drawable.botton_addgo);

        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText("현재 루틴이 켜져있습니다.");
        style.setBigContentTitle(null);
        style.setSummaryText("여기를 클릭하면 앱을 켭니다");

        builder.setContentText(null);
        builder.setContentTitle(null);
        builder.setOngoing(true);

        builder.setStyle(style);
        builder.setWhen(0);
        builder.setShowWhen(false);


        Intent notificationIntent = new Intent(this, ActivityRoutineShow.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            manager.createNotificationChannel(new NotificationChannel("1","gogoroutine undead",NotificationManager.IMPORTANCE_NONE));
        }

        Notification notification = builder.build();
        startForeground(1,notification);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND,3);
        Intent intent = new Intent(this,AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this,0,intent,0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 3);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
