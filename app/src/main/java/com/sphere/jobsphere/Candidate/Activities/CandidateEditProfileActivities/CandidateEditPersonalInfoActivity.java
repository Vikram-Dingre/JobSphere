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

public class CandidateEditPersonalInfoActivity extends AppCompatActivity {
    EditText etCandidateEditPersonalInfoFullName, etCandidateEditPersonalInfoEmail, etCandidateEditPersonalInfoPhone, etCandidateEditPersonalInfoLocation;
    ImageView ivCandidateEditProfilePersonalInfoSaveButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    CandidateProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_edit_personal_info);
        currentUid = auth.getCurrentUser().getUid();

        etCandidateEditPersonalInfoFullName = findViewById(R.id.etCandidateEditPersonalInfoFullName);
        etCandidateEditPersonalInfoEmail = findViewById(R.id.etCandidateEditPersonalInfoEmail);
        etCandidateEditPersonalInfoPhone = findViewById(R.id.etCandidateEditPersonalInfoPhone);
        etCandidateEditPersonalInfoLocation = findViewById(R.id.etCandidateEditPersonalInfoLocation);
        ivCandidateEditProfilePersonalInfoSaveButton = findViewById(R.id.ivCandidateEditProfilePersonalInfoSaveButton);


        fetchDefaultPersonalInfo();

        ivCandidateEditProfilePersonalInfoSaveButton.setOnClickListener(v -> {
            profile.getPersonalInfo().setFullName(etCandidateEditPersonalInfoFullName.getText().toString());
            profile.getPersonalInfo().setEmail(etCandidateEditPersonalInfoEmail.getText().toString());
            profile.getPersonalInfo().setPhone(etCandidateEditPersonalInfoPhone.getText().toString());
            profile.getPersonalInfo().setCurrentLocation(etCandidateEditPersonalInfoLocation.getText().toString());

            db.collection("candidates")
                    .document(currentUid)
                    .set(profile);

            Intent intent = new Intent(this, CandidateEditProfileActivity.class);
            startActivity(intent);
            finish();

        });


    }

    private void fetchDefaultPersonalInfo() {
        db.collection("candidates")
                .document(currentUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    profile = documentSnapshot.toObject(CandidateProfile.class);
                    profile.setUid(documentSnapshot.getId());

                    etCandidateEditPersonalInfoFullName.setText(profile.getPersonalInfo().getFullName());
                    etCandidateEditPersonalInfoEmail.setText(profile.getPersonalInfo().getEmail());
                    etCandidateEditPersonalInfoPhone.setText(profile.getPersonalInfo().getPhone());
                    etCandidateEditPersonalInfoLocation.setText(profile.getPersonalInfo().getCurrentLocation());
                });
    }
}