package com.exadel.sampleapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.exadel.sampleapp.R;

public class LogSelfStateActivity extends AppCompatActivity {

    public static final String KEY_SAVED_LOG = "savedState";
    private TextView logView;
    private StringBuilder fullLogBuffer = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize the components of activity here
        // called once for each activity instance

        setContentView(getLayoutId());
        logView = findViewById(R.id.tv_log_messages);
        if (savedInstanceState != null) {
            String oldLog = savedInstanceState.getString(KEY_SAVED_LOG);
            fullLogBuffer.append(oldLog);
        }
        logView.setText(fullLogBuffer);
        log("onCreate(): savedInstanceState empty = " + (savedInstanceState == null));
    }

    protected int getLayoutId() {
        return R.layout.activity_self_state_logging;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // WARNING!: called after onStart() if the view was recreated!
        // you may need to do extra initialization after onStart() once
        log("onPostCreate(): savedInstanceState empty = " + (savedInstanceState == null));
    }

    @Override
    protected void onStart() {
        super.onStart();
        // activity is going to be visible
        log("onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // activity is restarted after onStop() and going to become visible
        log("onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity is going to become fully visible and allow user to interact with it
        log("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity is covered partially with another activity and is no more foreground
        // but it is still visible partially
        // Android may kill this activity after calling this callback
        log("onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // activity is completely covered with another one and no longer visible
        log("onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity is no more needed, as user left it by navigating back from it
        log("onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // activity is no more active (not in the foreground)
        // and android may kill it to free some memory
        // but it wants to save activity state and allows you
        // to save some data which can be used to restore state

        // saved state is also provided to onCreate() method and you can use it there
        outState.putString(KEY_SAVED_LOG, fullLogBuffer.toString());
        log("onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // called after onStart()!
        // you may restore the state of activity
        log("onRestoreInstanceState(): savedInstanceState empty = " + (savedInstanceState == null));
    }

    protected void log(String message) {
        // default logger from andorid SDK.
        logWithTag(LogSelfStateActivity.class.getSimpleName(), message);
    }

    protected void logWithTag(String tag, String message) {
        String formatted = String.format("%s: %s\n\n", tag, message);

        fullLogBuffer.append(formatted);

        if (logView != null) {
            logView.append(formatted);
        }

        // default logger from andorid SDK.
        Log.d(tag, message);
    }
}
