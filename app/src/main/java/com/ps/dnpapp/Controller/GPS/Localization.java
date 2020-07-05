package com.ps.dnpapp.Controller.GPS;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ps.dnpapp.R;


public class Localization{
    public double latitude, longitude;
    CameraActivity mapFragment;

   public Localization(Double lat, Double lon){
        this.latitude=lat;
        this.longitude=lon;
        mapa(latitude,longitude);
    }
    public Localization(){

    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLocalizationActi(CameraActivity activity){
        this.mapFragment=activity;
    }

    public  CameraActivity getMapFragment(){
        return mapFragment;
    }

      public void mapa(double latitude, double longitude) {
        FragmentMaps fragment=new FragmentMaps();
        Bundle bundle=new Bundle();
        bundle.putDouble("lat",new Double(latitude));
        bundle.putDouble("lon",new Double(longitude));
        fragment.setArguments(bundle);
        FragmentManager fragmentManager=getMapFragment().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mapaLoca,fragment,null);
        fragmentTransaction.commit();

    }


}
