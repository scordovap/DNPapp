package com.ps.dnpapp.Controller.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.ps.dnpapp.Controller.GPS.CameraActivity;
import com.ps.dnpapp.R;


public class CAMERA extends Fragment {
    Button btnCamera;

    public CAMERA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera,container,false);
        // Inflate the layout for this fragment
        btnCamera = (Button) v.findViewById(R.id.tomarFoto);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
