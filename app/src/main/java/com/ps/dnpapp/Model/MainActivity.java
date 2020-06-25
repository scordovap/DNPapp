package com.ps.dnpapp.Model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.ps.dnpapp.Controller.*;
import com.ps.dnpapp.R;
;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static String user, contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ClassCam");
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();

        Button bt1=(Button) findViewById(R.id.RegistrarMain);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int1=new Intent(view.getContext(), RegisterActivity.class);
                startActivityForResult(int1,0);
            }
        });

        Button bt2=(Button) findViewById(R.id.loginMain);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                int code = 4545; // Esto puede ser cualquier código.
                startActivityForResult(intent, code);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();


        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this, UsuarioActivity.class ));
            finish();
        }
    }
}