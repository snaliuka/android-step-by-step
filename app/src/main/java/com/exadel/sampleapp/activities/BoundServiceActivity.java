package com.exadel.sampleapp.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.exadel.sampleapp.R;
import com.exadel.sampleapp.services.BoundService;

public class BoundServiceActivity extends AppCompatActivity {

    private Messenger outcomeMessenger;
    private ServiceConnection currentConnection;

    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service_sample);
        editText = findViewById(R.id.et_data_text);
        Button sendButton = findViewById(R.id.btn_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendText(editText.getText().toString());
            }
        });
    }

    private void sendText(String text) {
        if (outcomeMessenger != null) {
            Message message = Message.obtain();
            message.obj = text;
            try {
                outcomeMessenger.send(message);
            } catch (RemoteException e) {
                Log.e(BoundServiceActivity.class.getName(), e.getMessage());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                outcomeMessenger = new Messenger(binder);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                outcomeMessenger = null;
            }
        };
        bindService(new Intent(this, BoundService.class), currentConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (outcomeMessenger != null) {
            outcomeMessenger = null;
            unbindService(currentConnection);
        }
    }

}
