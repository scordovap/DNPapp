package com.ps.dnpapp.Controller.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ps.dnpapp.Controller.GPS.CameraActivity;
import com.ps.dnpapp.R;


public class CAMERA extends Fragment {


    public CAMERA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intent =new Intent(getActivity(), CameraActivity.class);
        startActivity(intent);
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

}
