package com.ps.dnpapp.Controller.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.ps.dnpapp.Controller.Activities.InfoAplication;
import com.ps.dnpapp.Controller.Activities.Instrucciones;
import com.ps.dnpapp.Controller.Activities.LoginRegister.LoginActivity;
import com.ps.dnpapp.Model.MainActivity;
import com.ps.dnpapp.R;


public class Home extends Fragment {
    private FirebaseAuth mAuth;
    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home1,container,false);
        // Inflate the layout for this fragment

        CardView MoreInformation = v.findViewById(R.id.card_aboutUs);
        CardView ShareApplication = v.findViewById(R.id.card_share);
        CardView Back = v.findViewById(R.id.card_back);
        CardView Instructions = v.findViewById(R.id.card_instructions);
        mAuth=FirebaseAuth.getInstance();

        MoreInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InfoAplication.class);
                startActivity(intent);
            }
        });
        ShareApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()!=null){
                    mAuth.signOut();
                    startActivity(new Intent(getContext(),MainActivity.class ));
                    getActivity().finish();
                }
            }
        });
        Instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Instrucciones.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
