package com.ps.dnpapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.ps.dnpapp.Controller.GPS.CameraActivity;
import com.ps.dnpapp.Controller.adapter.ViewPagerAdapter;
import com.ps.dnpapp.Controller.fragment.HOME;
import com.ps.dnpapp.Controller.fragment.STATUS;
import com.ps.dnpapp.Controller.fragment.CAMERA;
import com.ps.dnpapp.R;

public class UsuarioActivity extends AppCompatActivity {
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        viewPager = findViewById(R.id.viewPager);

        Button btnCamera=(Button) findViewById(R.id.camara);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int1=new Intent(view.getContext(), CameraActivity.class);
                startActivityForResult(int1,0);
            }
        });
        addTabs(viewPager);
        ((TabLayout) findViewById(R.id.tabLayout)).setupWithViewPager( viewPager );
    }
    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HOME(), "HOME");
        adapter.addFrag(new CAMERA(), "CAMERA");
        adapter.addFrag(new STATUS(), "SENSOR STATS");
        viewPager.setAdapter(adapter);
    }

}
