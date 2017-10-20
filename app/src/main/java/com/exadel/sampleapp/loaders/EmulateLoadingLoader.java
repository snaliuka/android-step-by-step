package com.exadel.sampleapp.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class EmulateLoadingLoader extends AsyncTaskLoader<Boolean> {

    private int count = -1;
    private long stepDelay = -1;

    public EmulateLoadingLoader(Context context, int count, long stepDelay) {
        super(context);
        this.count = count;
        this.stepDelay = stepDelay;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Boolean loadInBackground() {
        // check that params were initialized.
        if (count < 0 || stepDelay < 0) {
            return false;
        }

        int progress = 0;
        try {
            while (progress < count) {
                progress++;
                Thread.sleep(stepDelay);
            }
        } catch (InterruptedException ie) {
            Log.w("EmulateLoadingLoader", "Can not perform countdown");
            return false;
        }
        return true;
    }
}
