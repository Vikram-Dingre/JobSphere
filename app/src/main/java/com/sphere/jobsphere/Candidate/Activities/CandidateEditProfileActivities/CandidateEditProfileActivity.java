package com.sphere.jobsphere.Candidate.Activities.CandidateEditProfileActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Activities.CandidateHomeActivity;
import com.sphere.jobsphere.Candidate.Models.CandidateProfileSetupModels.CandidateProfile;
import com.sphere.jobsphere.R;

public class CandidateEditProfileActivity extends AppCompatActivity {
ImageView ivCandidateEditProfileSaveButton,ivCandidateEditProfileImage,ivCandidateEditProfileEditIcon,ivCandidateEditProfilePersonalInfo,ivCandidateEditProfileWorkExperience,ivCandidateEditProfileEducations;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    CandidateProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_edit_profile);

        currentUid = auth.getCurrentUser().getUid();

        ivCandidateEditProfileSaveButton = findViewById(R.id.ivCandidateEditProfileSaveButton);
        ivCandidateEditProfileImage = findViewById(R.id.ivCandidateEditProfileImage);
        ivCandidateEditProfileEditIcon = findViewById(R.id.ivCandidateEditProfileEditIcon);
        ivCandidateEditProfilePersonalInfo = findViewById(R.id.ivCandidateEditProfilePersonalInfo);
        ivCandidateEditProfileWorkExperience = findViewById(R.id.ivCandidateEditProfileWorkExperience);
        ivCandidateEditProfileEducations = findViewById(R.id.ivCandidateEditProfileEducations);

        fetchProfile();

        ivCandidateEditProfilePersonalInfo.setOnClickListener(v -> {
            Intent intent = new Intent(this, CandidateEditPersonalInfoActivity.class);
            startActivity(intent);
            finish();
        });

        ivCandidateEditProfileWorkExperience.setOnClickListener(v -> {
            Intent intent = new Intent(this, CandidateEditWorkExperienceActivity.class);
            startActivity(intent);
            finish();
        });

        ivCandidateEditProfileEducations.setOnClickListener(v -> {
            Intent intent = new Intent(this, CandidateEditEducationActivity.class);
            startActivity(intent);
            finish();
        });

        ivCandidateEditProfileSaveButton.setOnClickListener(v -> {

            // update the profile image url here using profile object and then run next query

//            db.collection("candidates")
//                    .document(currentUid)
//                    .set(profile);

            Intent intent = new Intent(this, CandidateHomeActivity.class);
            intent.putExtra("openFragment","profile");
            startActivity(intent);

        });


    }

    private void fetchProfile() {
        db.collection("candidates")
                .document(currentUid)
                .addSnapshotListener((documentSnapshot,e) -> {
                    profile = documentSnapshot.toObject(CandidateProfile.class);
                    profile.setUid(documentSnapshot.getId());
                });
    }
}