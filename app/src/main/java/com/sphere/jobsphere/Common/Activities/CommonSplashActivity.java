package com.sphere.jobsphere.Common.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Activities.CandidateHomeActivity;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterHomeActivity;

public class CommonSplashActivity extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_splash);

        pref = getSharedPreferences("settings", MODE_PRIVATE);
        editor = pref.edit();

        new Handler().postDelayed(() -> {

            boolean isLoggedIn = pref.getBoolean("isLoggedIn", false);
            boolean isProfileSetupCompleted = pref.getBoolean("isProfileSetupCompleted", false);

            if (isLoggedIn) {
                db.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(documentSnapshot -> {
                    String userRole = documentSnapshot.getString("role");
                    if (isProfileSetupCompleted) {
                        if (userRole.equals("seeker")) {
                            startActivity(new Intent(this, CandidateHomeActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(this, RecruiterHomeActivity.class));
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(this, CommonProfileSetupIntroActivity.class);
                        intent.putExtra("userRole", userRole);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                startActivity(new Intent(this, CommonLoginActivity.class));
                finish();
            }
        }, 2000);
    }
}
