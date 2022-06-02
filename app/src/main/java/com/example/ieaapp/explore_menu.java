package com.example.ieaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class explore_menu extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_menu);

        mAuth = FirebaseAuth.getInstance();

        ImageView logout = findViewById(R.id.logout_img);
        logout.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(explore_menu.this, LandingPage.class));

        });

        CardView explore=(CardView) findViewById(R.id.explore);
        explore.setOnClickListener(view -> startActivity(new Intent(explore_menu.this, explore_iea.class)));

        CardView core_mem=(CardView) findViewById(R.id.core_mem);
        core_mem.setOnClickListener(view -> startActivity(new Intent(explore_menu.this, CoreTeamMembers.class)));

    }
}