package com.sphere.jobsphere.Candidate.Activities.CandidateEditProfileActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Models.CandidateProfileSetupModels.CandidateProfile;
import com.sphere.jobsphere.R;

public class CandidateEditWorkExperienceActivity extends AppCompatActivity {
    EditText etCandidateEditWorkExperienceJobTitle, etCandidateEditWorkExperienceCurrentCompany, etCandidateEditWorkExperienceExperience, etCandidateEditWorkExperienceExpectedSalary;
    ImageView ivCandidateEditProfileWorkExperienceSaveButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    CandidateProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_edit_work_experience);

        currentUid = auth.getCurrentUser().getUid();

        etCandidateEditWorkExperienceJobTitle = findViewById(R.id.etCandidateEditWorkExperienceJobTitle);
        etCandidateEditWorkExperienceCurrentCompany = findViewById(R.id.etCandidateEditWorkExperienceCurrentCompany);
        etCandidateEditWorkExperienceExperience = findViewById(R.id.etCandidateEditWorkExperienceExperience);
        etCandidateEditWorkExperienceExpectedSalary = findViewById(R.id.etCandidateEditWorkExperienceExpectedSalary);
        ivCandidateEditProfileWorkExperienceSaveButton = findViewById(R.id.ivCandidateEditProfileWorkExperienceSaveButton);

        fetchDefaultWorkExperience();

        ivCandidateEditProfileWorkExperienceSaveButton.setOnClickListener(v -> {
            profile.getProfessionalDetails().setJobTitle(etCandidateEditWorkExperienceJobTitle.getText().toString());
            profile.getProfessionalDetails().setCurrentCompany(etCandidateEditWorkExperienceCurrentCompany.getText().toString());
            profile.getProfessionalDetails().setExperience(etCandidateEditWorkExperienceExperience.getText().toString());
            profile.getProfessionalDetails().setExpectedSalary(Double.parseDouble(etCandidateEditWorkExperienceExpectedSalary.getText().toString().trim()));

            db.collection("candidates")
                    .document(currentUid)
                    .set(profile);

            Intent intent = new Intent(this, CandidateEditProfileActivity.class);
            startActivity(intent);
            finish();

        });

    }

    private void fetchDefaultWorkExperience() {
        db.collection("candidates")
                .document(currentUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    profile = documentSnapshot.toObject(CandidateProfile.class);
                    profile.setUid(documentSnapshot.getId());

                    etCandidateEditWorkExperienceJobTitle.setText(profile.getProfessionalDetails().getJobTitle());
                    etCandidateEditWorkExperienceCurrentCompany.setText(profile.getProfessionalDetails().getCurrentCompany());
                    etCandidateEditWorkExperienceExperience.setText(profile.getProfessionalDetails().getExperience());
                    etCandidateEditWorkExperienceExpectedSalary.setText(profile.getProfessionalDetails().getExpectedSalary() + "");
                });
    }
}