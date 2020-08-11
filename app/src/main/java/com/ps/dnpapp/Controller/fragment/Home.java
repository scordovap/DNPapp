package com.ps.dnpapp.Controller.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.ps.dnpapp.Controller.Activities.Instrucciones;
import com.ps.dnpapp.Controller.GPS.CameraActivity;
import com.ps.dnpapp.R;


public class Home extends Fragment {

    Button btnInstrucciones;
    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home1,container,false);
        btnInstrucciones = (Button) v.findViewById(R.id.instruccionesbutton);
        btnInstrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Instrucciones.class);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

}
