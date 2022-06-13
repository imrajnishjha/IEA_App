package com.example.ieaapp;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MyGrievances extends AppCompatActivity {

    RecyclerView myGrievancesRecyclerView;
    AppCompatButton myGrievancesBackButton;
    MyGrievancesAdapter myGrievancesAdapter;
    FirebaseRecyclerOptions<MyGrievanceModel> options;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grievances);

        myGrievancesRecyclerView = (RecyclerView) findViewById(R.id.my_grievances_recyclerView);
        myGrievancesRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this));
        myGrievancesBackButton = findViewById(R.id.my_grievances_back_button);

        options = new FirebaseRecyclerOptions.Builder<MyGrievanceModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Unsolved Grievances").orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail()), MyGrievanceModel.class)
                .build();

        myGrievancesAdapter = new MyGrievancesAdapter(options);
        myGrievancesRecyclerView.setAdapter(myGrievancesAdapter);
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
        myGrievancesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myGrievancesAdapter.stopListening();
    }
}

