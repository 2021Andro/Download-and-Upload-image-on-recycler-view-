package com.example.examplerecyclerview2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.examplerecyclerview2.Classes.PersonDemo;
import com.example.examplerecyclerview2.Classes.RecyclerEventListener_1;
import com.example.examplerecyclerview2.RecAdapter.MyAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.internal.StorageReferenceUri;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity{

    private ImageView ivProfile;
    private Uri imageUri;
    private int imageCode = 1;
    private boolean isImageSelect = false;

    private EditText etPersonName;

    private DatabaseReference myRef;
    private StorageReference myStorage;
    private String TAG = "MyTag";
    private StorageReference childId;
    private PersonDemo personDemo = new PersonDemo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase Storage Example Name of db

        ivProfile = findViewById(R.id.imageView);
        etPersonName = findViewById(R.id.etPersonName);

        myStorage = FirebaseStorage.getInstance().getReference("Upload");
        myRef = FirebaseDatabase.getInstance().getReference("Upload");


    }

    @Override
    protected void onResume() {
        super.onResume();



        // image related code
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("image/*");

                startActivityForResult(Intent.createChooser(intent, "image"), imageCode);

            }
        });


    }

    // button click related code
    public void onBtnAddPerson(View view) {

        String name = etPersonName.getText().toString().trim();

        if (name.isEmpty())
        {
            etPersonName.setError("Enter your name");
            return;
        }else if (!isImageSelect)
        {
            Toast.makeText(getApplicationContext(), "Select profile image", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {

            setAddPerson();

        }
    }

    private void setAddPerson() {

        StorageReference fileRef = myStorage.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));

        fileRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        PersonDemo personDemo = new PersonDemo(etPersonName.getText().toString().trim(),
                                taskSnapshot.getUploadSessionUri().toString());

                        String uploadedKey = myRef.push().getKey();

                        myRef.child(uploadedKey).setValue(personDemo);
                    }
                });


    }

    private String getFileExtension(Uri uri) {

        ContentResolver cr = getContentResolver();

        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == imageCode)
        {

            if (resultCode == RESULT_OK)
            {

                imageUri = data.getData();

                isImageSelect = true;

                Picasso.with(this).load(imageUri).into(ivProfile);

            }
            if (resultCode == RESULT_CANCELED)
            {


                isImageSelect = false;

                ivProfile.setImageResource(R.drawable.ic_launcher_background);

            }


        }


    }

    public void onBtnShowListPerson(View view) {

        Intent intent = new Intent(getApplicationContext(), RecyclerView_Activity.class);

        startActivity(intent);
    }
}