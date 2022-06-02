package com.example.ieaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        AppCompatButton proceed_pay= findViewById(R.id.proceed_to_pay_btn);

        proceed_pay.setOnClickListener(view -> startActivity(new Intent(Registration.this, payment.class)));

        dropdownInit();

        AppCompatButton registrationBackButton = findViewById(R.id.registration_back_button);


        registrationBackButton.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        dropdownInit();
    }

    public void dropdownInit() {
        String[] departments = getResources().getStringArray(R.array.department);
        ArrayAdapter<String> arrayAdapterDepartments = new ArrayAdapter<>(getBaseContext(), R.layout.drop_down_item, departments);
        AutoCompleteTextView autoCompleteTextViewDepartments = findViewById(R.id.autocomplete_department_field);
        autoCompleteTextViewDepartments.setAdapter(arrayAdapterDepartments);

        String[] pricing = getResources().getStringArray(R.array.pricing);
        ArrayAdapter<String> arrayAdapterPricing = new ArrayAdapter<>(getBaseContext(), R.layout.drop_down_item, pricing);
        AutoCompleteTextView autoCompleteTextViewPricing = findViewById(R.id.autocomplete_pricing);
        autoCompleteTextViewPricing.setAdapter(arrayAdapterPricing);
    }
}