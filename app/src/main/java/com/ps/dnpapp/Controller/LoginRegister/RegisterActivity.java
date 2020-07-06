package com.ps.dnpapp.Controller.LoginRegister;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import com.ps.dnpapp.Controller.UsuarioActivity;
import com.ps.dnpapp.Model.MainActivity;
import com.ps.dnpapp.R;
import java.util.HashMap;
import java.util.Map;
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegistrar;
    private EditText textEmail, textPassword,textUsuario;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registro");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        firebaseAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();

        textEmail=(EditText)findViewById(R.id.mailRegistro);
        textUsuario=(EditText)findViewById(R.id.usuarioRegistro);

        textPassword=(EditText)findViewById(R.id.passwordRegistro);
        btnRegistrar=(Button)findViewById(R.id.RegistrarUsuario);


        progressDialog=new ProgressDialog(this);

        btnRegistrar.setOnClickListener(this);

    }

    public void registrarUsuario(){
        final String email=textEmail.getText().toString().trim();
        final String usuario=textUsuario.getText().toString().trim();
        final String password=textPassword.getText().toString().trim();

        if(password.length()<=6){
            Toast.makeText(this,"Ingresa una contraseña de 6 o más carácteres", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Ingresa email", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Realizando Registro...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            MainActivity.user=email;
                            MainActivity.contra=password;

                            Map<String,Object> mapa=new HashMap();
                            mapa.put("correo",email);
                            mapa.put("usuario",usuario);
                            mapa.put("password",password);
                            String id=mAuth.getCurrentUser().getUid();

                            mDatabase.child("Usuarios").child(id).setValue(mapa);
                            Toast.makeText(RegisterActivity.this,"registrado",Toast.LENGTH_SHORT).show();
                            Intent a=new Intent(RegisterActivity.this, UsuarioActivity.class);
                            startActivity(a);
                            finish();

                        }else{
                            if (task.getException()instanceof FirebaseAuthUserCollisionException){
                                //destecta si hay un usuario registrado
                                Toast.makeText(RegisterActivity.this,"ya existe el usuario",Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(RegisterActivity.this,"registro fallido",Toast.LENGTH_SHORT).show();

                            }


                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        registrarUsuario();
    }
}
