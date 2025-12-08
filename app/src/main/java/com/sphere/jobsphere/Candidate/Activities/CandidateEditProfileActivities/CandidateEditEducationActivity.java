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

public class CandidateEditEducationActivity extends AppCompatActivity {
    EditText etCandidateEditEducationQualifications, etCandidateEditEducationBranch, etCandidateEditEducationUniversity, etCandidateEditEducationGraduationYear;
    ImageView ivCandidateEditProfileEducationSaveButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    CandidateProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_edit_education);

        currentUid = auth.getCurrentUser().getUid();

        etCandidateEditEducationQualifications = findViewById(R.id.etCandidateEditEducationQualifications);
        etCandidateEditEducationBranch = findViewById(R.id.etCandidateEditEducationBranch);
        etCandidateEditEducationUniversity = findViewById(R.id.etCandidateEditEducationUniversity);
        etCandidateEditEducationGraduationYear = findViewById(R.id.etCandidateEditEducationGraduationYear);
        ivCandidateEditProfileEducationSaveButton = findViewById(R.id.ivCandidateEditProfileEducationSaveButton);


        fetchEducation();

        ivCandidateEditProfileEducationSaveButton.setOnClickListener(v -> {
            profile.getEducation().get(0).setQualification(etCandidateEditEducationQualifications.getText().toString());
            profile.getEducation().get(0).setSpecialization(etCandidateEditEducationBranch.getText().toString());
            profile.getEducation().get(0).setUniversity(etCandidateEditEducationUniversity.getText().toString());
            profile.getEducation().get(0).setGraduationYear(Integer.parseInt(etCandidateEditEducationGraduationYear.getText().toString().trim()));

            db.collection("candidates")
                    .document(currentUid)
                    .set(profile);

            Intent intent = new Intent(this, CandidateEditProfileActivity.class);
            startActivity(intent);
            finish();

        });

    }

    private void fetchEducation() {
        db.collection("candidates")
                .document(currentUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    profile = documentSnapshot.toObject(CandidateProfile.class);
                    profile.setUid(documentSnapshot.getId());

                    etCandidateEditEducationQualifications.setText(profile.getEducation().get(0).getQualification());
                    etCandidateEditEducationBranch.setText(profile.getEducation().get(0).getSpecialization());
                    etCandidateEditEducationUniversity.setText(profile.getEducation().get(0).getUniversity());
                    etCandidateEditEducationGraduationYear.setText(profile.getEducation().get(0).getGraduationYear() + "");
                });
    }
}