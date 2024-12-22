package com.example.enrollmentstudents;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

    public class FirebaseHelper {

        // Fungsi untuk menyimpan data ke Firebase
        public void saveData() {
            // Referensi ke database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference subjectsRef = database.getReference("Subjects");

            // Menambahkan USER_ID_1
            DatabaseReference user1Ref = subjectsRef.child("USER_ID_1");
            user1Ref.child("subjects").setValue("Writing Academic (4 Credits)\nSurvival Life (6 Credits)\n");
            user1Ref.child("totalCredits").setValue(10);

            // Menambahkan USER_ID_2
            DatabaseReference user2Ref = subjectsRef.child("USER_ID_2");
            user2Ref.child("subjects").setValue("Network Security (8 Credits)\n");
            user2Ref.child("totalCredits").setValue(8);
        }
    }


