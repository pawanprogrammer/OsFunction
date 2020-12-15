package com.trishasofttech.osfunction;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_wifion, btnwifioff, btn_bleon, btn_bleoff, btn_camera, btn_video, btn_torch,
            btn_gpson,btn_airplane;
    WifiManager wifiManager;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_gpson = findViewById(R.id.btn_gpson);
        btn_airplane = findViewById(R.id.btn_airplane);
        btn_torch = findViewById(R.id.btn_torch);
        btn_video = findViewById(R.id.btn_video);
        btn_camera = findViewById(R.id.btn_camera);
        btn_bleoff = findViewById(R.id.btn_bleoff);
        btn_bleon = findViewById(R.id.btn_bleon);
        btn_wifion = findViewById(R.id.btn_wifion);
        btnwifioff = findViewById(R.id.btn_wifioff);
        /*pass wifimanager access of your mobile wifi*/
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        /*get the bluethooth instance*/
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        btn_wifion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*check whether the wifi is disabled*/
                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                } else {
                    Toast.makeText(MainActivity.this, "Wifi is already enabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnwifioff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*check whether the wifi is disabled*/
                if (wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                } else {
                    Toast.makeText(MainActivity.this, "Wifi is already disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_bleon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*check whether the bluetooth is disabled*/
                if (!bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.enable();
                }
            }
        });

        btn_bleoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.disable();
                }
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*to open the camera call intent action */
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
        });

        /*long click to capture the video*/
        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivity(intent);

            }
        });

        btn_torch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                String cameraId = null; // Usually front camera is at 0 position.
                try {
                    cameraId = camManager.getCameraIdList()[0];
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        camManager.setTorchMode(cameraId, true);
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }

            }
        });

        btn_gpson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locationprovider =
                        Settings.Secure.getString(getContentResolver(),
                                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

                /*to check whether mobile contain gps function*/
                if (!locationprovider.contains("gps"))
                {
                    /*gps is disable*/
                    Intent mygps = new Intent();
                    mygps.setClassName("com.android.settings",
                            "com.android.settings.widget.SettingsAppWidgetProvider");
                    mygps.addCategory(Intent.CATEGORY_ALTERNATIVE);
                    mygps.setData(Uri.parse("3"));
                    sendBroadcast(mygps);
                }
            }
        });
/*
        btn_airplane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Manifest.permission.WRITE_SETTINGS != PackageManager.PERMISSION_GRANTED) {
                        // Do stuff here
                        Settings.Global.putInt(getContentResolver(),
                                Settings.Global.AIRPLANE_MODE_ON, 1);
                        // broadcast an intent to inform
                        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                        intent.putExtra("state", false);
                        sendBroadcast(intent);
                    }
                    else {
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }

            }*//*
        });*/
    }
}