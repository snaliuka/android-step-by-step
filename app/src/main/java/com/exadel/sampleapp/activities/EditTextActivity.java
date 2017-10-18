package com.exadel.sampleapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.exadel.sampleapp.R;

public class EditTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        String action = getIntent().getAction();
        boolean editable = action != null && action.equals(Intent.ACTION_EDIT);
        String textToEdit = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        EditText editTextControl = ((EditText) findViewById(R.id.et_data_text));
        editTextControl.setText(textToEdit);
        editTextControl.setEnabled(editable);

        findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
