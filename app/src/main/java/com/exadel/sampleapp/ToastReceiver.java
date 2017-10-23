package com.exadel.sampleapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.exadel.sampleapp.providers.SampleContentProvider;

public class ToastReceiver extends BroadcastReceiver {

    public static final String ACTION_TRIGGER_RECEIVER =  "com.exadel.broadcastsampleapp.receivers.ACTION_TRIGGER_RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "This is the receiver from Sample App", Toast.LENGTH_SHORT).show();

        new SampleContentProvider();
    }

}
