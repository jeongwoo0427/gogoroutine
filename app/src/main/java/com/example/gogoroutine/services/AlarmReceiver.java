package com.example.gogoroutine.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlarmReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent in = new Intent(context, UndeadService.class);
            context.startForegroundService(in);
        } else {
            Intent in = new Intent(context, UndeadService.class);
            context.startService(in);
        }

    }
}
