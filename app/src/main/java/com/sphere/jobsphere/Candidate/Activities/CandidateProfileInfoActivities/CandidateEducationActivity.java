package com.sphere.jobsphere.Candidate.Activities.CandidateProfileInfoActivities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Models.CandidateProfileSetupModels.CandidateProfile;
import com.sphere.jobsphere.R;

public class CandidateEducationActivity extends AppCompatActivity {
    TextView tvCandidateEducationQualifications, tvCandidateEducationBranch, tvCandidateEducationUniversity, tvCandidateEducationGraduationYear;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    CandidateProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_education);

        currentUid = auth.getCurrentUser().getUid();

        tvCandidateEducationQualifications = findViewById(R.id.tvCandidateEducationQualifications);
        tvCandidateEducationBranch = findViewById(R.id.tvCandidateEducationBranch);
        tvCandidateEducationUniversity = findViewById(R.id.tvCandidateEducationUniversity);
        tvCandidateEducationGraduationYear = findViewById(R.id.tvCandidateEducationGraduationYear);

        fetchEducation();

    }

    private void fetchEducation() {
        db.collection("candidates")
                .document(currentUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    profile = documentSnapshot.toObject(CandidateProfile.class);
                    profile.setUid(documentSnapshot.getId());

                    tvCandidateEducationQualifications.setText(profile.getEducation().get(0).getQualification());
                    tvCandidateEducationBranch.setText(profile.getEducation().get(0).getSpecialization());
                    tvCandidateEducationUniversity.setText(profile.getEducation().get(0).getUniversity());
                    tvCandidateEducationGraduationYear.setText(profile.getEducation().get(0).getGraduationYear() + "");

                });

    }
}