package com.example.ieaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        AppCompatButton proceed_pay= findViewById(R.id.proceed_to_pay_btn);

        proceed_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this,payment.class);
                EditText fullname = findViewById(R.id.fullname);
                EditText email = findViewById(R.id.registrationEmail);
                EditText phoneNo = findViewById(R.id.number);
                EditText Comapany_name = findViewById(R.id.company_name);
                AutoCompleteTextView Department = findViewById(R.id.autocomplete_department_field);
                EditText Annual_turn = findViewById(R.id.annual_turnover);
                AutoCompleteTextView member_fee = findViewById(R.id.member_price);

                intent.putExtra("name",fullname.getText().toString());
                intent.putExtra("email",email.getText().toString());
                intent.putExtra("phoneno",phoneNo.getText().toString());
                intent.putExtra("cname",Comapany_name.getText().toString());
                intent.putExtra("department",Department.getText().toString());
                intent.putExtra("annual_term",Annual_turn.getText().toString());
                intent.putExtra("memberfee",member_fee.getText().toString());
                startActivity(intent);

            }
        });

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
        AutoCompleteTextView autoCompleteTextViewPricing = findViewById(R.id.member_price);
        autoCompleteTextViewPricing.setAdapter(arrayAdapterPricing);
    }
}