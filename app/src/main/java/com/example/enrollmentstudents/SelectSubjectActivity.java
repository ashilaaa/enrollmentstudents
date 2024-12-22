package com.example.enrollmentstudents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelectSubjectActivity extends AppCompatActivity {

    CheckBox cbSubject1, cbSubject2, cbSubject3, cbSubject4, cbSubject6, cbSubject7, cbSubject8, cbSubject9, cbSubject10, cbSubject11;
    Button btnSubmitSubjects, btnSummary;

    StringBuilder selectedSubjects = new StringBuilder();
    int totalCredits = 0;

    // Firebase
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Subjects");

        cbSubject1 = findViewById(R.id.cbSubject1);
        cbSubject2 = findViewById(R.id.cbSubject2);
        cbSubject3 = findViewById(R.id.cbSubject3);
        cbSubject4 = findViewById(R.id.cbSubject4);
        cbSubject6 = findViewById(R.id.cbSubject6);
        cbSubject7 = findViewById(R.id.cbSubject7);
        cbSubject8 = findViewById(R.id.cbSubject8);
        cbSubject9 = findViewById(R.id.cbSubject9);
        cbSubject10 = findViewById(R.id.cbSubject10);
        cbSubject11 = findViewById(R.id.cbSubject11);

        btnSubmitSubjects = findViewById(R.id.btnSubmitSubjects);
        btnSummary = findViewById(R.id.btnSummary);

        btnSubmitSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSubjects.setLength(0);
                totalCredits = 0;

                if (cbSubject1.isChecked()) {
                    totalCredits += 4;
                    selectedSubjects.append("Writing Academic (4 Credits)\n");
                }
                if (cbSubject2.isChecked()) {
                    totalCredits += 6;
                    selectedSubjects.append("Survival Life (6 Credits)\n");
                }
                if (cbSubject3.isChecked()) {
                    totalCredits += 8;
                    selectedSubjects.append("Network Security (8 Credits)\n");
                }
                if (cbSubject4.isChecked()) {
                    totalCredits += 6;
                    selectedSubjects.append("Math (6 Credits)\n");
                }
                if (cbSubject6.isChecked()) {
                    totalCredits += 5;
                    selectedSubjects.append("Survival English (5 Credits)\n");
                }
                if (cbSubject7.isChecked()) {
                    totalCredits += 4;
                    selectedSubjects.append("Numerical Method (4 Credits)\n");
                }
                if (cbSubject8.isChecked()) {
                    totalCredits += 4;
                    selectedSubjects.append("OOVP (4 Credits)\n");
                }
                if (cbSubject9.isChecked()) {
                    totalCredits += 4;
                    selectedSubjects.append("Discrete Mathematics (4 Credits)\n");
                }
                if (cbSubject10.isChecked()) {
                    totalCredits += 4;
                    selectedSubjects.append("Religion (4 Credits)\n");
                }
                if (cbSubject11.isChecked()) {
                    totalCredits += 4;
                    selectedSubjects.append("Indonesian Language (4 Credits)\n");
                }

                if (totalCredits > 24) {
                    Toast.makeText(SelectSubjectActivity.this, "Total credits exceed 24. Please select fewer subjects.", Toast.LENGTH_SHORT).show();
                } else if (totalCredits == 0) {
                    Toast.makeText(SelectSubjectActivity.this, "Please select at least one subject.", Toast.LENGTH_SHORT).show();
                } else {
                    saveSubjectsToDatabase();
                }
            }
        });

        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectSubjectActivity.this, SummaryActivity.class);
                intent.putExtra("selectedSubjects", selectedSubjects.toString());
                intent.putExtra("totalCredits", totalCredits);
                startActivity(intent);
            }
        });
    }

    private void saveSubjectsToDatabase() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference userSubjectsRef = databaseReference.child(userId);

        userSubjectsRef.setValue(new UserSubjects(selectedSubjects.toString(), totalCredits))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SelectSubjectActivity.this, "Subjects successfully submitted!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SelectSubjectActivity.this, "Failed to submit subjects. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static class UserSubjects {
        public String subjects;
        public int totalCredits;

        public UserSubjects() {
        }

        public UserSubjects(String subjects, int totalCredits) {
            this.subjects = subjects;
            this.totalCredits = totalCredits;
        }
    }
}
