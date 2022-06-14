package com.example.ieaapp;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MembersDirectory extends AppCompatActivity {

    RecyclerView memberDirectoryRecyclerView;
    AppCompatButton memberDirectoryBackButton, memberSearchButton;
    MembersDirectoryAdapter memberDirectoryAdapter;
    FirebaseRecyclerOptions<MembersDirectoryModel> options;
    AutoCompleteTextView memberSearchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_directory);

        memberDirectoryRecyclerView = (RecyclerView) findViewById(R.id.members_directory_recycler_view);
        memberDirectoryRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this));
        memberDirectoryBackButton = findViewById(R.id.members_directory_back_button);
        memberSearchTextView = findViewById(R.id.member_search_autocomplete);
        memberSearchButton = findViewById(R.id.member_search_icon);

        options = new FirebaseRecyclerOptions.Builder<MembersDirectoryModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Registered Users"), MembersDirectoryModel.class)
                .build();

        memberDirectoryBackButton.setOnClickListener(view -> finish());


        memberSearchButton.setOnClickListener(view -> {
            options = new FirebaseRecyclerOptions.Builder<MembersDirectoryModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Registered Users").orderByChild("industry_type").equalTo(memberSearchTextView.getText().toString()), MembersDirectoryModel.class)
                    .build();
            memberDirectoryAdapter = new MembersDirectoryAdapter(options);
            memberDirectoryRecyclerView.setAdapter(memberDirectoryAdapter);
            memberDirectoryAdapter.startListening();
        });


        memberDirectoryAdapter = new MembersDirectoryAdapter(options);
        memberDirectoryRecyclerView.setAdapter(memberDirectoryAdapter);

    }

    public void dropdownInit() {
        String[] departments = getResources().getStringArray(R.array.department);
        ArrayAdapter<String> arrayAdapterDepartments = new ArrayAdapter<>(getBaseContext(), R.layout.drop_down_item, departments);
        AutoCompleteTextView autoCompleteTextViewDepartments = findViewById(R.id.member_search_autocomplete);
        autoCompleteTextViewDepartments.setAdapter(arrayAdapterDepartments);
    }

    @Override
    public void onResume() {
        super.onResume();
        dropdownInit();
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
        memberDirectoryAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        memberDirectoryAdapter.stopListening();
    }

}