package com.exadel.sampleapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by sergey on 10/3/17.
 */

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent implicit = new Intent(Intent.ACTION_VIEW);
        implicit.setData(Uri.fromFile(new File("/sdcard/cats.jpg")));
        context.startActivity(implicit);

        Intent explicit = new Intent(context, SyncService.class);
        context.startService(explicit);
        explicit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
}
