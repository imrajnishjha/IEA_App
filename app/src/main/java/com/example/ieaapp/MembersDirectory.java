package com.example.ieaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MembersDirectory extends AppCompatActivity {

    RecyclerView memberDirectoryRecyclerView;
    MembersDirectoryAdapter membersDirectoryAdapter;
    AppCompatButton membersDirectoryBackButton, membersDirectoryLogoutButton;
    FirebaseAuth mAuth;
    TextView membersDirectoryLogoutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_directory);

        memberDirectoryRecyclerView = (RecyclerView) findViewById(R.id.members_directory_recycler_view);
        memberDirectoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        membersDirectoryBackButton = findViewById(R.id.members_directory_back_button);
        membersDirectoryLogoutButton = findViewById(R.id.members_directory_logout_button);
        membersDirectoryLogoutText = findViewById(R.id.members_directory_logout_text);
        mAuth = FirebaseAuth.getInstance();


        FirebaseRecyclerOptions<MembersDirectoryModel> options =
                new FirebaseRecyclerOptions.Builder<MembersDirectoryModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Members Directory"), MembersDirectoryModel.class)
                        .build();

        membersDirectoryAdapter = new MembersDirectoryAdapter(options);
        memberDirectoryRecyclerView.setAdapter(membersDirectoryAdapter);

        membersDirectoryBackButton.setOnClickListener(view -> finish());

        membersDirectoryLogoutText.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(MembersDirectory.this, LandingPage.class));
        });

        membersDirectoryLogoutButton.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(MembersDirectory.this, LandingPage.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        membersDirectoryAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        membersDirectoryAdapter.stopListening();
    }
}