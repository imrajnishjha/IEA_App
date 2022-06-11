package com.example.ieaapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class UserProfile extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userEmail = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
    String userEmailConverted;
    Uri resultUri;

    {
        assert userEmail != null;
        userEmailConverted = userEmail.replaceAll("\\.", "%7");
    }

    DatabaseReference ref = database.getReference("Registered Users/" + userEmailConverted);

    ImageView userProfileImage;
    TextView userProfileName, userMembershipId, userMembershipDate;
    EditText userContactNumberEdtTxt, userDateOfBirthEdtTxt, userEmailEdtTxt, userCompanyNameEdtTxt, userAddressEdtTxt;
    AppCompatButton saveProfileBtn;
    ActivityResultLauncher<String> mGetContent;

    StorageReference storageProfilePicReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userProfileImage = findViewById(R.id.user_profile_image);
        userProfileName = findViewById(R.id.user_profile_name);
        userMembershipId = findViewById(R.id.user_membership_id);
        userMembershipDate = findViewById(R.id.user_membership_date);
        userContactNumberEdtTxt = findViewById(R.id.user_profile_contactNumber_edtTxt);
        userDateOfBirthEdtTxt = findViewById(R.id.user_profile_dateOfBirth_edtTxt);
        userEmailEdtTxt = findViewById(R.id.user_profile_email_edtTxt);
        userCompanyNameEdtTxt = findViewById(R.id.user_profile_company_name_edtTxt);
        userAddressEdtTxt = findViewById(R.id.user_profile_address_edtTxt);
        saveProfileBtn = findViewById(R.id.user_profile_save_button);

        storageProfilePicReference = FirebaseStorage.getInstance().getReference();

        StorageReference fileRef = storageProfilePicReference.child("User Profile Pictures/" + mAuth.getCurrentUser().getEmail().toString() + "ProfilePicture");
        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(userProfileImage.getContext())
                        .load(uri)
                        .placeholder(R.drawable.iea_logo)
                        .circleCrop()
                        .error(R.drawable.iea_logo)
                        .into(userProfileImage);
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userProfileNameStr = Objects.requireNonNull(Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString());
                userProfileName.setText(userProfileNameStr);

                String userMembershipIdStr = Objects.requireNonNull(dataSnapshot.child("member_id").getValue()).toString();
                userMembershipId.setText(userMembershipIdStr);

                String userMembershipDateStr = Objects.requireNonNull(dataSnapshot.child("date_of_membership").getValue()).toString();
                userMembershipDate.setText(userMembershipDateStr);

                String userContactNumberStr = Objects.requireNonNull(dataSnapshot.child("phone_number").getValue()).toString();
                userContactNumberEdtTxt.setText(userContactNumberStr);

                String userDOBStr = Objects.requireNonNull(dataSnapshot.child("date_of_birth").getValue()).toString();
                if (dataSnapshot.child("date_of_birth").getValue().toString().equals("")) {
                    userDateOfBirthEdtTxt.setText(userDOBStr);
                } else {
                    userDateOfBirthEdtTxt.setText(userDOBStr);
                    userDateOfBirthEdtTxt.setFocusable(false);
                    userDateOfBirthEdtTxt.setTextColor(getResources().getColor(R.color.grey));
                }

                String userEmailStr = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                userEmailEdtTxt.setText(userEmailStr);

                String userCompanyNameStr = Objects.requireNonNull(dataSnapshot.child("company_name").getValue()).toString();
                userCompanyNameEdtTxt.setText(userCompanyNameStr);

                String userAddressStr = Objects.requireNonNull(dataSnapshot.child("address").getValue()).toString();
                userAddressEdtTxt.setText(userAddressStr);

                String corePictureUrl = Objects.requireNonNull(dataSnapshot.child("purl").getValue()).toString();
                Glide.with(userProfileImage.getContext())
                        .load(corePictureUrl)
                        .placeholder(R.drawable.iea_logo)
                        .circleCrop()
                        .error(R.drawable.iea_logo)
                        .into(userProfileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        saveProfileBtn.setOnClickListener(view -> {
            String userContactNumberStr = userContactNumberEdtTxt.getText().toString();
            String userDOBStr = userDateOfBirthEdtTxt.getText().toString();
            String userCompanyNameStr = userCompanyNameEdtTxt.getText().toString();
            String userAddressStr = userAddressEdtTxt.getText().toString();

            updateData(userContactNumberStr, userDOBStr, userCompanyNameStr, userAddressStr);

            if(resultUri != null){
                uploadImageToFirebase(resultUri);
            }
        });

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                String destinationUri = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
                UCrop.of(result, Uri.fromFile(new File(getCacheDir(),destinationUri)))
                        .withAspectRatio(1,1)
                        .start(UserProfile.this);
            }
        });

        userProfileImage.setOnClickListener(view -> {
            mGetContent.launch("image/*");
        });

    }

    private void updateData(String userContactNumberStr, String userDOBStr, String userCompanyNameStr, String userAddressStr) {
        HashMap UserData = new HashMap();
        UserData.put("phone_number", userContactNumberStr);
        UserData.put("date_of_birth", userDOBStr);
        UserData.put("company_name", userCompanyNameStr);
        UserData.put("address", userAddressStr);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users/" + userEmailConverted);
        databaseReference.updateChildren(UserData).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UserProfile.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfile.this, "Failed to Update Data", Toast.LENGTH_SHORT).show();
            }
        });
//        uploadImageToFirebase(resultUri);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            resultUri = UCrop.getOutput(data);
            userProfileImage.setImageURI(resultUri);



        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        StorageReference fileRef = storageProfilePicReference.child("User Profile Pictures/" + mAuth.getCurrentUser().getEmail().toString()+"ProfilePicture");
        fileRef.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(userProfileImage.getContext())
                                .load(uri)
                                .placeholder(R.drawable.iea_logo)
                                .circleCrop()
                                .error(R.drawable.iea_logo)
                                .into(userProfileImage);

                        HashMap UserData = new HashMap();
                        UserData.put("purl", uri.toString());

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users/" + userEmailConverted);
                        databaseReference.updateChildren(UserData).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(UserProfile.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserProfile.this, "Failed to Update Data", Toast.LENGTH_SHORT).show();
                            }
                        });

                        Toast.makeText(UserProfile.this, "Profile Picture Updated", Toast.LENGTH_SHORT).show();
                    }

                });
                String fileReference = String.valueOf(fileRef.getDownloadUrl());
                Log.d("downloadUrl", fileReference);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfile.this, "Failed to Update Profile Picture", Toast.LENGTH_SHORT).show();
            }
        });
    }
}