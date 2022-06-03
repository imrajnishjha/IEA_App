package com.example.ieaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class explore_us extends AppCompatActivity {

    TextView join_now;
    AppCompatButton exploreUsBackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_us);

        join_now = findViewById(R.id.join_now);

        join_now.setOnClickListener(view -> startActivity(new Intent(explore_us.this, Registration.class)));

        exploreUsBackButton = findViewById(R.id.exploreus_back_button);


        exploreUsBackButton.setOnClickListener(view -> {
            finish();
        });

    }
}