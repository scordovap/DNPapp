package com.ps.dnpapp.Controller.BroadcastGPS;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.io.Serializable;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class LocationIntentServiceBroadcast extends Service implements LocationListener {

    private static final String TAG = "LocationIntentServiceBroadcast";

    @SuppressLint("LongLogTag")
    @Override
    public void onLocationChanged(Location location) {
        Intent intent = new Intent(LocationManager.KEY_LOCATION_CHANGED);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(LocationManager.KEY_LOCATION_CHANGED, (Serializable) location);
        intent.putExtras(mBundle);
        sendBroadcast(intent);
        Log.d(TAG, location.getLatitude() + "," + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("LongLogTag")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0,
                    0,
                    this);

            Log.d(TAG, "location started");
        }

        Intent intent2 = new Intent(LocationBroadcastReceiver.ACTION);
        intent2.putExtra(LocationBroadcastReceiver.LOCATION_CHANGE, "hhhhhhhh");
        sendBroadcast(intent2);

        return START_STICKY;
    }

}
