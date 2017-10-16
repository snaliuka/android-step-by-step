package com.exadel.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button launchEditorBtn;
    private Button exploreLifeCycleBtn;
    private Button exploreLifeCycleWithFragmentsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the layout for the activity
        setContentView(R.layout.activity_main);

        // find views inside layout by it's ID
        launchEditorBtn = findViewById(R.id.btn_launch_editor);
        // in sdk < 26 (8.0) you need to cast result
        exploreLifeCycleBtn = (Button) findViewById(R.id.btn_explore_lc);
        // in sdk < 26 (8.0) you need to cast result
        exploreLifeCycleWithFragmentsBtn = (Button) findViewById(R.id.btn_explore_lc_with_fragments);

        initListeners();
    }

    private void initListeners() {
        launchEditorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewTextIntent = new Intent(Intent.ACTION_VIEW);
                viewTextIntent.putExtra(Intent.EXTRA_TEXT, "This is test to be viewed");
                startActivity(viewTextIntent);
            }
        });


        exploreLifeCycleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLifeCycleIntent = new Intent(MainActivity.this, LogSelfStateActivity.class);
                startActivity(goToLifeCycleIntent);
            }
        });

        exploreLifeCycleWithFragmentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLifeCycleIntent = new Intent(MainActivity.this, LogSelfStateActivityWithFragments.class);
                startActivity(goToLifeCycleIntent);
            }
        });

    }


}
