package com.example.ieaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class BAAS extends AppCompatActivity {

    RecyclerView baasListRecyclerView;
    FirebaseRecyclerOptions<BaasListModel> options;
    BaasListAdapter baasListAdapter;
    AppCompatButton baasBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baas);
        baasBackBtn = findViewById(R.id.baas_back_button);

        baasListRecyclerView = findViewById(R.id.baas_list_rv);
        baasListRecyclerView.setLayoutManager(new MembersDirectory.WrapContentLinearLayoutManager(this));
        options = new FirebaseRecyclerOptions.Builder<BaasListModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Registered Users"), BaasListModel.class)
                .build();

        baasListAdapter = new BaasListAdapter(options);
        baasListRecyclerView.setAdapter(baasListAdapter);
        baasListAdapter.startListening();

        baasBackBtn.setOnClickListener(view -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        baasListAdapter.startListening();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        baasListAdapter.stopListening();
//    }

}