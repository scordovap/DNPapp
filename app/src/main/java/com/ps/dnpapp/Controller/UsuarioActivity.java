package com.ps.dnpapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.ps.dnpapp.Controller.GPS.CameraActivity;
import com.ps.dnpapp.R;

public class UsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);


        Button btnCamera=(Button) findViewById(R.id.camara);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int1=new Intent(view.getContext(), CameraActivity.class);
                startActivityForResult(int1,0);
            }
        });
    }

}
