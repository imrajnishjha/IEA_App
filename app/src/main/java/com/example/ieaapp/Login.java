package com.example.ieaapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Objects;

public class Login extends AppCompatActivity {

    AppCompatButton loginBackButton, signInButton;
    EditText loginEmail, loginPassword;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBackButton = findViewById(R.id.login_back_button);
        signInButton = findViewById(R.id.signin_btn);

        mAuth = FirebaseAuth.getInstance();

        loginBackButton.setOnClickListener(view -> finish());

        signInButton.setOnClickListener(view -> loginUser());
    }

    private void loginUser(){
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        String login_email = loginEmail.getText().toString();
        String login_password = loginPassword.getText().toString();

        if(TextUtils.isEmpty(login_email)){
            loginEmail.setError("Email cannot be empty!");
            loginEmail.requestFocus();
        }else if(TextUtils.isEmpty(login_password)){
            loginPassword.setError("Password cannot be empty!");
            loginPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(login_email, login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                public String replacePeriod(String login_email) {
                    return login_email.replaceAll("\\.", "%7");
                }
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Login.this, "You are logged in!", Toast.LENGTH_SHORT).show();
                        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                Log.d("Messaging", "onComplete: Messaging On Complete");
                                String token = task.getResult();
                                Log.d("Messaging", "Token: "+token);
                                sendTokenToDatabase(token);
                            }
                        });
                        startActivity(new Intent(Login.this, explore_menu.class).putExtra("userEmail", replacePeriod(login_email)));
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Login Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendTokenToDatabase(String token) {
        HashMap userToken = new HashMap();
        userToken.put("user_token", token);

        FirebaseDatabase.getInstance().getReference().child("Registered Users/"+loginEmail.getText().toString().replaceAll("\\.", "%7"))
                .updateChildren(userToken);
    }
}