package com.ps.dnpapp.Controller.GPS;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.provider.MediaStore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ps.dnpapp.Controller.MainActivityInf;
import com.ps.dnpapp.R;

public class CameraActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "Magnetic";
    Localization puntos;
    private ImageView mImageView;
    private TextView mLocationTextView;
    private static final long MIN_TIME=10000;
    float positionX,positionY,positionZ;
    private ProgressDialog progressDialog;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer, senMagnometrico;
    private SensorEventListener sensorEventListener;
    private float a = 0.8f;
    private MainActivityInf mainActivityInf;
    private float mHighPassX, mHighPassY, mHighPassZ;
    private float mLastX, mLastY, mLastZ;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private BroadcastReceiver broadcastReceiver;
    FragmentMaps mapFragment;
    Double lat,lon;
    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    lat= (Double) intent.getExtras().get("lat");
                    lon= (Double) intent.getExtras().get("lon");
                    puntos.setLatitude(lat);
                    puntos.setLongitude(lon);
                    puntos.mapa(lat,lon);
                    mLocationTextView.setText("\n" +lat+" "+lon);
                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapFragment = new FragmentMaps();
        puntos=new Localization();
        puntos.setLocalizationActi(this);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        senMagnometrico = senSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        senSensorManager.registerListener(this, senMagnometrico , SensorManager.SENSOR_DELAY_NORMAL);

        setContentView(R.layout.activity_camera);
        mImageView = findViewById(R.id.iv_image);
        mLocationTextView = findViewById(R.id.locacion);



        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1000);

        }

    }

    public void takeAPicture(View view) {
        Intent i =new Intent(getApplicationContext(), GPS_Service.class);
        startService(i);
        Log.d("Get", "GetLocation");
        Log.d("TAKE", "takeAPicture");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void Location(View view){

        /*progressDialog=new ProgressDialog(this);

        progressDialog . setMessage ( "Loading ..." ); // Configuración del mensaje
        progressDialog . setTitle ( " ProgressDialog " ); // Establecer título

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(true);*/




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(bitmap);

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
                Log.w(TAG, "Telefono horizontal derecha");
            }else if(positionX > 5 ){
               Log.w(TAG, "Telefono horizontal izquierda");
            }else if(positionY < -5){
                Log.w(TAG, "Telefono vertical boca abajo");
            }else if(positionY> 5){
                Log.w(TAG, "Telefono por defecto");;
            }else if(positionZ > 5){
               Log.w(TAG, "Telefono frontal arriba");
            }else if(positionZ < -5){
              Log.w(TAG, "Telefono frontal abajo");
            }
        }
        if ((positionX < 0.5 && positionX > -0.5) && (positionY < 0.5 && positionY > -0.5)){
            System.out.println("El telefono esta en una superficie");
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


    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){

            }else {
                runtime_permissions();
            }
        }
    }


}
