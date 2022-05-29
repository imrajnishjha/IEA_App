package com.example.ieaapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        AppCompatButton requestForRegistration = findViewById(R.id.request_for_membership_btn);
        AppCompatButton existingMember = findViewById(R.id.existing_member_btn);

        requestForRegistration.setOnClickListener(view -> startActivity(new Intent(LandingPage.this, Registration.class)));

        existingMember.setOnClickListener(view -> startActivity(new Intent(LandingPage.this, Login.class)));
    }
}