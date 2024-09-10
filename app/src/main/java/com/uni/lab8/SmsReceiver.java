package com.uni.lab8;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("sms", "Вам пришел sms");
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus!=null){
                for (Object pdu:pdus){
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])pdu);
                    String sender = smsMessage.getDisplayOriginatingAddress();
                    String smsMess = smsMessage.getDisplayMessageBody();
                    Log.d("SMS","SMS from "+sender+":"+smsMess);
                }
            }
        }
    }
}
