package com.ps.dnpapp.Controller;


import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CentrosActivity extends SupportMapFragment implements OnMapReadyCallback {

    double lat,lon;

    public CentrosActivity(){

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view= super.onCreateView(layoutInflater, viewGroup, bundle);
        if(getArguments()!=null){
            this.lat=getArguments().getDouble("lat");
            this.lon=getArguments().getDouble("lon");

        }
        getMapAsync(this);

        return  view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng latLng=new LatLng(lat,lon);
        float zoom=17;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(latLng));
        UiSettings settings=googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
    }
}
