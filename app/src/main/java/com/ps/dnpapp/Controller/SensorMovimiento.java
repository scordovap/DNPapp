package com.ps.dnpapp.Controller;


import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorMovimiento implements SensorEventListener {
    private static final String TAG = "AccelerationEventListener";
    private float x, y, z;
    private float a = 0.8f;
    private float mHighPassX, mHighPassY, mHighPassZ;
    private float mLastX, mLastY, mLastZ;

    @SuppressLint("LongLogTag")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        x = sensorEvent.values[0];
        y = sensorEvent.values[1];
        z = sensorEvent.values[2];
        Log.d(TAG, "CURRENT:" + x + "," + y + "," + z);
        mHighPassX = highPass(x, mLastX, mHighPassX);
        mHighPassY = highPass(y, mLastY, mHighPassY);
        mHighPassZ = highPass(z, mLastZ, mHighPassZ);
        mLastX = x;
        mLastY = y;
        mLastZ = z;

       // Log.d(TAG, "FILTER:" + mHighPassX + "," + mHighPassY + "," + mHighPassZ);

    }
    @SuppressLint("LongLogTag")
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
       // Log.d(TAG, "" + sensor.getName());

    }

    // simple high-pass filter
    float highPass(float current, float last, float filtered) {
        return a * (filtered + current - last);

    }
}