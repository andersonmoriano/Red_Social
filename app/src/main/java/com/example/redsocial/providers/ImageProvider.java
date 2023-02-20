package com.example.redsocial.providers;

import android.content.Context;
import android.util.Log;

import com.example.redsocial.utils.CompressorBitmapImage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.util.Date;


public class ImageProvider
{
    StorageReference mstorage;

    public ImageProvider()
    {
        mstorage = FirebaseStorage.getInstance().getReference();
    }

    public UploadTask save(Context context, File file)
    {
        byte[] imageByte = CompressorBitmapImage.getImage(context, file.getPath(), 500, 500);
        StorageReference spaceRef = FirebaseStorage.getInstance().getReference().child(new Date()+ ".jpg");
        mstorage = spaceRef;
        UploadTask task = spaceRef.putBytes(imageByte);
        return task;
    }
    public StorageReference getSrorage()
    {
        return mstorage;
    }
}
