package com.uni.lab8;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

    @SuppressLint("NewApi")
    @Override
    public void onCreate() {
        Log.i("TimeService", "onCreate");
        super.onCreate();
        createNotificationChannel();
        handler = new Handler(Looper.getMainLooper());
        startTime = SystemClock.elapsedRealtime();
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
                long hours = (elapsedTime / 3600000) % 24;
                long minutes = (elapsedTime / 60000) % 60;
                long sec = (elapsedTime / 1000) % 60;

                Notification notification = createNotification(String.format("Time: %02d:%02d:%02d", hours, minutes, sec));
                startForeground(1, notification);
                Log.i("Service", hours + " " + minutes + " " + sec);

                if (elapsedTime < 60 * 1000) { // 60 секунд
                    handler.postDelayed(this, 5000); // Обновление каждые 5 секунд
                } else {
                    stopSelf(); // Остановка службы по истечении минуты
                }
            }
        };
        handler.post(runnable);
    }

    private Notification createNotification(String contentText) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Service test")
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
    }
}