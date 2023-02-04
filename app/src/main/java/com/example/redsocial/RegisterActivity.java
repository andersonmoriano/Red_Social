package com.example.redsocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity
{
    CircleImageView ArrowLeft;
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtEmail;
    private EditText txtContraseña;
    private EditText txtConfContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtContraseña = (EditText) findViewById(R.id.txtContraseña);
        txtConfContraseña = (EditText) findViewById(R.id.txtConfContraseña);
        ArrowLeft = (CircleImageView) findViewById(R.id.ArrowLeft);
    }
    public void registro(View v)
    {
        register();
    }
    public void register()
    {
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtContraseña.getText().toString();
        String confirmPassword = txtConfContraseña.getText().toString();

        if(!nombre.isEmpty() && !apellido.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty())
        {
            if(isEmailValid(email))
            {
                Toast.makeText(this, "El email es valido", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "El email es Invalido", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this,"Haz insertado todos los campos", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Para Continuar inserte todos los campos", Toast.LENGTH_LONG).show();
        }
    }
    public boolean isEmailValid(String email)
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void arrowLeft(View v)
    {
        finish();
    }
}