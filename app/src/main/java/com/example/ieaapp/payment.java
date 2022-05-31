package com.example.ieaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class payment extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        String[] pricing = getResources().getStringArray(R.array.pricing);
        ArrayAdapter<String> arrayAdapterPricing = new ArrayAdapter<>(getBaseContext(), R.layout.drop_down_item, pricing);
        AutoCompleteTextView autoCompleteTextViewPricing = findViewById(R.id.autocomplete_pricing);
        autoCompleteTextViewPricing.setAdapter(arrayAdapterPricing);

        String[] payment = getResources().getStringArray(R.array.paymethod);
        ArrayAdapter<String> arrayAdapterPaymethod = new ArrayAdapter<>(getBaseContext(), R.layout.drop_down_item, payment);
        AutoCompleteTextView autoCompleteTextViewPayment = findViewById(R.id.autocomplete_payment);
        autoCompleteTextViewPayment.setAdapter(arrayAdapterPaymethod);

        AppCompatButton registrationBackButton = findViewById(R.id.payment_back_button);


        registrationBackButton.setOnClickListener(view -> {
            finish();

        });

        AppCompatButton paynow = findViewById(R.id.paynow_btn);
        paynow.setOnClickListener(view -> startActivity(new Intent(payment.this, payment.class)));

    }
}