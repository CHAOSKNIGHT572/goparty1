package com.example.tansen.goparty1;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class FirebaseStorageHelper {

    private static final String TAG = FirebaseStorageHelper.class.getCanonicalName();

    private FirebaseStorage firebaseStorage;

    private StorageReference rootRef;

    private Context context;

    public FirebaseStorageHelper(Context context){
        this.context = context;
        init();
    }

    private void init(){
        this.firebaseStorage = FirebaseStorage.getInstance();
        rootRef = firebaseStorage.getReferenceFromUrl("gs://goparty-24c59.appspot.com/");
    }

    public void saveProfileImageToCloud(String userId, Uri selectedImageUri, final ImageView imageView) {

        StorageReference photoParentRef = rootRef.child(userId);
        final StorageReference photoRef = photoParentRef.child(selectedImageUri.getLastPathSegment());
        UploadTask uploadTask = photoRef.putFile(selectedImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "OnFailure " + e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("VisibleForTests")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                Picasso.with(context).load(downloadUrl.getPath()).into(imageView);
                System.out.println("haha in helper");
                Glide.with(context).using(new FirebaseImageLoader()).load(photoRef).into(imageView);
            }
        });

    }
}
