package com.ps.dnpapp.Controller.BroadcastGPS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.ps.dnpapp.Controller.GPS.CameraActivity;


public class LocationBroadcastReceiver extends BroadcastReceiver {
    private String TAG = "LocationBroadcastReceiver";
    private MainActivityInf mainActivityInf;
    public static int UNIQUE_ID = 0;
    public static String LOCATION_CHANGE = "location_changed";
    public static String ACTION = "action";
    CameraActivity mapFragment;



    public double latitude, longitude;

    public LocationBroadcastReceiver(MainActivityInf mainActivityInf) {
        this.mainActivityInf = mainActivityInf;

    }

    public LocationBroadcastReceiver() {

    }
/*    public LocationBroadcastReceiver() {
        UNIQUE_ID++;
    }*/

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "ID:" + UNIQUE_ID);
        if (intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)) {

            String locationChanged = LocationManager.KEY_LOCATION_CHANGED;
            Location location = (Location) intent.getExtras().get(locationChanged);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.d(TAG, latitude + "," + longitude);
            mainActivityInf.DisplayLocationChange(latitude + "," + longitude);

        }

        if (intent.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)) {

            String providerEnabled = LocationManager.KEY_PROVIDER_ENABLED;
            boolean isEnabled = intent.getBooleanExtra(providerEnabled, false);
            //mainActivityInf.DisplayProviderEnable(isEnabled);

        }
    }
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLocalizationActi(CameraActivity cameraActivity) {
        this.mapFragment=cameraActivity;

    }
 }
