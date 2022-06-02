package com.example.ieaapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class explore_iea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_iea);

        AppCompatButton exploreback_btn = findViewById(R.id.explore_back_button);
        exploreback_btn.setOnClickListener(view -> {finish();});
    }
}