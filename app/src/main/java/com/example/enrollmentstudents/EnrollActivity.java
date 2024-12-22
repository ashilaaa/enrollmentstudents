package com.example.enrollmentstudents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EnrollActivity extends AppCompatActivity {

    private EditText editPassword;
    private Button btnSubmitEnrollment;
    private FirebaseAuth mAuth; // Firebase Authentication instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        editPassword = findViewById(R.id.editPassword);
        btnSubmitEnrollment = findViewById(R.id.btnSubmitEnrollment);

        // Set button click listener
        btnSubmitEnrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPassword = editPassword.getText().toString().trim();

                if (enteredPassword.isEmpty()) {
                    Toast.makeText(EnrollActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                } else {
                    verifyPassword(enteredPassword);
                }
            }
        });
    }

    /**
     * Verifies the entered password with the current user's password using Firebase Authentication.
     */
    private void verifyPassword(String enteredPassword) {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String email = currentUser.getEmail(); // Get the current user's email

            // Reauthenticate user
            mAuth.signInWithEmailAndPassword(email, enteredPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(EnrollActivity.this, "Enrollment successful!", Toast.LENGTH_SHORT).show();
                            redirectToSelectSubjectActivity();
                        } else {
                            Toast.makeText(EnrollActivity.this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No user is currently logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Redirects to SelectSubjectActivity after successful enrollment.
     */
    private void redirectToSelectSubjectActivity() {
        Intent intent = new Intent(EnrollActivity.this, SelectSubjectActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }
}
