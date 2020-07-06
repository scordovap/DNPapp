package com.ps.dnpapp.Controller.BroadcastGPS;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class LocationIntentService extends IntentService {

    private static final String TAG = "LocationIntentService";

    public LocationIntentService(){
        super("LocationIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            for (int i = 0; i < 20; i++) {
                Log.d(TAG,Thread.currentThread().getId()+":"+i);
                Thread.sleep(500);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
