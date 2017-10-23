package com.exadel.sampleapp.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.exadel.sampleapp.activities.EditTextActivity;
import com.exadel.sampleapp.activities.LogSelfStateActivity;
import com.exadel.sampleapp.activities.WiFiControllingActivity;

public class BoundService extends Service {

    private HandlerThread handlerThread;

    @Override
    public void onCreate() {
        super.onCreate();
        handlerThread = new HandlerThread("BoundServiceThread");
        handlerThread.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        final Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // process message here
                String text = (String) msg.obj;
                Toast.makeText(BoundService.this, text, Toast.LENGTH_SHORT).show();

                Intent toLaunchActivity = new Intent(BoundService.this, EditTextActivity.class);
                toLaunchActivity.putExtra(Intent.EXTRA_TEXT, text);
                PendingIntent pendingIntent = PendingIntent.getActivity(BoundService.this, 1, toLaunchActivity, 0);

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(BoundService.this, "test-channel")
                                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                                .setContentTitle("Title")
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)
                                .setContentText(text);

                NotificationManagerCompat.from(BoundService.this).notify(1, mBuilder.build());
            }
        };
        return new Messenger(handler).getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }
}
