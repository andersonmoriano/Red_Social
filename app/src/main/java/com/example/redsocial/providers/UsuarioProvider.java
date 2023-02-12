package com.example.redsocial.providers;

import com.example.redsocial.models.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UsuarioProvider
{
    private CollectionReference mCollection;

    public UsuarioProvider()
    {
        mCollection = FirebaseFirestore.getInstance().collection("Users");
    }
    public Task<Void> create(Usuario usuario)
    {
        return mCollection.document(usuario.getId()).set(usuario);
    }
}
