package com.ps.dnpapp.Controller;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ps.dnpapp.R;

public class GPSActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private LocationBroadcastReceiver broadcastReceiver;
    private TextView txtLocation;
    private TextView txtProvider;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_p_s);


        broadcastReceiver = new LocationBroadcastReceiver(mainActivityInf2);

        checkLocationPermission();

        Button btnService = (Button) findViewById(R.id.btnService);
        Button btnIntentService = (Button) findViewById(R.id.btnIntentService);
        txtLocation = (TextView) findViewById(R.id.textViewLocation);
        txtProvider = (TextView) findViewById(R.id.textViewProvider);

        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "LocationService start");
                Intent myservice = new Intent(getApplicationContext(), LocationService.class);
                startService(myservice);

            }
        });

        btnIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "LocationIntentService start");
                Intent myintentservice = new Intent(getApplicationContext(), LocationIntentService.class);
                startService(myintentservice);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(LocationManager.KEY_LOCATION_CHANGED);
            registerReceiver(broadcastReceiver, intentFilter);
        } else {
            Log.d(TAG, "broadcastReceiver is null");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);

    }

    public boolean checkLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);

        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        initGPS();

                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(TAG, "Location not allowed");
                }
                return;
            }

        }
    }

    public void initGPS() {

        //Intent intent = new Intent(this, LocationBroadcastReceiver.class);
        Intent intent = new Intent(LocationManager.KEY_LOCATION_CHANGED);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(//sendBroadcast(...)
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                0,
                0,
                pendingIntent);

    }

    private MainActivityInf mainActivityInf2=new MainActivityInf() {
        @Override
        public void DisplayLocationChange(String location) {
            Log.d(TAG,"Location: "+location);
            txtLocation.setText(location);
        }

        @Override
        public void DisplayProviderEnable(boolean isEnabled) {
            if (isEnabled) {
                txtProvider.setText("Location is enabled");
            } else {
                txtProvider.setText("Location is not enabled");
            }
        }
    };

}
