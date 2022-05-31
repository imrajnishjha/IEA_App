package com.example.ieaapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class CoreTeamMembers extends AppCompatActivity {

    RecyclerView coreMemberRecyclerView;
    AppCompatButton coreTeamMemberBackButton;
    CoreMemberAdapter coreMemberAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_team_members);

        coreMemberRecyclerView = (RecyclerView) findViewById(R.id.core_member_recycler_view);
        coreMemberRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coreTeamMemberBackButton = findViewById(R.id.core_team_member_back_button);

        FirebaseRecyclerOptions<CoreMemberModel> options =
                new FirebaseRecyclerOptions.Builder<CoreMemberModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Core Team Members"), CoreMemberModel.class)
                        .build();

        coreMemberAdapter = new CoreMemberAdapter(options);
        coreMemberRecyclerView.setAdapter(coreMemberAdapter);

        coreTeamMemberBackButton.setOnClickListener(view -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        coreMemberAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        coreMemberAdapter.stopListening();
    }
}