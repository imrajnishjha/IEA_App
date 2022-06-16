package com.example.ieaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Grievance extends AppCompatActivity {

    AppCompatButton grievance_back_button;
    AppCompatButton grievance_submit;
    FirebaseDatabase grievancedb;
    DatabaseReference grievancereference, grievancereference2;
    TextInputEditText issue;
    AutoCompleteTextView dept;
    CardView myGrievancesBtn;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievance);

        grievance_back_button = findViewById(R.id.grievance_back_button);
        myGrievancesBtn = findViewById(R.id.my_grievances_btn);

        dropdownInit();

        grievance_back_button.setOnClickListener(view -> finish());

        grievance_submit=findViewById(R.id.grievance_submit);
        grievance_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grievancedb = FirebaseDatabase.getInstance();
                String complainerEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();


                issue=findViewById(R.id.issue_input_edttxt);
                dept=findViewById(R.id.grievance_department_field);
                String complain=issue.getText().toString();
                String departments=dept.getText().toString();
                grievancereference=grievancedb.getReference("Unsolved Grievances");
                grievancehelperclass Grievancehelperclass = new grievancehelperclass(complainerEmail,departments,complain,"Unsolved");
                grievancehelperclass Grievancehelperclass2 = new grievancehelperclass(complainerEmail,departments,complain,"Unsolved");
//                grievancereference.push().setValue(Grievancehelperclass);
//                grievancereference2.push().setValue(Grievancehelperclass);
                String grievanceKey = grievancereference.push().getKey();


                grievancereference2=grievancedb.getReference("Unresolved Grievances").child(complainerEmail.replaceAll("\\.", "%7")).child(grievanceKey);
                GrievanceModel solvedmodel = new GrievanceModel(complain,departments,complainerEmail,"Unsolved");
                grievancereference2.setValue(solvedmodel);
                grievancereference.child(grievanceKey).setValue(solvedmodel);

                new AlertDialog.Builder(Grievance.this)
                        .setTitle("Grievance ID")
                        .setMessage("Your Grievance ID is: "+grievanceKey).show();

                issue.setText("");
                Toast.makeText(Grievance.this, "We have received your complaint", Toast.LENGTH_LONG).show();
            }
        });

        myGrievancesBtn.setOnClickListener(view -> startActivity(new Intent(Grievance.this, MyGrievances.class)));
    }

    @Override
    public void onResume(){
        super.onResume();
        dropdownInit();
    }

    public void dropdownInit() {
        String[] grievance_departments = getResources().getStringArray(R.array.grievance_department);
        ArrayAdapter<String> arrayAdapterDepartments = new ArrayAdapter<>(getBaseContext(), R.layout.drop_down_item, grievance_departments);
        AutoCompleteTextView autoCompleteTextViewDepartments = findViewById(R.id.grievance_department_field);
        autoCompleteTextViewDepartments.setAdapter(arrayAdapterDepartments);
    }
}