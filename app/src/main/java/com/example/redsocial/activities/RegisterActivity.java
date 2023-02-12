package com.example.redsocial.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.redsocial.R;
import com.example.redsocial.models.Usuario;
import com.example.redsocial.providers.AuthProvider;
import com.example.redsocial.providers.UsuarioProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity
{
    CircleImageView ArrowLeft;
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtEmail;
    private EditText txtContraseña;
    private EditText txtConfContraseña;
    AuthProvider mAuthProvider;
    UsuarioProvider mUserProvider;
    AlertDialog dialog;
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
        mAuthProvider = new AuthProvider();
        mUserProvider = new UsuarioProvider();
        dialog = new SpotsDialog.Builder().setContext(this).setMessage("Espere un Momento").setCancelable(false).build();
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
        dialog.show();
        mAuthProvider.registro(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    String id = mAuthProvider.getUid();
                    Usuario usuario = new Usuario();
                    usuario.setId(id);
                    usuario.setNombre(nombre);
                    usuario.setApellido(apellido);
                    usuario.setEmail(email);
                    mUserProvider.create(usuario).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            dialog.dismiss();
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
                    dialog.dismiss();
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