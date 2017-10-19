package com.exadel.sampleapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.exadel.sampleapp.R;
import com.exadel.sampleapp.views.adapters.SampleSelectionSpinnerAdapter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Button launchSampleButton;
    private Spinner sampleSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the layout for the activity
        setContentView(R.layout.activity_main);

        // find views inside layout by it's ID
        launchSampleButton = findViewById(R.id.btn_launch_sample);

        sampleSelection = findViewById(R.id.sp_sample_selection);
        SampleSelectionSpinnerAdapter adapter = new SampleSelectionSpinnerAdapter(this,
                Arrays.asList(
                        R.string.launch_text_edit,
                        R.string.explore_life_cycle,
                        R.string.explore_life_cycle_with_fragments,
                        R.string.explore_async_tasks,
                        R.string.explore_custom_view,
                        R.string.explore_recycler_view
                ));
        sampleSelection.setAdapter(adapter);
        initListeners();
    }

    private void initListeners() {
        launchSampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer itemResId = (Integer) sampleSelection.getSelectedItem();
                switch (itemResId) {
                    case R.string.launch_text_edit:
                        launchEditor();
                        break;
                    case R.string.explore_life_cycle:
                        launchActivity(LogSelfStateActivity.class);
                        break;
                    case R.string.explore_life_cycle_with_fragments:
                        launchActivity(LogSelfStateActivityWithFragments.class);
                        break;
                    case R.string.explore_async_tasks:
                        launchActivity(AsyncTaskLoadingActivity.class);
                        break;
                    case R.string.explore_custom_view:
                        launchActivity(WiFiControllingActivity.class);
                        break;
                    case R.string.explore_recycler_view:
                        launchActivity(WiFiControllingActivity.class);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Not mapped", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void launchEditor() {
        Intent viewTextIntent = new Intent(Intent.ACTION_VIEW);
        viewTextIntent.putExtra(Intent.EXTRA_TEXT, "This is test to be viewed");
        startActivity(viewTextIntent);
    }

    private void launchActivity(Class<? extends Activity> cls) {
        Intent launchIntent = new Intent(MainActivity.this, cls);
        startActivity(launchIntent);
    }


}
