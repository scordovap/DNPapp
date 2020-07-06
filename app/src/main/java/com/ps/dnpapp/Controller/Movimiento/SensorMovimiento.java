package com.ps.dnpapp.Controller.Movimiento;


import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;

public class SensorMovimiento extends View implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor acelerometro;
    private static final String TAG = "AccelerationEventListener";
    private float x, y, z;
    private float a = 0.8f;
    private float mHighPassX, mHighPassY, mHighPassZ;
    private float mLastX, mLastY, mLastZ;


    public SensorMovimiento(Context context) {
        super(context);
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,acelerometro, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        x = sensorEvent.values[0];
        y = sensorEvent.values[1];
        z = sensorEvent.values[2];
        mHighPassX = highPass(x, mLastX, mHighPassX);
        mHighPassY = highPass(y, mLastY, mHighPassY);
        mHighPassZ = highPass(z, mLastZ, mHighPassZ);
        mLastX = x;
        mLastY = y;
        mLastZ = z;
       Log.d(TAG, "FILTER MOVIMIENTO:" + mHighPassX + "," + mHighPassY + "," + mHighPassZ);

    }
    @SuppressLint("LongLogTag")
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
       // Log.d(TAG, "" + sensor.getName());

    }

    float highPass(float current, float last, float filtered) {
        return a * (filtered + current - last);

    }
}