package com.example.student.newsapp.Firebase;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.student.newsapp.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class upload extends AppCompatActivity {
    public Button select, upload_button;
    ImageView image ;
    TextView title ;
    public static final int READ_EXTERNAL_STORAGE=0;
    private static final int GALLERY_INTENT=2;
    private ProgressDialog mProgressDialog;
    private Firebase mRoofRef;
    public Uri mImgUri=null;
     DatabaseReference mDatabaseReference;
    private  StorageReference mStorage;
    FirebaseAuth firebaseauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);

        Firebase.setAndroidContext(this);

        select=(Button)findViewById(R.id.select);
        upload_button=(Button)findViewById(R.id.upload_bt);
        image=(ImageView)findViewById(R.id.image);
        title=(EditText)findViewById(R.id.title);

        mProgressDialog = new ProgressDialog(upload.this);

        select.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(),"Call for Permission", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},READ_EXTERNAL_STORAGE);
                    }
                }
                else
                {
                    callgalary();
                }

            }
        });


        firebaseauth = FirebaseAuth.getInstance();

       final String userid = firebaseauth.getCurrentUser().getUid();
            mDatabaseReference= FirebaseDatabase.getInstance().getReference();
            mRoofRef = new Firebase("https://firebae-b5687.firebaseio.com/").child("User_Details").child(userid).push();
            mStorage= FirebaseStorage.getInstance().getReferenceFromUrl("gs://firebae-b5687.appspot.com");
        //Click on Upload Button Title will upload to Database
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mName = title.getText().toString().trim();



                if(mName.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Fill all Field", Toast.LENGTH_SHORT).show();
                    return;
                }
                Firebase childRef_name = mRoofRef.child("Image_Title");
                childRef_name.setValue(mName);


                Toast.makeText(getApplicationContext(), "Updated Info", Toast.LENGTH_SHORT).show();
            }
        });

       }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case READ_EXTERNAL_STORAGE:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                    callgalary();
                return;

        }
        Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_SHORT).show();
    }

    private void callgalary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            mImgUri = data.getData();
            image.setImageURI(mImgUri);
            StorageReference filePath = mStorage.child("User_Images").child(mImgUri.getLastPathSegment());

            mProgressDialog.setMessage("Uploading Image....");
            mProgressDialog.show();

            filePath.putFile(mImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUri = taskSnapshot.getDownloadUrl();  //Ignore This error

                    mRoofRef.child("Image_URL").setValue(downloadUri.toString());

                    Glide.with(getApplicationContext())
                            .load(downloadUri)
                            .crossFade()
                            .placeholder(R.drawable.loading)
                            .diskCacheStrategy(DiskCacheStrategy.RESULT)
                            .into(image);
                    Toast.makeText(getApplicationContext(), "Updated.", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            });
        }
    }
}
