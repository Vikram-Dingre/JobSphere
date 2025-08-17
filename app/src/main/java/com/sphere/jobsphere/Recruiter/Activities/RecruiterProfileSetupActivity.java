package com.sphere.jobsphere.Recruiter.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Fragments.RecruiterProfileStep1PersonalInfoFragment;
import com.sphere.jobsphere.Recruiter.Models.RecruiterProfile;

public class RecruiterProfileSetupActivity extends AppCompatActivity {

   public RecruiterProfile recruiterProfile;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_profile_setup);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recruiterProfile = new RecruiterProfile();

        loadFragment(new RecruiterProfileStep1PersonalInfoFragment());

    }
    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.flRecruiterProfileSetupFrameContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void saveProfileToFirestore() {

        recruiterProfile.setUid(userId);

        FirebaseFirestore.getInstance()
                .collection("recruiters")
                .document(userId)
                .set(recruiterProfile)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Profile Completed!!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}

