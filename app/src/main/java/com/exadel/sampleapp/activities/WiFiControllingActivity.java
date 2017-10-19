package com.exadel.sampleapp.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.exadel.sampleapp.R;
import com.exadel.sampleapp.views.LabeledSwitch;
import com.exadel.sampleapp.views.ProgressView;
import com.exadel.sampleapp.views.adapters.AvailableNetworksAdapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WiFiControllingActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS_CODE = 101;

    private static final String[] LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private LabeledSwitch wifiToggle;
    private RecyclerView networksList;
    private ProgressView progressView;

    private WifiManager wifiManager;
    private BroadcastReceiver wifiScanResultReceiver = createWifiScanReceiver();

    private AvailableNetworksAdapter networksAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_controlling);

        // initialize variables for views
        wifiToggle = findViewById(R.id.ls_wifi_toggle);
        progressView = findViewById(R.id.cv_progress);

        networksList = findViewById(R.id.rv_networks_list);
        networksList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // get the system service, which works with wifi module
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // set the adapter to list view
        networksAdapter = new AvailableNetworksAdapter(this, wifiManager.getScanResults());
        networksList.setAdapter(networksAdapter);

        wifiToggle.setSwitchListener(new LabeledSwitch.SwitchListener() {
            @Override
            public void onSwitched(LabeledSwitch.Option option, Object optionValue) {
                boolean enabled = option == LabeledSwitch.Option.RIGHT;
                wifiManager.setWifiEnabled(enabled);
                if (enabled == false) {
                    networksAdapter.updateDataList(Collections.<ScanResult>emptyList());
                    progressView.hide();
                } else {
                    progressView.show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(wifiScanResultReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        // check current state
        int state = wifiManager.getWifiState();
        switch (state) {
            case WifiManager.WIFI_STATE_ENABLED:
            case WifiManager.WIFI_STATE_ENABLING:
                wifiToggle.setStateEnabled(true);
                break;
            default:
                wifiToggle.setStateEnabled(false);
                break;
        }
        progressView.show();
        loadNetworksList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiScanResultReceiver);
        progressView.hide();
    }

    @NonNull
    private BroadcastReceiver createWifiScanReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                progressView.hide();
                List<ScanResult> scanResults = Collections.emptyList();
                try {
                    scanResults = wifiManager.getScanResults();
                } catch (SecurityException e) {
                    Toast.makeText(WiFiControllingActivity.this, R.string.message_grant_permissions_location, Toast.LENGTH_SHORT).show();
                    return;
                }
                networksAdapter.updateDataList(scanResults);
                networksList.setAdapter(networksAdapter);
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Map<String, Integer> permissionsMap = new HashMap<>();
        for (int i = 0; i < permissions.length; i++) {
            permissionsMap.put(permissions[i], grantResults[i]);
        }
        Integer permissionResult = permissionsMap.get(Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionResult != null && permissionResult == PackageManager.PERMISSION_GRANTED) {
            loadNetworksList();
        }
    }

    private void loadNetworksList() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(WiFiControllingActivity.this, LOCATION_PERMISSIONS, REQUEST_PERMISSIONS_CODE);
            return;
        }

        if (wifiManager.isWifiEnabled() == false) {
            wifiManager.setWifiEnabled(true);
            return;
        }
        wifiManager.startScan();
    }
}
