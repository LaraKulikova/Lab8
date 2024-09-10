package com.uni.lab8;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

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
        Log.i("TimeServise", "onCreate");
        super.onCreate();
        createNotificationChanel();
        long elapsedTime = SystemClock.elapsedRealtime() - startTime;
        long hours = (elapsedTime / 3600000) % 24;
        long minutes = (elapsedTime / 60000) % 24;
        long sec = (elapsedTime / 1000) % 24;
        handler = new Handler(Looper.getMainLooper());
        startTime = SystemClock.elapsedRealtime();
        // startForeground(1, CreateNotification("Timer started"));

        startTimer();
    }

    private void createNotificationChanel() {
        NotificationChannel serviceChanel = new NotificationChannel(CHANNEL_ID, "Tima Service Chanel", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(serviceChanel);
    }

    private void startTimer() {
        runnable = new Runnable() {
            @Override
            public void run() {
                long elapsedTime = SystemClock.elapsedRealtime() - startTime;
                long hours = (elapsedTime / 3600000) % 24;
                long minutes = (elapsedTime / 60000) % 24;
                long sec = (elapsedTime / 1000) % 24;
               // Notification notification = createNotification(String.format("Time: %0.2d:%0.2d", hours, minutes, sec));
                // startForeground(1, notification);
                Log.i("ServiceLog", "run: " + hours + " " + minutes + " " + sec);
                if (elapsedTime < 60 * 1000) {
                    handler.postDelayed(this, 1000);
                } else {
                    //Log.i("ServiceRun", "run: " + hours + " " + minutes + " " + sec);
                    stopSelf();
                }
            }

            ;
        };
        handler.post(runnable);
    }

//    private Notification createNotification(String contextText) {
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//        return new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("Service Test")
//                .setContentText(contextText)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentIntent(pendingIntent)
//                .build();
//
//    }

}
