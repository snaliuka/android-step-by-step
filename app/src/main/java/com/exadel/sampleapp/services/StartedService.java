package com.exadel.sampleapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartedService extends Service {

    private ExecutorService executor = null;
    private int lastId = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        lastId = startId;
        executor.submit(new Runnable() {
            @Override
            public void run() {
                // do some stuff
                //
                Toast.makeText(StartedService.this, "Started service is running", Toast.LENGTH_SHORT).show();
                stopSelf(Math.min(startId, lastId));
            }
        });
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }
}
