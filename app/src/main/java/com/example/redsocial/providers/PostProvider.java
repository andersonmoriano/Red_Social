package com.example.redsocial.providers;

import com.example.redsocial.activities.PostActivity;
import com.example.redsocial.models.Publicacion;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

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
    public Query getAll()
    {
        return collectionReference.orderBy("nombre", Query.Direction.DESCENDING);
    }
}
