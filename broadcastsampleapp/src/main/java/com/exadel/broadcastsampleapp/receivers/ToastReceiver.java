package com.exadel.broadcastsampleapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ToastReceiver extends BroadcastReceiver {

    public static final String ACTION_TRIGGER_RECEIVER =  "com.exadel.broadcastsampleapp.receivers.ACTION_TRIGGER_RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "This is the receiver from BroadcastSamleApp", Toast.LENGTH_SHORT).show();
    }
}
