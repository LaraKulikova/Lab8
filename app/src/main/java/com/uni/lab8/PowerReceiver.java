package com.uni.lab8;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PowerReceiver extends BroadcastReceiver {
    public static final String TAG = "PowerReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
String action = intent.getAction();
if (intent.ACTION_BATTERY_LOW.equals(action)) {
    Log.i(TAG, "Battery Low");
    if (intent.ACTION_BATTERY_OKAY.equals(action)) {
        Log.i(TAG, "Battery OKAY");
        if (intent.ACTION_BATTERY_CHANGED.equals(action)) {
            Log.i(TAG, "Battery CHANGED");

        }
    }
}}}
