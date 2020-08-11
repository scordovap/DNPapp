package com.ps.dnpapp.Controller.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import com.ps.dnpapp.Model.Sensores;
import com.ps.dnpapp.R;


public class Status extends Fragment {

    Button btnSensores;
    public Status() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stats,container,false);
        // Inflate the layout for this fragment
        btnSensores = (Button) v.findViewById(R.id.sensores);
        btnSensores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Sensores.class);
                startActivity(intent);
            }
        });
        return v;
    }

}
