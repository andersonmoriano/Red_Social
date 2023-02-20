package com.example.redsocial.activities;

/*import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;*/
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
//import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.redsocial.R;
import com.example.redsocial.models.Publicacion;
import com.example.redsocial.providers.AuthProvider;
import com.example.redsocial.providers.ImageProvider;
import com.example.redsocial.providers.PostProvider;
import com.example.redsocial.utils.FileUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class PostActivity extends AppCompatActivity
{
    ImageView ivPost1, ivPost2;
    ImageProvider imageProvider;
    PostProvider postProvider;
    Button btnPublicacion;
    EditText txtVideoGame;
    EditText txtDescripcion;
    TextView lblCategoria;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    String categoria = "";
    File imageFile;
    File imageFile2;
    private final int GALLERY_REQUEST_CODE = 1;
    private final int GALLERY_REQUEST_CODE_2 = 2;
    String nombre ="";
    String descripcion ="";
    AuthProvider authProvider;
    AlertDialog dialog;
    CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ivPost1 = (ImageView) findViewById(R.id.ivPost1);
        ivPost2 = (ImageView) findViewById(R.id.ivPost2);
        imageProvider = new ImageProvider();
        btnPublicacion = (Button) findViewById(R.id.btnPublicacion);
        txtVideoGame =  findViewById(R.id.txtVideoGame);
        txtDescripcion =  findViewById(R.id.txtDescripcion);
        lblCategoria = (TextView) findViewById(R.id.lblCategoria);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        dialog = new SpotsDialog.Builder().setContext(this).setMessage("Espere un Momento").setCancelable(false).build();
        postProvider = new PostProvider();
        authProvider = new AuthProvider();
        circleImageView = findViewById(R.id.ArrowLeft);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveImage();
                publicar();
            }
        });
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria ="LAPTOP";
                lblCategoria.setText("categoria");
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "LAPTOP 2";
                lblCategoria.setText(categoria);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria = "XBOX";
                lblCategoria.setText(categoria);
            }
        });
        image4.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View v) {
            categoria = "PS4";
            lblCategoria.setText(categoria);
        }
        });

    }
    public void publicar()
    {
        nombre = txtVideoGame.getText().toString();
        descripcion = txtDescripcion.getText().toString();
        if(!nombre.isEmpty() && !descripcion.isEmpty())
        {
            if(imageFile!=null)
            {
                saveImage();
            }
            else
            {
                Toast.makeText(this, "Debe seleccionar una imagen", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "Complete todos los campos para continuar", Toast.LENGTH_LONG).show();
        }
    }
    public void uploadImage1(View v)
    {
        openGallery(GALLERY_REQUEST_CODE);
    }
    public void uploadImage2(View v)
    {
        openGallery(GALLERY_REQUEST_CODE_2);
    }
    /*ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult
            (
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if(result.getResultCode() == Activity.RESULT_OK)
                            {
                                try
                                {
                                    imageFile = FileUtil.from(PostActivity.this, result.getData().getData());
                                    ivPost1.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
                                }catch(Exception e)
                                {
                                    Log.d("ERROR", "Se produjo un error "+ e.getMessage());
                                    Toast.makeText(PostActivity.this, "Se produjo un error "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
            );*/

    private void saveImage()
    {
        dialog.show();
        imageProvider.save(PostActivity.this, imageFile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
            {
                if (task.isSuccessful())
                {
                    imageProvider.getSrorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                    {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String url = uri.toString();
                            imageProvider.save(PostActivity.this, imageFile2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task2) {
                                    if(task2.isSuccessful())
                                    {
                                        imageProvider.getSrorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri2) {
                                                String url2 = uri2.toString();
                                                Publicacion publicacion = new Publicacion();
                                                publicacion.setImage1(url);
                                                publicacion.setImage2(url2);
                                                publicacion.setNombre(nombre);
                                                publicacion.setDescripcion(descripcion);
                                                publicacion.setCategoria(categoria);
                                                publicacion.setIdUsuario(authProvider.getUid());
                                                postProvider.save(publicacion).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> tasksave)
                                                    {
                                                        dialog.dismiss();
                                                        if(tasksave.isSuccessful())
                                                        {
                                                            clearForm();
                                                            Toast.makeText(PostActivity.this, "Se almacenó correctamente", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(PostActivity.this, "No se pudo almacenar la configuración", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                    else
                                    {
                                        dialog.dismiss();
                                        Toast.makeText(PostActivity.this, "Error al almacenar la imagen" + task, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    });
                }
                else
                {
                    dialog.dismiss();
                    Toast.makeText(PostActivity.this, "Error al almacenar la imagen" + task, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void clearForm()
    {
        txtVideoGame.setText("");
        txtDescripcion.setText("");
        lblCategoria.setText("");
        ivPost1.setImageResource(R.drawable.cover_image);
        ivPost2.setImageResource(R.drawable.cover_image);
        categoria = "";
        descripcion = "";
        imageFile = null;
        imageFile2 = null;
    }
    private void openGallery(int requestCode)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        //galleryLauncher.launch(galleryIntent);
        startActivityForResult(galleryIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK)
        {
            try
            {
                imageFile = FileUtil.from(PostActivity.this, data.getData());
                ivPost1.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
            }catch(Exception e)
            {
                Log.d("ERROR", "Se produjo un error "+ e.getMessage());
                Toast.makeText(PostActivity.this, "Se produjo un error "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == GALLERY_REQUEST_CODE_2 && resultCode == RESULT_OK)
        {
            try
            {
                imageFile2 = FileUtil.from(PostActivity.this, data.getData());
                ivPost2.setImageBitmap(BitmapFactory.decodeFile(imageFile2.getAbsolutePath()));
            }catch(Exception e)
            {
                Log.d("ERROR", "Se produjo un error "+ e.getMessage());
                Toast.makeText(PostActivity.this, "Se produjo un error "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}