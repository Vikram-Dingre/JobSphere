package com.sphere.jobsphere.Candidate.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Classes.CandidateProfileSetupData;
import com.sphere.jobsphere.Candidate.Fragments.CandidateProfileSetupFragments.CandidateStep1PersonalInfoFragment;
import com.sphere.jobsphere.Candidate.Models.CandidateProfile;
import com.sphere.jobsphere.R;

public class CandidateProfileSetupActivity extends AppCompatActivity {

    public CandidateProfileSetupData candidateProfileSetupData = new CandidateProfileSetupData();
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public CandidateProfile candidateProfile = new CandidateProfile();
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile_setup);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Load Step 1 initially
        loadFragment(new CandidateStep1PersonalInfoFragment());
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.flCandidateProfileSetupFrameContainer, fragment)
                .addToBackStack(null)
                .commit();
    }


    public void saveProfileToFirestore() {
        candidateProfile.setUid(userId);
        FirebaseFirestore.getInstance()
                .collection("candidates")
                .document(userId)
                .set(candidateProfile)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Profile Completed!!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
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


// date picker ✅
// skip ✅
// validations ✅
// enable one time profile setup
// photo
