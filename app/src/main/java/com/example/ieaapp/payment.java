package com.example.ieaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class payment extends AppCompatActivity implements PaymentResultListener {

    private AppCompatButton paynow;
    private String memberfees;


    String fullname, email, phoneNo, companyName, Department, Turnover;

    FirebaseDatabase memberDirectoryRoot;
    DatabaseReference memberDirectoryRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
        fullname=intent.getStringExtra("name");
        email=intent.getStringExtra("email");
        phoneNo=intent.getStringExtra("phoneno");
        companyName=intent.getStringExtra("cname");
        Department=intent.getStringExtra("department");
        Turnover=intent.getStringExtra("annual_turn");
        memberfees=intent.getStringExtra("memberfee");


        String[] pricing = getResources().getStringArray(R.array.pricing);
        ArrayAdapter<String> arrayAdapterPricing = new ArrayAdapter<>(getBaseContext(), R.layout.drop_down_item, pricing);
        TextView autoCompleteTextViewPricing = findViewById(R.id.member_price);

        String[] payment = getResources().getStringArray(R.array.paymethod);
        ArrayAdapter<String> arrayAdapterPaymethod = new ArrayAdapter<>(getBaseContext(), R.layout.drop_down_item, payment);
        AutoCompleteTextView autoCompleteTextViewPayment = findViewById(R.id.autocomplete_payment);
        autoCompleteTextViewPayment.setAdapter(arrayAdapterPaymethod);

        AppCompatButton registrationBackButton = findViewById(R.id.payment_back_button);


        registrationBackButton.setOnClickListener(view -> {
            finish();

        });


        TextView memberprice= findViewById(R.id.member_price);
        memberprice.setText(memberfees);
        paynow = findViewById(R.id.paynow_btn);
        String finalAmount = feeConverter(memberfees);



        //Payment

        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // amount that is entered by user.
                String samount=finalAmount;

                // rounding off the amount.
                int amounts = Math.round(Float.parseFloat(samount) * 100);

                // initialize Razorpay account.
                Checkout checkout = new Checkout();

                // set your id as below
                checkout.setKeyID("rzp_test_USimbGClz1VOxE");

                // set image
                checkout.setImage(R.drawable.members);

                // initialize json object
                JSONObject object = new JSONObject();
                try {
                    // to put name
                    object.put("name", "IEA");

                    // put description
                    object.put("description", "Membership fees");

                    // to set theme color
                    object.put("theme.color", "");

                    // put the currency
                    object.put("currency", "INR");

                    // put amount
                    object.put("amount", amounts);

                    // put mobile number
                    object.put("prefill.contact", "9284064503");

                    // put email
                    object.put("prefill.email", "chaitanyamunje@gmail.com");

                    // open razorpay to checkout activity
                    checkout.open(payment.this, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public String feeConverter(String s){
        if(s.equals("Rs. 3,658/Month *including gst*")){
            return "3658";
        }
        if(s.equals("Rs. 6,018/Month *including gst*")){
            return "6018";
        }
        if(s.equals("Rs. 12,980/Month *including gst*")){
            return "12980";
        }
        return "0";
    }



    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"Payment is unsucessful",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentSuccess(String s) {

        Toast.makeText(this,"Payment is sucessful",Toast.LENGTH_SHORT).show();
        memberDirectoryRoot = FirebaseDatabase.getInstance();
        memberDirectoryRef = memberDirectoryRoot.getReference("Members Directory");

        UserRegistrationHelperClass userRegistrationHelperClass = new UserRegistrationHelperClass(fullname, email, phoneNo, companyName, Department, Turnover);

        memberDirectoryRef.child(email.replaceAll("\\.", "%7")).setValue(userRegistrationHelperClass);
    }

}