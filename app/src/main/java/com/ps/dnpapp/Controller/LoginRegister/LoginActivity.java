package com.ps.dnpapp.Controller.LoginRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.ps.dnpapp.Model.*;
import com.ps.dnpapp.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private TextView crear;
    private EditText textEmail, textPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        crear=(TextView)findViewById(R.id.crearcuentaActivity);

        firebaseAuth=FirebaseAuth.getInstance();
        textEmail=(EditText)findViewById(R.id.mailLogin);
        textPassword=(EditText)findViewById(R.id.passwordLogin);
        btnLogin=(Button)findViewById(R.id.botonLogin);

        progressDialog=new ProgressDialog(this);

        btnLogin.setOnClickListener(this);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

    }
    private void loginUsuario() {
        final String email=textEmail.getText().toString().trim();
        final String password=textPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Ingresa email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Ingresa contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Comprobando...");
        progressDialog.show();
//Login usuario
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            MainActivity.user=email;
                            MainActivity.contra=password;
                            Toast.makeText(LoginActivity.this,"Bienvenido",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            if (task.getException()instanceof FirebaseAuthUserCollisionException){
                                //destecta si hay un usuario registrado
                                Toast.makeText(LoginActivity.this,"ya existe el usuario",Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(LoginActivity.this,"Datos incorrectos",Toast.LENGTH_SHORT).show();

                            }


                        }
                        progressDialog.dismiss();
                    }
                });

    }
    @Override
    public void onClick(View view) {
        loginUsuario();
    }


}

