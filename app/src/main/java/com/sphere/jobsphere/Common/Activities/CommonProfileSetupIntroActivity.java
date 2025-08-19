package com.sphere.jobsphere.Common.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.sphere.jobsphere.Candidate.Activities.CandidateProfileSetupActivity;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileSetupActivity;

public class CommonProfileSetupIntroActivity extends AppCompatActivity {
    AppCompatButton acbProfileSetupIntroGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_profile_setup_intro);

        String userRole = getIntent().getStringExtra("userRole");

        acbProfileSetupIntroGetStarted = findViewById(R.id.acbProfileSetupIntroGetStarted);

        acbProfileSetupIntroGetStarted.setOnClickListener(v -> {
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

        });

    }
}