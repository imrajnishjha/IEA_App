package com.example.ieaapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Refer extends AppCompatActivity {

    AppCompatButton referBackButton, referButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);

        referBackButton = findViewById(R.id.refer_back_button);
        referButton = findViewById(R.id.refer_button);

        referBackButton.setOnClickListener(view -> finish());

    }
}