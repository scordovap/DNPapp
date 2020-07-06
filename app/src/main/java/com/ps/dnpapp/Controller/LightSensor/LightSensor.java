package com.ps.dnpapp.Controller.LightSensor;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ps.dnpapp.R;

public class LightSensor extends View implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View root;
    private float valormax;
     public LightSensor(Context context){
         super(context);
         sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
         lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
         sensorManager.registerListener(this,lightSensor, SensorManager.SENSOR_DELAY_FASTEST);


         valormax = lightSensor.getMaximumRange();
     }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float value = sensorEvent.values[0];
        Log.d("Luminosidad", "hola"+value);
        int newValue = (int) (255f * value / valormax);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
