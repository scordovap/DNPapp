package com.ps.dnpapp.Controller.Sensores;

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


public class Temperatura extends Fragment implements SensorEventListener {
    TextView textView;
    SensorManager sensorManager;
    Sensor tempSensor;
    public Boolean estaDisponible;

    public static DecimalFormat DECIMAL_FORMATTER;
    private static final String TAG = "OrientacionListener";
    float positionX, positionY, positionZ;

    public static Temperatura newInstance(String param1, String param2) {
        Temperatura fragment5 = new Temperatura();
        return fragment5;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        DECIMAL_FORMATTER = new DecimalFormat("#.000", symbols);

        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_FASTEST);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_temperatura, container, false);
        textView = (TextView) v.findViewById(R.id.temperature);
        // Inflate the layout for this fragment
        return v;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        String txt;
        txt = "\n" + event.values[0] + " ºC";
        textView.setText(txt);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();

    }
}
