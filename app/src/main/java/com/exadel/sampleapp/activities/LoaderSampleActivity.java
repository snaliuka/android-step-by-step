package com.exadel.sampleapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exadel.sampleapp.R;
import com.exadel.sampleapp.loaders.EmulateLoadingLoader;
import com.exadel.sampleapp.views.ProgressView;

public class LoaderSampleActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Boolean> {

    private static final int LOADER_ID = 10001;

    private static final String ARG_COUNT = "arg_count";
    private static final String ARG_DELAY = "arg_delay";

    private ProgressView progressView;
    private TextView dataCaption;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_loader_sample);

        progressView = findViewById(R.id.cv_progress);
        dataCaption = findViewById(R.id.tv_data_loaded_caption);

        Bundle args = new Bundle();
        args.putInt(ARG_COUNT, 100);
        args.putLong(ARG_DELAY, 50L);
        getSupportLoaderManager().initLoader(LOADER_ID, args, LoaderSampleActivity.this);
        progressView.show();
    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_ID) {
            int count = args.getInt(ARG_COUNT);
            long delay = args.getLong(ARG_DELAY);
            return new EmulateLoadingLoader(this, count, delay);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean result) {
        progressView.hide();
        dataCaption.setVisibility(View.VISIBLE);
        if (loader.getId() == LOADER_ID) {
            Toast.makeText(this, getString(R.string.loading_result_pattern_rotate, String.valueOf(result)), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) { }
}
