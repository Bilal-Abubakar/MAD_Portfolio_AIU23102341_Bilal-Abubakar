// RegistrationActivity.java
package com.example.regislog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    private EditText etUsername, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvLoginLink;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        databaseHelper = new DatabaseHelper(this);
        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLoginLink = findViewById(R.id.tvLoginLink);
    }

    private void setupClickListeners() {
        btnRegister.setOnClickListener(v -> attemptRegistration());
        tvLoginLink.setOnClickListener(v -> navigateToLogin());
    }

    private void attemptRegistration() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (validateInputs(username, email, password, confirmPassword)) {
            if (databaseHelper.registerUser(username, email, password)) {
                showMessage("Registration successful!");
                navigateToLogin();
            } else {
                showMessage("Registration failed!");
            }
        }
    }

    private boolean validateInputs(String username, String email, String password, String confirmPassword) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showMessage("Please fill all fields");
            return false;
        }

        if (username.length() < 3) {
            showMessage("Username must be at least 3 characters");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showMessage("Please enter a valid email");
            return false;
        }

        if (password.length() < 6) {
            showMessage("Password must be at least 6 characters");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showMessage("Passwords do not match");
            return false;
        }

        if (databaseHelper.checkUsername(username)) {
            showMessage("Username already exists");
            return false;
        }

        if (databaseHelper.checkEmail(email)) {
            showMessage("Email already registered");
            return false;
        }

        return true;
    }

    private void navigateToLogin() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}