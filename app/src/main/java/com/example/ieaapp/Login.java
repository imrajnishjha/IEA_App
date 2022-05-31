package com.example.ieaapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppCompatButton loginBackButton = findViewById(R.id.login_back_button);

        loginBackButton.setOnClickListener(view -> finish());

        AppCompatButton loginButton = findViewById(R.id.signin_btn);
        loginButton.setOnClickListener(view -> startActivity(new Intent(Login.this, explore_menu.class)));
    }
}