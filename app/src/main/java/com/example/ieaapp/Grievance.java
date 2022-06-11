package com.example.ieaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Grievance extends AppCompatActivity {

    AppCompatButton grievance_back_button;
    AppCompatButton grievance_submit;
    FirebaseDatabase grievancedb;
    DatabaseReference grievancereference;
    TextInputEditText issue;
    AutoCompleteTextView dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievance);

        grievance_back_button = findViewById(R.id.grievance_back_button);

        dropdownInit();

        grievance_back_button.setOnClickListener(view -> finish());

        grievance_submit=findViewById(R.id.grievance_submit);
        grievance_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grievancedb = FirebaseDatabase.getInstance();
                grievancereference=grievancedb.getReference("Grievances");
                issue=findViewById(R.id.issue_input_edttxt);
                dept=findViewById(R.id.autocomplete_department_field);
                String complain=issue.getText().toString();
                String departments=dept.getText().toString();
                String compainerEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                grievancehelperclass Grievancehelperclass = new grievancehelperclass(compainerEmail,departments,complain);
                grievancereference.push().setValue(Grievancehelperclass);
                issue.setText("");
                Toast.makeText(Grievance.this, "We have received your complaint", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        dropdownInit();
    }

    public void dropdownInit() {
        String[] grievance_departments = getResources().getStringArray(R.array.grievance_department);
        ArrayAdapter<String> arrayAdapterDepartments = new ArrayAdapter<>(getBaseContext(), R.layout.drop_down_item, grievance_departments);
        AutoCompleteTextView autoCompleteTextViewDepartments = findViewById(R.id.autocomplete_department_field);
        autoCompleteTextViewDepartments.setAdapter(arrayAdapterDepartments);
    }
}