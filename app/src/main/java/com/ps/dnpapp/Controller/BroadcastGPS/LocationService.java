package com.ps.dnpapp.Controller.BroadcastGPS;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class LocationService extends Service {

    private static final String TAG = "LocationService";

    public LocationService(){
        super();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);


/*        try {
            for (int i = 0; i < 10; i++) {
                Log.d(TAG,Thread.currentThread().getId()+":"+i);
                Thread.sleep(500);
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/

        return START_STICKY;
    }


}
