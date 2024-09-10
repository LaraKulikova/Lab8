package com.uni.lab8;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class PowerReceiver extends BroadcastReceiver {
    public static final String TAG = "PowerReceiver";
    private static final String CHANNEL_ID = "BatteryChannel";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_BATTERY_LOW.equals(action)) {
            Log.i(TAG, "Battery Low");
            createNotification(context, "Battery Low", "The battery level is low.");
        } else if (Intent.ACTION_BATTERY_OKAY.equals(action)) {
            Log.i(TAG, "Battery OKAY");
            createNotification(context, "Battery OKAY", "The battery level is okay.");
        } else if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
            Log.i(TAG, "Battery CHANGED");
            // Здесь вы можете добавить дополнительную логику, если это необходимо.
        }
    }

    private void createNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Создание канала уведомлений для Android 8.0 и выше
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Battery Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Создание уведомления
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_foreground) //
                .build();


        notificationManager.notify((int) System.currentTimeMillis(), notification); // Используем текущее время как уникальный ID
    }
}