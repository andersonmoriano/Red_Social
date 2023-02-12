package com.example.redsocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
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
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
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
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
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
                if(password.equals(confirmPassword))
                {
                    if(password.length()>=6)
                    {
                        createUser(nombre,apellido,email, password);
                    }
                    else
                    {
                        Toast.makeText(this, "La contraseña debe tener mayor a 6 caracteres", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "La contraseña no coincide con su confirmación", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(this, "El email es Invalido", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Inserte todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    private void createUser(String nombre,String apellido,String email,String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombre);
                    map.put("apellido", apellido);
                    map.put("email", email);
                    mFirestore.collection("Users").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "El usuario se registro correctamente", Toast.LENGTH_SHORT).show();
                                limpiar();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "El correo electrónico ingresado ya existe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void limpiar()
    {
        txtNombre.setText( "" );
        txtApellido.setText( "" );
        txtEmail.setText( "" );
        txtContraseña.setText( "" );
        txtConfContraseña.setText( "" );
    }
    public boolean isEmailValid(String email)
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";//[\w\.-]+@([\w\-]+\.)+[A-Z]{2,4}
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void arrowLeft(View v)
    {
        finish();
    }
}