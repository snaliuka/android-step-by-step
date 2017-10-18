package com.exadel.sampleapp.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

/**
 * This task is a sample of {@link AsyncTask} implementation, which emulates loading process
 *
 * <p>
 *     {@link EmulateLoadingTask.InputParams} input data for loading
 * </p>
 * <p>
 *     {@link Integer} - the second param - the type of progress
 * </p>
 * <p>
 *     {@link Boolean} - the third param - the type of result
 * </p>
 */
public class EmulateLoadingTask extends AsyncTask<EmulateLoadingTask.InputParams, Integer, Boolean> {

    private ProgressListener listener;

    public EmulateLoadingTask(ProgressListener progressListener) {
        listener = progressListener;
    }

    // this method is executed in a non-UI thread, so it can execute
    // long-running operations
    @Override
    protected Boolean doInBackground(InputParams... params) {
        if (params == null || params.length < 1) {
            return false;
        }
        InputParams emulationParams = params[0];
        int progress = 0;
        try {
            while (progress < emulationParams.count) {
                progress++;
                publishProgress(progress);
                Thread.sleep(emulationParams.stepDelay);
            }
        } catch (InterruptedException ie) {
            Log.w("EmulateLoadingTask", "Can not perform countdown");
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (listener != null) {
            listener.onProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (listener != null) {
            listener.onFinished(result);
        }
    }

    public static class InputParams {
        private final int count;
        private final long stepDelay;

        public InputParams(int count, long stepDelay) {
            this.count = count;
            this.stepDelay = stepDelay;
        }
    }

    public interface ProgressListener {
        void onProgress(Integer progress);
        void onFinished(Boolean result);
    }

}
