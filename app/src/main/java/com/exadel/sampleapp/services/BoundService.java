package com.exadel.sampleapp.services;

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

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(BoundService.this, "test-channel")
                                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                                .setContentTitle("Title")
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
