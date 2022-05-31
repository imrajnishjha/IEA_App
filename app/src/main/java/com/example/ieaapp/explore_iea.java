package com.example.ieaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;

public class explore_iea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_iea);

        AppCompatButton exploreback_btn = findViewById(R.id.explore_back_button);
        exploreback_btn.setOnClickListener(view -> {finish();});
    }
}