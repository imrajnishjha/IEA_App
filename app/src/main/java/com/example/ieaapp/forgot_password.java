package com.example.ieaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {

    TextView forgotPassInfo;
    EditText resetEmail;
    AppCompatButton reset_Btn,forgotPass_Backbtn;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPassInfo = findViewById(R.id.ForgotPassInfo);
        resetEmail= findViewById(R.id.forgotPass_email_edtTxt);
        reset_Btn = findViewById(R.id.ResetPass_btn);
        forgotPass_Backbtn= findViewById(R.id.forgotPass_back_button);
        mAuth= FirebaseAuth.getInstance();

        forgotPass_Backbtn.setOnClickListener(view -> finish());

        reset_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = resetEmail.getText().toString();
                if(mail.isEmpty()){
                    forgotPassInfo.setTextColor(Color.parseColor("#ff2c2c"));
                    resetEmail.requestFocus();
                    forgotPassInfo.setText("Please Enter Email");
                }
                else{
                    mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            forgotPassInfo.setTextColor(Color.parseColor("#03AC13"));
                            forgotPassInfo.setText("Check your mail to reset your password");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            forgotPassInfo.setTextColor(Color.parseColor("#ff2c2c"));
                            resetEmail.requestFocus();
                            forgotPassInfo.setText("This mail is not valid, Enter the Correct Mail");
                        }
                    });
                }
            }
        });

    }
}