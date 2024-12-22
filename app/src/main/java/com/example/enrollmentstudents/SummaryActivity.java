package com.example.enrollmentstudents;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SummaryActivity extends AppCompatActivity {

    TextView tvSelectedSubjects, tvTotalCredits;

    // Firebase
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tvSelectedSubjects = findViewById(R.id.tvSelectedSubjects);
        tvTotalCredits = findViewById(R.id.tvTotalCredits);

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Subjects");

        // Load data from Firebase
        loadUserSubjects();
    }

    private void loadUserSubjects() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference userSubjectsRef = databaseReference.child(userId);

        userSubjectsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String subjects = snapshot.child("subjects").getValue(String.class);
                    Integer totalCredits = snapshot.child("totalCredits").getValue(Integer.class);

                    tvSelectedSubjects.setText(subjects != null ? subjects : "No subjects selected.");
                    tvTotalCredits.setText("Total Credits: " + (totalCredits != null ? totalCredits : 0));
                } else {
                    Toast.makeText(SummaryActivity.this, "No data found for this user.", Toast.LENGTH_SHORT).show();
                    tvSelectedSubjects.setText("No subjects selected.");
                    tvTotalCredits.setText("Total Credits: 0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SummaryActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
