package com.exadel.sampleapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exadel.sampleapp.R;

public class SeparateThreadLoadingActivity extends AppCompatActivity {

    private TextView progressView;
    private View progressWrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_async_task_loading);

        progressView = findViewById(R.id.tv_progress_text);
        progressWrapper = findViewById(R.id.fl_progress_wrapper);

        Button launchBtn = findViewById(R.id.btn_launch_async);
        launchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int progress = 0;
                        try {
                            while (progress < 100) {
                                progress++;
                                onProgress(progress);
                                Thread.sleep(50);
                            }
                        } catch (InterruptedException ie) {
                            Log.w("EmulateLoadingTask", "Can not perform countdown");
                            onFinished(false);
                        }
                        onFinished(true);
                    }
                }).start();

                progressWrapper.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onProgress(final Integer progress) {
        // will ocure a crash as View can be updated only in a main thread!
        //progressView.setText(getString(R.string.current_progress_pattern, progress));

        // send the event to the thread view was drawn (main thread) to update view content
        progressView.post(new Runnable() {
            @Override
            public void run() {
                progressView.setText(getString(R.string.current_progress_pattern, progress));
            }
        });
    }

    public void onFinished(final Boolean result) {
        // will ocure a crash as View can be updated only in a main thread!
        // progressWrapper.setVisibility(View.GONE);

        // another way to update views
        // send the event to the main thread update views
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                progressWrapper.setVisibility(View.GONE);
                Toast.makeText(SeparateThreadLoadingActivity.this, getString(R.string.loading_result_pattern, String.valueOf(result)), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
