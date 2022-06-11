package com.example.ieaapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;


public class payment_proof extends AppCompatActivity {

    ImageView proof_img;
    AppCompatButton insert_btn,payment_proofbackbtn,upload_btn;
    String fullname,email,companyName,Department,phoneNo,Turnover,memberfees,amountleft;
    FirebaseDatabase memberDirectoryRoot;
    DatabaseReference memberDirectoryRef;
    private StorageReference memberstorageRef;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_proof);

        memberstorageRef = FirebaseStorage.getInstance().getReference();

        proof_img = findViewById(R.id.proof_img);
        insert_btn = findViewById(R.id.insert_proof_img_btn);
        payment_proofbackbtn = findViewById(R.id.paymentproof_back_button);
        upload_btn = findViewById(R.id.proofupload_btn);

        Intent intent = getIntent();
        fullname=intent.getStringExtra("name");
        email=intent.getStringExtra("email");
        phoneNo=intent.getStringExtra("phoneno");
        companyName=intent.getStringExtra("cname");
        Department=intent.getStringExtra("department");
        Turnover=intent.getStringExtra("annual_turn");
        memberfees=intent.getStringExtra("memberfee");
        amountleft=intent.getStringExtra("costleft");

        payment_proofbackbtn.setOnClickListener(view -> {
            finish();
        });

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                boolean pick=true;
                if(pick==true){
                    if(!checkCameraPermission()){
                        requestCameraPermission();

                    } else{
                        PickImagefromcamera();
                    }
                }else{
                    if(!checkStoragePermission()){
                        requestStoragePermission();

                    } else{
                        PickImagefromstorage();
                    }

                }
            }
        });


        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberDirectoryRoot = FirebaseDatabase.getInstance();
                memberDirectoryRef = memberDirectoryRoot.getReference("Temp Registry");





                if(imageBitmap!=null){

                    Uri proofimg_uri =getimageUri(payment_proof.this,imageBitmap);
                    Log.d("imguri", "onClick: "+proofimg_uri.toString());


                    StorageReference urirefence =memberstorageRef.child("paymentproof/"+UUID.randomUUID().toString());
                    urirefence.putFile(proofimg_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(payment_proof.this,"Upload Sucessfull", Toast.LENGTH_SHORT).show();
                            urirefence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    UserRegistrationHelperClass userRegistrationHelperClass = new UserRegistrationHelperClass(fullname, email, phoneNo, companyName, Department, Turnover,uri.toString(),amountleft);
                                    memberDirectoryRef.child(email.replaceAll("\\.", "%7")).setValue(userRegistrationHelperClass);


                                }
                            });

                        }
                    });



                } else{

                    Toast.makeText(payment_proof.this,"Please Upload Image", Toast.LENGTH_SHORT).show();
                }

            }
        });





    }

    private void PickImagefromstorage() {
        Intent fromstorage = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(fromstorage,1);
    }

    private void PickImagefromcamera() {
        Intent fromcamera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(fromcamera,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
    }

    private boolean checkStoragePermission() {
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return res2;
    }

    private boolean checkCameraPermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return res1 && res2;
    }

    public Uri getimageUri(Context context,Bitmap bitimage){
        ByteArrayOutputStream bytes =new ByteArrayOutputStream();
        bitimage.compress(Bitmap.CompressFormat.JPEG,90,bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),bitimage,"Title",null);
        return Uri.parse(path);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){

            case 0:
                if(resultCode==RESULT_OK){
                    Bundle extras=data.getExtras();
                    imageBitmap=(Bitmap) extras.get("data");
                    proof_img.setImageBitmap(imageBitmap);




                }
                break;

            case 1:
                if(resultCode==RESULT_OK){
                    Uri selectedImage =data.getData();
                    proof_img.setImageURI(selectedImage);
                }
                break;
        }
    }




}