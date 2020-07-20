package com.ps.dnpapp.Controller.GPS;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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

import com.ps.dnpapp.Controller.BroadcastGPS.GPSActivity;

import com.ps.dnpapp.R;

public class CameraActivity extends AppCompatActivity implements SensorEventListener {
    GPSActivity gpsActivity;
    Localization puntos;
    private ImageView mImageView;
    private TextView mLocationTextView;
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
    protected void onStop() {
        super.onStop();
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
        setContentView(R.layout.activity_camera);


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
        Log.d("TAKE", "TomarFoto");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
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
    @Override
    public void onSensorChanged(SensorEvent event) {

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
