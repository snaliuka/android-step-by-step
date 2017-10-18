package com.exadel.sampleapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exadel.sampleapp.R;
import com.exadel.sampleapp.asynctasks.EmulateLoadingTask;

public class AsyncTaskLoadingActivity extends AppCompatActivity implements EmulateLoadingTask.ProgressListener {

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
                EmulateLoadingTask.InputParams inputParams = new EmulateLoadingTask.InputParams(100, 50L);
                EmulateLoadingTask task = new EmulateLoadingTask(AsyncTaskLoadingActivity.this);
                task.execute(inputParams);
                progressWrapper.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onProgress(Integer progress) {
        progressView.setText(getString(R.string.current_progress_pattern, progress));
    }

    @Override
    public void onFinished(Boolean result) {
        progressWrapper.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.loading_result_pattern, String.valueOf(result)), Toast.LENGTH_SHORT).show();
    }
}
