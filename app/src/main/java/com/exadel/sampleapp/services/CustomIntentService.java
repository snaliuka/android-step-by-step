package com.exadel.sampleapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class CustomIntentService extends IntentService {

    // Requires to call a constructor with the name of the service
    public CustomIntentService() {
        super(CustomIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // implement the logic for long-running operation here
    }
}
