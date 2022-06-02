package com.example.ieaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class explore_menu extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView exploreUsername, descriptionUsername;
    ImageView logoutImg;
    CardView coreMembersCard, memberDirectoryCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_menu);

        exploreUsername = findViewById(R.id.explore_username);
        descriptionUsername = findViewById(R.id.description_username);
        logoutImg = findViewById(R.id.logout_img);
        coreMembersCard = findViewById(R.id.core_mem);
        memberDirectoryCard = findViewById(R.id.member_directory);

        mAuth = FirebaseAuth.getInstance();

        String userEmail = mAuth.getCurrentUser().getEmail();

        String userEmailConverted = userEmail.replaceAll("\\.", "%7");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Registered Users/"+userEmailConverted);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userNameDatabase = Objects.requireNonNull(dataSnapshot.child("name").getValue().toString());
                exploreUsername.setText(userNameDatabase);
                descriptionUsername.setText(userNameDatabase);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



        CardView explore=(CardView) findViewById(R.id.explore);
        explore.setOnClickListener(view -> startActivity(new Intent(explore_menu.this, explore_iea.class)));

        logoutImg.setOnClickListener(view -> {
            mAuth.signOut();
            finish();
        });

        coreMembersCard.setOnClickListener(view -> startActivity(new Intent(explore_menu.this, CoreTeamMembers.class)));

        memberDirectoryCard.setOnClickListener(view -> startActivity(new Intent(explore_menu.this, MembersDirectory.class)));

    }

}