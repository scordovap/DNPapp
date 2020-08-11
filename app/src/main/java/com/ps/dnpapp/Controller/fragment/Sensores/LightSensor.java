package com.ps.dnpapp.Controller.fragment.Sensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.ps.dnpapp.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class LightSensor extends Fragment implements SensorEventListener {
    TextView textView;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private float valormax;
    public static DecimalFormat DECIMAL_FORMATTER;
    private static final String TAG = "OrientacionListener";
    float positionX, positionY, positionZ;
    public static LightSensor newInstance(String param1, String param2) {
        LightSensor fragment4 = new LightSensor();
        return fragment4;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        DECIMAL_FORMATTER = new DecimalFormat("#.000", symbols);


        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this,lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
        valormax = lightSensor.getMaximumRange();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_light_sensor, container, false);
        textView=(TextView)v.findViewById(R.id.light2);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float value = sensorEvent.values[0];
        textView.setText("Luminosidad"+ "\nsensor valor \n"+value);
        int newValue = (int) (255f * value / valormax);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
