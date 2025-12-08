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
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterMainActivity;

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

        pref = getSharedPreferences("settings", MODE_PRIVATE);
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
                                        Intent intent = new Intent(this, CandidateHomeActivity.class);
                                        startActivity(intent);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                Intent.FLAG_ACTIVITY_NEW_TASK |
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        finish();
                                    }, 1000);
                                } else {
                                    new Handler().postDelayed(() -> {
                                        Intent intent = new Intent(this, RecruiterMainActivity.class);
                                        startActivity(intent);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                Intent.FLAG_ACTIVITY_NEW_TASK |
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        finish();
                                    }, 1000);
                                }
                            } else {
                                Intent intent = new Intent(this, CommonProfileSetupIntroActivity.class);
                                intent.putExtra("userRole", userRole);
                                startActivity(intent);
                                finish();
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

