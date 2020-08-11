package com.ps.dnpapp.Controller.fragment.Sensores;

import android.annotation.SuppressLint;
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


public class OrientationSensor extends Fragment implements SensorEventListener {
    TextView textView;
    private SensorManager sensorManager;
    private Sensor senMagnometrico,senGiros;
    public static DecimalFormat DECIMAL_FORMATTER;
    private static final String TAG = "OrientacionListener";
    float positionX, positionY, positionZ;
    public static OrientationSensor newInstance(String param1, String param2) {
        OrientationSensor fragment3 = new OrientationSensor();
        return fragment3;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        DECIMAL_FORMATTER = new DecimalFormat("#.000", symbols);


        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        senMagnometrico = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        senGiros=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, senMagnometrico , SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_orientation_sensor, container, false);
        textView=(TextView)v.findViewById(R.id.orientacion);
        // Inflate the layout for this fragment
        return v;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            if(x<-5 ){
                textView.setText("Giro a la izquierda");

            }else if(x > 5 ){
                textView.setText("Mirando abajo");
            }else if(y < -5){
                textView.setText("Giro a la derecha");
            }else if(y > 5){
                textView.setText("Giro a la arriba");
            }else if(z > 5){
                textView.setText("Telefono boca arriba");
            }else if(z < -5){
                textView.setText("Telefono Boca Abajo");
            }
            else if ((positionX < 0.5 && positionX > -0.5) && (positionY < 0.5 && positionY > -0.5)){
                textView.setText("Telefono sobre superficie");
            }
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