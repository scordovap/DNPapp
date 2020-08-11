package com.ps.dnpapp.Controller.fragment.Sensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.ps.dnpapp.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class Accelerometer extends Fragment implements SensorEventListener {
    Button btnFrag;
    TextView textView;
    private static final String TAG = "Magnometer";
    private SensorManager senSensorManager;
    private Sensor senAccelerometer, senMagnometrico;
    private float x1,y1,z1;
    private float mLastX,mLastY, mLastZ;
    private SensorEventListener sensorEventListener;
    public static DecimalFormat DECIMAL_FORMATTER;
    public double aceTotal;
    private int whip=0;
    float x,y,z;

    public static Accelerometer newInstance(String param1, String param2) {
        Accelerometer fragment2 = new Accelerometer();
        return fragment2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        DECIMAL_FORMATTER = new DecimalFormat("#.000", symbols);


        senSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_accelerometer, container, false);
        textView=(TextView)v.findViewById(R.id.acelero);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;


        x1=sensorEvent.values[0];
        y1 = sensorEvent.values[1];
        z1 = sensorEvent.values[2];
        mLastX = x1;
        mLastY = y1;
        mLastZ = z1;
        aceTotal=Math.sqrt(Math.pow(x1,2)+Math.pow(y1,2)+Math.pow(z1,2));
        Log.d(TAG, mLastX+","+mLastY+ ","+mLastZ);
        textView.setText("Eje x: "+x1+"\nEje y: "+y1+"\nEje z: "+z1+"\nAceleracion total calculada: \n"+z1);


    }

    private void stop(){
        senSensorManager.unregisterListener(this);

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}