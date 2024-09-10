package com.uni.lab8;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenState extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())){
            Log.d("Screen", "ACTION_SCREEN_ON");
        }
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
            Log.d("Screen", "ACTION_SCREEN_OFF");
        }

    }
}
