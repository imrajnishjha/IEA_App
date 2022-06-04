package com.example.ieaapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CoreMemberDetail extends AppCompatActivity {

    TextView coreTeamMemberDetailDesignation, coreTeamMemberDetailName;
    ImageView coreTeamMemberDetailProfile;
    AppCompatButton coreTeamMemberContactButton, coreTeamMemberDetailBackButton;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_member_detail);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Core Teams Member");

        coreTeamMemberDetailDesignation = findViewById(R.id.core_team_member_designation_text);
        coreTeamMemberDetailName = findViewById(R.id.core_detail_name);
        coreTeamMemberDetailProfile = findViewById(R.id.core_detail_profile_picture);
        coreTeamMemberContactButton = findViewById(R.id.core_detail_contact_button);
        coreTeamMemberDetailBackButton = findViewById(R.id.core_member_detail_back_button);

        String coreItemKey = getIntent().getStringExtra("CoreItemKey");

        databaseReference.child(coreItemKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String coreMemberName = snapshot.child("name").getValue().toString();
                    String coreMemberDesignation = snapshot.child("designation").getValue().toString();
                    String corePictureUrl = snapshot.child("purl").getValue().toString();

                    coreTeamMemberDetailName.setText(coreMemberName);
                    coreTeamMemberDetailDesignation.setText(coreMemberDesignation);

                    Glide.with(coreTeamMemberDetailProfile.getContext())
                            .load(corePictureUrl)
                            .placeholder(R.drawable.iea_logo)
                            .circleCrop()
                            .error(R.drawable.iea_logo)
                            .into(coreTeamMemberDetailProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        coreTeamMemberDetailBackButton.setOnClickListener(view -> finish());
    }
}