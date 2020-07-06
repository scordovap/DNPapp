package com.ps.dnpapp.Controller.BroadcastGPS;


import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ps.dnpapp.Controller.GPS.CameraActivity;
import com.ps.dnpapp.Controller.GPS.FragmentMaps;
import com.ps.dnpapp.R;

public class GPSActivity extends View implements SensorEventListener {
    private static final String TAG = "MainActivity";
    private LocationBroadcastReceiver broadcastReceiver;

    CameraActivity mapFragment;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    Context context;

    public GPSActivity(Context context) {
        super(context);
        this.context = context;
        broadcastReceiver = new LocationBroadcastReceiver(mainActivityInf2);
        broadcastReceiver = new LocationBroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Double lat= (Double) intent.getExtras().get("lat");
                Double lon= (Double) intent.getExtras().get("lon");
                //puntos.setLatitude(lat);
                //puntos.setLongitude(lon);
                mapa(lat,lon);
            }
        };
        checkLocationPermission();
        initGPS();
    }

    public boolean checkLocationPermission() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);

        return true;

    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                    }

                } else {
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
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                0,
                0,
                pendingIntent);

    }


    private MainActivityInf mainActivityInf2 = new MainActivityInf() {

        @Override
        public void DisplayLocationChange(String location) {
            Log.d(TAG, "Location: " + location);

        }

        @Override
        public void DisplayProviderEnable(boolean isEnabled) {
            if (isEnabled) {
                Toast.makeText(context, "is Enable", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context.getApplicationContext(), "is Disable", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public CameraActivity getMapFragment() {
        return mapFragment;
    }
    public void setLocalizationActi(CameraActivity activity){
        this.mapFragment=activity;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void mapa(double latitude, double longitude) {
        FragmentMaps fragment = new FragmentMaps();
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", new Double(latitude));
        bundle.putDouble("lon", new Double(longitude));
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getMapFragment().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mapaLoca, fragment, null);
        fragmentTransaction.commit();
    }

}
