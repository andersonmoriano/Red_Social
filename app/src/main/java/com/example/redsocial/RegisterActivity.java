package com.example.redsocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity
{
    CircleImageView ArrowLeft;
    float variablePrueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ArrowLeft = (CircleImageView) findViewById(R.id.ArrowLeft);
    }
    public void arrowLeft(View v)
    {
        finish();
    }
}