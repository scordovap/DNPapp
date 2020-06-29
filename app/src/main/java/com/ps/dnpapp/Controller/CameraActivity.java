package com.ps.dnpapp.Controller;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.Telephony;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.MapFragment;
import com.ps.dnpapp.R;

import java.io.IOException;
import java.util.List;

public class CameraActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "Magnetic";
    private Location location;
    MapFragment mapFragment;
    private LocationManager locationManager;
    private ImageView mImageView;
    private TextView mLocationTextView;
float positionX,positionY,positionZ;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer, senMagnometrico;
    private SensorEventListener sensorEventListener;
    private float a = 0.8f;
    private float mHighPassX, mHighPassY, mHighPassZ;
    private float mLastX, mLastY, mLastZ;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    LocationBroadcastReceiver datos=new LocationBroadcastReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapFragment = new MapFragment();

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senMagnometrico = senSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senMagnometrico , SensorManager.SENSOR_DELAY_NORMAL);

        setContentView(R.layout.activity_camera);
        mImageView = findViewById(R.id.iv_image);
        mLocationTextView = findViewById(R.id.locacion);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);




    }

    public void takeAPicture(View view) {
        Log.d("TAKE", "takeAPicture");
        iniciarLocalización();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void iniciarLocalización() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationBroadcastReceiver local = new LocationBroadcastReceiver();
        local.setLocalizationActi(this);
        final boolean gpsenable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsenable) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, 0, local);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, 0, local);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(bitmap);
            mLocationTextView.setText(datos.getLatitude()+"   "+datos.getLongitude());
        }
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            positionX = sensorEvent.values[0];
            positionY = sensorEvent.values[1];
            positionZ = sensorEvent.values[2];

            if(positionX<-5 ){
               // Log.w(TAG, "Telefono horizontal derecha");
            }else if(positionX > 5 ){
              //  Log.w(TAG, "Telefono horizontal izquierda");
            }else if(positionY < -5){
               // Log.w(TAG, "Telefono vertical boca abajo");
            }else if(positionY> 5){
               // Log.w(TAG, "Telefono por defecto");;
            }else if(positionZ > 5){
                //Log.w(TAG, "Telefono frontal arriba");
            }else if(positionZ < -5){
               // Log.w(TAG, "Telefono frontal abajo");
            }
        }
        if ((positionX < 0.5 && positionX > -0.5) && (positionY < 0.5 && positionY > -0.5)){
           // System.out.println("El telefono esta en una superficie");
        }
        //System.out.println(x+"->"+"----- "+y+"->"+"-----"+z+"->");

        /*if (mySensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
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
        }*/


        positionX = sensorEvent.values[0];
        positionY = sensorEvent.values[1];
        positionZ = sensorEvent.values[2];
       // Log.d(TAG, "CURRENT:" +  positionX + "," +  positionY + "," +  positionZ);
        mHighPassX = highPass(positionX, mLastX, mHighPassX);
        mHighPassY = highPass(positionY, mLastY, mHighPassY);
        mHighPassZ = highPass(positionZ, mLastZ, mHighPassZ);
        mLastX =  positionX;
        mLastY = positionY;
        mLastZ =   positionZ ;

       // Log.d(TAG, "FILTER:" + mHighPassX + "," + mHighPassY + "," + mHighPassZ);


    }

    private void stop(){
        senSensorManager.unregisterListener(this);

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Log.d(TAG, "" + sensor.getName());
    }

    float highPass(float current, float last, float filtered) {
        return a * (filtered + current - last);

    }
}
