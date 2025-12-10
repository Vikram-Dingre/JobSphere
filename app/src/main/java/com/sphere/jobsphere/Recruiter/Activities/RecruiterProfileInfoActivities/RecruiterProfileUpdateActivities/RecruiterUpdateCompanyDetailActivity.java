package com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileInfoActivities.RecruiterProfileUpdateActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileUpdateActivity;
import com.sphere.jobsphere.Recruiter.Models.RecruiterProfile;

public class RecruiterUpdateCompanyDetailActivity extends AppCompatActivity {
    TextInputEditText tieRecruiterUpdateCompanyDetailsCompanyName, tieRecruiterUpdateCompanyDetailsIndustry, tieRecruiterUpdateCompanyDetailsSize, tieRecruiterUpdateCompanyDetailsWebsite;
    ImageView ivRecruiterUpdateCompanyDetailSave;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    RecruiterProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_update_company_detail);
        currentUid = auth.getCurrentUser().getUid();

        tieRecruiterUpdateCompanyDetailsCompanyName = findViewById(R.id.tieRecruiterUpdateCompanyDetailsCompanyName);
        tieRecruiterUpdateCompanyDetailsIndustry = findViewById(R.id.tieRecruiterUpdateCompanyDetailsIndustry);
        tieRecruiterUpdateCompanyDetailsSize = findViewById(R.id.tieRecruiterUpdateCompanyDetailsSize);
        tieRecruiterUpdateCompanyDetailsWebsite = findViewById(R.id.tieRecruiterUpdateCompanyDetailsWebsite);
        ivRecruiterUpdateCompanyDetailSave = findViewById(R.id.ivRecruiterUpdateCompanyDetailSave);

        fetchDefaultPersonalInfo();

        ivRecruiterUpdateCompanyDetailSave.setOnClickListener(v -> {
            profile.getCompanyDetails().setCompanyName(tieRecruiterUpdateCompanyDetailsCompanyName.getText().toString());
            profile.getCompanyDetails().setIndustry(tieRecruiterUpdateCompanyDetailsIndustry.getText().toString());
            profile.getCompanyDetails().setSize(tieRecruiterUpdateCompanyDetailsSize.getText().toString());
            profile.getCompanyDetails().setWebsite(tieRecruiterUpdateCompanyDetailsWebsite.getText().toString());

            db.collection("recruiters")
                    .document(currentUid)
                    .set(profile);

            Intent intent = new Intent(this, RecruiterProfileUpdateActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void fetchDefaultPersonalInfo() {
        db.collection("recruiters")
                .document(currentUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    profile = documentSnapshot.toObject(RecruiterProfile.class);
                    if (profile == null) {
                        return;
                    }
                    profile.setUid(documentSnapshot.getId());

                    tieRecruiterUpdateCompanyDetailsCompanyName.setText(profile.getCompanyDetails().getCompanyName());
                    tieRecruiterUpdateCompanyDetailsIndustry.setText(profile.getCompanyDetails().getIndustry());
                    tieRecruiterUpdateCompanyDetailsSize.setText(profile.getCompanyDetails().getSize());
                    tieRecruiterUpdateCompanyDetailsWebsite.setText(profile.getCompanyDetails().getWebsite());
                });
    }
}