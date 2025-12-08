package com.sphere.jobsphere.Candidate.Activities.CandidateProfileInfoActivities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Models.CandidateProfileSetupModels.CandidateProfile;
import com.sphere.jobsphere.R;

public class CandidateWorkExperienceActivity extends AppCompatActivity {

    TextView tvCandidateWorkExperienceJobTitle, tvCandidateWorkExperienceCurrentCompany, tvCandidateWorkExperienceExperience, tvCandidateWorkExperienceExpectedSalary;
    ChipGroup cgCandidateWorkExperienceSkills;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    CandidateProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_work_experience);

        currentUid = auth.getCurrentUser().getUid();

        tvCandidateWorkExperienceJobTitle = findViewById(R.id.tvCandidateWorkExperienceJobTitle);
        tvCandidateWorkExperienceCurrentCompany = findViewById(R.id.tvCandidateWorkExperienceCurrentCompany);
        tvCandidateWorkExperienceExperience = findViewById(R.id.tvCandidateWorkExperienceExperience);
        tvCandidateWorkExperienceExpectedSalary = findViewById(R.id.tvCandidateWorkExperienceExpectedSalary);
        cgCandidateWorkExperienceSkills = findViewById(R.id.cgCandidateWorkExperienceSkills);

        fetchWorkExperience();

    }

    private void fetchWorkExperience() {
        db.collection("candidates")
                .document(currentUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    profile = documentSnapshot.toObject(CandidateProfile.class);
                    profile.setUid(documentSnapshot.getId());

                    tvCandidateWorkExperienceJobTitle.setText(profile.getProfessionalDetails().getJobTitle());
                    tvCandidateWorkExperienceCurrentCompany.setText(profile.getProfessionalDetails().getCurrentCompany());
                    tvCandidateWorkExperienceExperience.setText(profile.getProfessionalDetails().getExperience());
                    tvCandidateWorkExperienceExpectedSalary.setText(profile.getProfessionalDetails().getExpectedSalary() + "");


                    cgCandidateWorkExperienceSkills.removeAllViews(); // Clear previous chips if any

                    for (String skill : profile.getProfessionalDetails().getSkills()) {
                        skill = skill.trim(); // remove extra spaces

                        Chip chip = new Chip(this);
                        chip.setText(skill);

                        // Style the chip
                        chip.setChipBackgroundColorResource(R.color.lightPurple);
                        chip.setTextColor(ContextCompat.getColor(this, R.color.black));
                        chip.setClickable(false);
                        chip.setCheckable(false);

                        cgCandidateWorkExperienceSkills.addView(chip);
                    }

                });

    }
}