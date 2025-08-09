package com.sphere.jobsphere.Common.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Activities.CandidateHomeActivity;
import com.sphere.jobsphere.Candidate.Activities.CandidateProfileSetupActivity;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterHomeActivity;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileSetupActivity;

public class CommonLoginActivity extends AppCompatActivity {
    AppCompatButton acbLoginLogin, acbLoginSignUp;
    TextInputEditText tieLoginEmail, tieLoginPassword;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_login);
        editor = pref.edit();
        acbLoginLogin = findViewById(R.id.acbLoginLogin);
        acbLoginSignUp = findViewById(R.id.acbLoginSignUp);
        tieLoginEmail = findViewById(R.id.tieLoginEmail);
        tieLoginPassword = findViewById(R.id.tieLoginPassword);

        acbLoginSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, CommonRegisterActivity.class));
        });

        acbLoginLogin.setOnClickListener(v -> {

            String userEmail = tieLoginEmail.getText().toString();
            String userPassword = tieLoginPassword.getText().toString();

            if (!userEmail.isEmpty() && !userPassword.isEmpty()) {
                auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();

                        db.collection("users").document(user.getUid()).get().addOnSuccessListener(documentSnapshot -> {

                            String userRole = documentSnapshot.getString("role");

                            boolean isProfileCompleted = pref.getBoolean("isProfileSetupCompleted", false);

                            Toast.makeText(this, "Login SuccessFull", Toast.LENGTH_SHORT).show();

                            editor.putBoolean("isLoggedIn", true).apply();

                            // if profile is already setup navigate user based on role to their home pages
                            if (isProfileCompleted) {
                                if (userRole.equals("seeker")) {
                                    new Handler().postDelayed(() -> {
                                        startActivity(new Intent(this, CandidateHomeActivity.class));
                                        finish();
                                    }, 1000);
                                } else {
                                    new Handler().postDelayed(() -> {
                                        startActivity(new Intent(this, RecruiterHomeActivity.class));
                                        finish();
                                    }, 1000);
                                }
                            } else {
                                // if profile is not setUp then navigate user based on role to setup the profile
                                if (userRole.equals("seeker")) {
                                    new Handler().postDelayed(() -> {
                                        startActivity(new Intent(this, CandidateProfileSetupActivity.class));
                                        finish();
                                    }, 1000);
                                } else {
                                    new Handler().postDelayed(() -> {
                                        startActivity(new Intent(this, RecruiterProfileSetupActivity.class));
                                        finish();
                                    }, 1000);
                                }
                            }

//                            Toast.makeText(this, "Login SuccessFull", Toast.LENGTH_SHORT).show();
//                            if (documentSnapshot.getString("role").equals("seeker")) {
//                                new Handler().postDelayed(() -> {
//                                    startActivity(new Intent(this, CandidateProfileSetupActivity.class));
//                                    finish();
//                                }, 1000);
//                            } else {
//                                new Handler().postDelayed(() -> {
//                                    startActivity(new Intent(this, RecruiterProfileSetupActivity.class));
//                                    finish();
//                                }, 1000);
//                            }
                        });
                    } else {
                        Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

