package com.exadel.sampleapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.exadel.sampleapp.fragments.SelfLoggingStateFragment;

import java.util.LinkedList;
import java.util.List;

public class LogSelfStateActivityWithFragments extends LogSelfStateActivity implements SelfLoggingStateFragment.LogWriter {

    public static final String DYNAMIC_FRAGMENT_TAG = "DynamicFragment";
    private List<Pair<String, String>> fragmentsCachedLogs = new LinkedList<>();
    private boolean created = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // don't do that, it's just for sample :)
        created = true;

        ToggleButton toggleFragmentBtn = findViewById(R.id.tb_toggle_fragment_btn);
        toggleFragmentBtn.setOnCheckedChangeListener(createToggleListener());
    }

    protected int getLayoutId() {
        return R.layout.activity_self_state_logging_with_fragments;
    }

    @Override
    public void log(CharSequence tag, String message) {
        if (created) {
            logWithTag(tag.toString(), message);
        } else {
            fragmentsCachedLogs.add(new Pair<>(tag.toString(), message));
        }
    }

    @Override
    protected void logWithTag(String tag, String message) {
        // flush cache
        if (!fragmentsCachedLogs.isEmpty()) {
            for (Pair<String, String> logPair : fragmentsCachedLogs) {
                super.logWithTag(logPair.first, logPair.second);
            }
            fragmentsCachedLogs.clear();
        }
        super.logWithTag(tag, message);


        Fragment fragment = new SelfLoggingStateFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(fragment, "TagToIdentify")
                .addToBackStack("state_name")
                .commit();  // commit changes
    }

    @NonNull
    private CompoundButton.OnCheckedChangeListener createToggleListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (isChecked) {
                    Fragment fragment = SelfLoggingStateFragment.newInstance("Dynamic fragment");
                    fragmentTransaction.add(R.id.fl_fragment_container, fragment, DYNAMIC_FRAGMENT_TAG);
                } else {
                    Fragment fragment = fragmentManager.findFragmentByTag(DYNAMIC_FRAGMENT_TAG);
                    if (fragment != null) {
                        fragmentTransaction.remove(fragment);
                    }
                }
                // !!! Important!! It's Required to commit changes
                fragmentTransaction.commit();
            }
        };
    }

}
