package com.sphere.jobsphere.Candidate.Activities.CandidateProfileInfoActivities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Models.CandidateProfileSetupModels.CandidateProfile;
import com.sphere.jobsphere.R;

public class CandidatePersonalInfoActivity extends AppCompatActivity {

    TextView tvCandidatePersonalInfoFullName, tvCandidatePersonalInfoDateOfBirth, tvCandidatePersonalInfoEmail, tvCandidatePersonalInfoPhone, tvCandidatePersonalInfoLocation;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    CandidateProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_personal_info);

        currentUid = auth.getCurrentUser().getUid();

        tvCandidatePersonalInfoFullName = findViewById(R.id.tvCandidatePersonalInfoFullName);
        tvCandidatePersonalInfoDateOfBirth = findViewById(R.id.tvCandidatePersonalInfoDateOfBirth);
        tvCandidatePersonalInfoEmail = findViewById(R.id.tvCandidatePersonalInfoEmail);
        tvCandidatePersonalInfoPhone = findViewById(R.id.tvCandidatePersonalInfoPhone);
        tvCandidatePersonalInfoLocation = findViewById(R.id.tvCandidatePersonalInfoLocation);

        fetchPersonalInfo();

    }

    private void fetchPersonalInfo() {
        db.collection("candidates")
                .document(currentUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    profile = documentSnapshot.toObject(CandidateProfile.class);
                    profile.setUid(documentSnapshot.getId());

                    tvCandidatePersonalInfoFullName.setText(profile.getPersonalInfo().getFullName());
                    tvCandidatePersonalInfoDateOfBirth.setText(profile.getPersonalInfo().getDob());
                    tvCandidatePersonalInfoEmail.setText(profile.getPersonalInfo().getEmail());
                    tvCandidatePersonalInfoPhone.setText(profile.getPersonalInfo().getPhone());
                    tvCandidatePersonalInfoLocation.setText(profile.getPersonalInfo().getCurrentLocation());

                });

    }
}