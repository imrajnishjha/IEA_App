package com.example.ieaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MemberDirectoryDetail extends AppCompatActivity {
    ImageView memberProfileImage;
    TextView memberProfileName, memberMembershipId, memberMembershipDate,
            memberCompanyName, memberAddress,memberMembershipExpiryDate;
    AppCompatButton memberProfileBackBtn;
    RecyclerView memberProductRecyclerView;
    String memberEmailStr;
    MemberProductAdapter memberProductAdapter;

    FirebaseRecyclerOptions<MemberProductModel> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_directory_detail);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Registered Users");

        memberProfileImage = findViewById(R.id.member_profile_image);
        memberMembershipId = findViewById(R.id.member_membership_id);
        memberProfileName = findViewById(R.id.member_profile_name);
        memberMembershipDate = findViewById(R.id.member_membership_date);
        memberCompanyName = findViewById(R.id.member_company_name);
        memberAddress = findViewById(R.id.member_address);
        memberMembershipExpiryDate=findViewById(R.id.memberExpiryDateId);
        memberProfileBackBtn=findViewById(R.id.memberDetail_back_button);

        memberProfileBackBtn.setOnClickListener(view -> {
            finish();
        });


        String coreItemKey = getIntent().getStringExtra("MemberItemKey");

        ref.child(coreItemKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String memberMembershipDateStr = snapshot.child("date_of_membership").getValue().toString();
                    String memberMembershipIdStr = snapshot.child("member_id").getValue().toString();
                    String memberPhoneNumberStr = snapshot.child("phone_number").getValue().toString();
                    String memberDOBStr = snapshot.child("date_of_birth").getValue().toString();
                    memberEmailStr = snapshot.child("email").getValue().toString();
                    String memberCompanyNameStr = snapshot.child("company_name").getValue().toString();
                    String memberAddressStr = snapshot.child("address").getValue().toString();
                    String memberNameStr = snapshot.child("name").getValue().toString();
                    String memberPictureUrl = snapshot.child("purl").getValue().toString();
                    String memberBioStr = snapshot.child("description").getValue().toString();
                    String memberMembershipExpiryDateStr = yearincrementer(memberMembershipDateStr);

                    memberMembershipId.setText(memberMembershipIdStr);
                    memberMembershipDate.setText(memberMembershipDateStr);
                    memberCompanyName.setText(memberCompanyNameStr);
                    memberAddress.setText(memberAddressStr);
                    memberProfileName.setText(memberNameStr);
                    memberMembershipExpiryDate.setText(memberMembershipExpiryDateStr);

                    Glide.with(memberProfileImage.getContext())
                            .load(memberPictureUrl)
                            .placeholder(R.drawable.iea_logo)
                            .circleCrop()
                            .error(R.drawable.iea_logo)
                            .into(memberProfileImage);




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        memberProductRecyclerView =(RecyclerView) findViewById(R.id.memberProductRecycleView);
        memberProductRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        options = new FirebaseRecyclerOptions.Builder<MemberProductModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Products by Member")
                        .child(coreItemKey.replaceAll("\\.","%7")),MemberProductModel.class)
                .build();
        memberProductAdapter = new MemberProductAdapter(options);
        memberProductRecyclerView.setAdapter(memberProductAdapter);







    }
    public String yearincrementer(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar c =Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE,365);
        date=sdf.format(c.getTime());
        return date;
    }

    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("TAG", "meet a IOOBE in RecyclerView");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        memberProductAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        memberProductAdapter.stopListening();
    }
}