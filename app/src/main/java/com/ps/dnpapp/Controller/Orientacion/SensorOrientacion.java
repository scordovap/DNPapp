package com.ps.dnpapp.Controller.Orientacion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;

public class SensorOrientacion extends View implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor senMagnometrico,senGiros;
    private static final String TAG = "OrientacionListener";
    float positionX, positionY, positionZ;

    public SensorOrientacion(Context context) {
        super(context);
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        senMagnometrico = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        senGiros=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, senMagnometrico , SensorManager.SENSOR_DELAY_NORMAL);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
    if (mySensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            if(x<-9.8 ){
                Log.w(TAG, "Giro a la derecha");
            }else if(x > 9.8 ){
                Log.w(TAG, "Giro a la Izquierda");
            }else if(y < -9.8){
                Log.w(TAG, "Giro abajo");
            }else if(y > 9.8){
                Log.w(TAG, "Giro Arriba");
            }else if(z > 9.8){
                Log.w(TAG, "Telefono boca arriba");
            }else if(z < -9.8){
                Log.w(TAG, "Telefono boca abajo");
            }
        }
        if ((positionX < 0.5 && positionX > -0.5) && (positionY < 0.5 && positionY > -0.5)){
            System.out.println("El telefono esta en una superficie");
        }
    }

    private void stop(){
        sensorManager.unregisterListener(this);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Log.d(TAG, "" + sensor.getName());
    }


}