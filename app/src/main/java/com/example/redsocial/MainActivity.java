package com.example.redsocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView lbl2;
    EditText txtCorreo;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lbl2 = (TextView) findViewById(R.id.lbl2);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
    }
    public void Register(View v)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void iniciarSesion(View v)
    {
        login();
    }
    public void login()
    {
        String email = txtCorreo.getText().toString();
        String password = txtPassword.getText().toString();
        Log.d("campo", "email: "+email);
        Log.d("campo", "password: "+password);
    }
}