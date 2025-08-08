package com.sphere.jobsphere.Candidate.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sphere.jobsphere.Candidate.Classes.CandidateProfileSetupData;
import com.sphere.jobsphere.Candidate.Fragments.CandidateProfileSetupStep1Fragment;
import com.sphere.jobsphere.R;

public class CandidateProfileSetupActivity extends AppCompatActivity {

    public CandidateProfileSetupData candidateProfileSetupData = new CandidateProfileSetupData();
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile_setup);


        getSupportFragmentManager().beginTransaction().replace(R.id.flCandidateProfileSetupFrameContainer, new CandidateProfileSetupStep1Fragment()).commit();

    }
}
//ProfileSetupActivity (Single Activity)
//    ├── Step1PersonalInfoFragment
//    ├── Step2EducationFragment
//    ├── Step3ExperienceFragment
//    ├── Step4SkillsFragment
//    ├── Step5ResumeUploadFragment


//        pref = getSharedPreferences("settings", MODE_PRIVATE);
//       editor = pref.edit();
//
//        btn = findViewById(R.id.btn);
//
//        btn.setOnClickListener(v -> {
//            editor.putBoolean("isProfileSetupCompleted",true).apply();
//            Toast.makeText(this, "Profile Setup Completed Successfully.", Toast.LENGTH_SHORT).show();
//        });
