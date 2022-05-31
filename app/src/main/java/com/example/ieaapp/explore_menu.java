package com.example.ieaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class explore_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_menu);

        ImageView logout = findViewById(R.id.logout_img);
        logout.setOnClickListener(view -> {
            finish();

        });

        CardView explore=(CardView) findViewById(R.id.explore);
        explore.setOnClickListener(view -> startActivity(new Intent(explore_menu.this, explore_iea.class)));

        CardView core_mem=(CardView) findViewById(R.id.core_mem);
        core_mem.setOnClickListener(view -> startActivity(new Intent(explore_menu.this, CoreTeamMembers.class)));

    }
}
