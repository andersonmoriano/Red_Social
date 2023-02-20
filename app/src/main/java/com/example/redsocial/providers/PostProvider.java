package com.example.redsocial.providers;

import com.example.redsocial.activities.PostActivity;
import com.example.redsocial.models.Publicacion;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostProvider
{
    CollectionReference collectionReference;
    public PostProvider()
    {
        collectionReference = FirebaseFirestore.getInstance().collection("Posts");
    }
    public Task<Void> save(Publicacion publicacion)
    {
        return collectionReference.document().set(publicacion);
    }
}
