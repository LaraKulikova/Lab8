package com.uni.lab8;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class TimeService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    private static final String CHANNEL_ID = "TimeServiceChannel";
    private Handler handler;
    private Runnable runnable;

    private long startTime;


    @Override
    public void onCreate() {
        Log.i("TimeService", "onCreate");
        super.onCreate();
        // createNotificationChannel();
        // startTime = SystemClock.elapsedRealtime();
        handler = new Handler(Looper.getMainLooper());
        startTime = SystemClock.elapsedRealtime();
        //startForeground(1, createNotification("Timer started:"));
        startTimer();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "Time Service Channel", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(serviceChannel);
    }

    private void startTimer() {
        runnable = new Runnable() {
            @Override
            public void run() {
                long elapsedTime = SystemClock.elapsedRealtime() - startTime;
                long hours = (elapsedTime / 360000) % 24;
                long minutes = (elapsedTime / 360000) % 60;
                long sec = (elapsedTime / 1000) % 60;
                Log.i("Serice", hours + " " + minutes + " " + sec);
                if (elapsedTime < 60 * 1000) {
                    handler.postDelayed(this, 1000);
                } else {
                    stopSelf();
                }
            }
        };
        handler.post(runnable);
    }
}