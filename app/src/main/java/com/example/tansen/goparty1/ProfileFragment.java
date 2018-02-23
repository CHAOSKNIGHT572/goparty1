package com.example.tansen.goparty1;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tansen.goparty1.Firebase.FirebaseDatabaseHelper;
import com.firebase.ui.auth.ui.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProfileFragment extends android.app.Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    private ImageView profilePhoto;

    private TextView profileName;

    private TextView country;

    private TextView userStatus;

    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;

    private String id;

    private static final int REQUEST_READ_PERMISSION = 120;

    ImageView imgView;

    FirebaseAuth firebaseAuth;

    Uri imageUri;

    SharedPreferences shre;
    SharedPreferences.Editor edit;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        shre = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        edit = shre.edit();

        getActivity().setTitle("My Profile");

        profileName = (TextView)view.findViewById(R.id.profile_name);
        country = (TextView)view.findViewById(R.id.country);
        profileName.setVisibility(View.GONE);
        country.setVisibility(View.GONE);


        profilePhoto = (ImageView)view.findViewById(R.id.circleView);
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePicSelection();
//                final Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                galleryIntent.setType("image/*");
//                startActivityForResult(galleryIntent, Helper.SELECT_PICTURE);
            }

            private void profilePicSelection() {
                final CharSequence[] items = {"Take Photo", "Choose from Library"
                        ,"Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add Photo");
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (items[i].equals("Take Photo")) {
                            cameraIntent();
                        } else if (items[i].equals("Choose from Library")) {
                            galleryIntent();
                        } else if (items[i].equals("Cancel")) {
                            dialogInterface.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        recyclerView = (RecyclerView)view.findViewById(R.id.profile_list);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        String img = shre.getString("/scard/Images/img_headPhoto.jpg", "");
        File imgFile = new File(img);

        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            profilePhoto.setImageBitmap(bitmap);
        }




        id = firebaseAuth.getCurrentUser().getUid();

        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.isUserKeyExist(id, getActivity(), recyclerView);
        return view;

    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//        File output = new File(dir, "camerascript.png");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
////        getActivity().startActivityForResult(intent, Helper.CAMERA_REQUEST);
//        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, Helper.CAMERA_REQUEST);
      //  }



    }

//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = Environment.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Helper.GALLERY_REQUEST);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_profile, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_edit_profile){
            Intent editProfileIntent = new Intent(getActivity(), EditProfileActivity.class);
            getActivity().startActivity(editProfileIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("user id has entered onActivityResult ");
        if (requestCode == Helper.GALLERY_REQUEST && resultCode == Activity.RESULT_OK ) {
            Uri selectedImageUri = data.getData();
            System.out.println("selectedImageUri" + selectedImageUri);
            final String imagePath = getPath(selectedImageUri);
            FirebaseStorageHelper storageHelper = new FirebaseStorageHelper(getActivity());

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_PERMISSION);
                return;
            }
            System.out.println("Uri: " + selectedImageUri + " and imagePath: " + imagePath);

            storageHelper.saveProfileImageToCloud(id, selectedImageUri, profilePhoto);

            String img = shre.getString("/scard/Images/img_headPhoto.jpg", "");
            File imgFile = new File(img);

            edit.putString("/scard/Images/img_headPhoto.jpg", imagePath);
            edit.commit();


            NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            imgView = (ImageView) headerView.findViewById(R.id.navcircleView);
            if (imgFile.exists()) {
                final Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imgView.setImageBitmap(bitmap);
                    }
                });
            }
        }

        else if (requestCode == Helper.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Title", null);
            Uri selectedImageUri = Uri.parse(path);
//            profilePhoto.setImageBitmap(photo);
//
//            Uri selectedImageUri = data.getData();
////            System.out.println("selectedImageUri" + selectedImageUri);
//            String imagePath = getPath(selectedImageUri);
            FirebaseStorageHelper storageHelper = new FirebaseStorageHelper(getActivity());
//
//            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_PERMISSION);
//                return;
//            }

            NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            imgView = (ImageView) headerView.findViewById(R.id.navcircleView);

//            Uri selectedImage = imageUri;
//            getActivity().getContentResolver().notifyChange(selectedImage, null);
//            ContentResolver cr = getActivity().getContentResolver();
            imgView.setImageBitmap(bitmap);

//            try {
//                bitmap = android.provider.MediaStore.Images.Media
//                        .getBitmap(cr, selectedImage);
//
//                imgView.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                Log.e("Camera", e.toString());
//            }
//
            storageHelper.saveProfileImageToCloud(id, selectedImageUri, profilePhoto);
//            System.out.println("haha");

//            String img = shre.getString("/scard/Images/img_headPhoto.jpg", "");
//            File imgFile = new File(img);
//
//            edit.putString("/scard/Images/img_headPhoto.jpg", imagePath);
//            edit.commit();



//            if (imgFile.exists()) {
//                final Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        imgView.setImageBitmap(bitmap);
//                    }
//                });
//            }

        }

    }

    public String getPath(Uri uri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getContext(), uri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);

        cursor.close();
        return result;
    }
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
//        assert cursor != null;
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        int columnIndex = cursor.getColumnIndex(projection[0]);
//
//        String filePath = cursor.getString(columnIndex);
//        System.out.println("filePath: " + filePath);
//        cursor.close();
//        return filePath;

//        String wholeID = DocumentsContract.getDocumentId(uri);
//
//        // Split at colon, use second item in the array
//        String id = wholeID.split(":")[1];
//
//        String[] column = { MediaStore.Images.Media.DATA };
//
//        // where id is equal to
//        String sel = MediaStore.Images.Media._ID + "=?";
//
//        Cursor cursor = getActivity().getContentResolver().
//                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        column, sel, new String[]{ id }, null);
//
//        String filePath = "";
//
//        int columnIndex = cursor.getColumnIndex(column[0]);
//
//        if (cursor.moveToFirst()) {
//            filePath = cursor.getString(columnIndex);
//        }
//        System.out.println("filePath: " + filePath);
//        cursor.close();
//        return filePath;

//        String result;
//        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
//        if (cursor == null) { // Source is Dropbox or other similar local file path
//            result = uri.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            result = cursor.getString(idx);
//            cursor.close();
//        }
//        return result;
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getActivity(), "Sorry!!!, you can't use this app without granting this permission", Toast.LENGTH_LONG).show();
            }
        }
    }


}
