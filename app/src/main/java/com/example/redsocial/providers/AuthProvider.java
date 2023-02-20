package com.example.redsocial.providers;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthProvider
{
    private FirebaseAuth mAuth;


    public AuthProvider()
    {
        mAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> login(String email, String password)
    {
        return mAuth.signInWithEmailAndPassword(email, password);
    }
    public Task<AuthResult> registro(String email, String password)
    {
        return mAuth.createUserWithEmailAndPassword(email, password);
    }
    public  String getUid()
    {
        if(mAuth.getCurrentUser()!=null)
        {
            return mAuth.getCurrentUser().getUid();
        }
        else
        {
            return null;
        }
    }
    public FirebaseUser getUserSesion()
    {
        if(mAuth.getCurrentUser()!=null)
        {
            return mAuth.getCurrentUser();
        }
        else
        {
            return null;
        }
    }
    public void logout()
    {
        if(mAuth != null)
        {
            mAuth.signOut();
        }
    }
}
