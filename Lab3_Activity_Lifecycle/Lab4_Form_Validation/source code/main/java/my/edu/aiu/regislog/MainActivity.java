// MainActivity.java
package com.example.regislog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvWelcome, tvUserInfo;
    private Button btnLogout;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        initializeViews();
        displayUserInfo();
        setupClickListeners();
    }

    private void initializeViews() {
        tvWelcome = findViewById(R.id.tvWelcome);
        tvUserInfo = findViewById(R.id.tvUserInfo);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void displayUserInfo() {
        String username = getIntent().getStringExtra("USERNAME");
        String email = databaseHelper.getUserEmail(username);

        tvWelcome.setText("Welcome to RegisLog!");
        tvUserInfo.setText("Username: " + username + "\nEmail: " + email);
    }

    private void setupClickListeners() {
        btnLogout.setOnClickListener(v -> logout());
    }

    private void logout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}