package com.example.redsocial.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.redsocial.R;
import com.example.redsocial.providers.AuthProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity
{
    private TextView lbl2;
    private EditText txtCorreo;
    private EditText txtPassword;
    AuthProvider authProvider;
    AlertDialog dialog;
    PostActivity postActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lbl2 = (TextView) findViewById(R.id.lbl2);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        authProvider = new AuthProvider();
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un Momento")
                .setCancelable(false).build();
        postActivity = new PostActivity();
    }
    public void Register(View v)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authProvider.getUserSesion()!=null)
        {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void iniciarSesion(View v)
    {
        userAutentication();
    }
    public void userAutentication()
    {
        String email = txtCorreo.getText().toString();
        String password = txtPassword.getText().toString();
        if(!email.isEmpty() && !password.isEmpty())
        {
            login();
        }
        else
        {
            Toast.makeText(this, "Inserte todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    public void login()
    {
        String email = txtCorreo.getText().toString();
        String password = txtPassword.getText().toString();
        dialog.show();
        authProvider.login(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialog.dismiss();
                if(task.isSuccessful())
                {
                    Intent i= new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Usuario Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.d("campo", "email: "+email);
        Log.d("campo", "password: "+password);
    }
}