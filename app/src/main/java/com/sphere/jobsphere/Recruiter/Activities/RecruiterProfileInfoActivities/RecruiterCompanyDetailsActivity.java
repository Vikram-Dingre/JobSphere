package com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileInfoActivities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Models.RecruiterProfile;

public class RecruiterCompanyDetailsActivity extends AppCompatActivity {
    TextView tvRecruiterCompanyDetailsCompanyName, tvRecruiterCompanyDetailsIndustry, tvRecruiterCompanyDetailsSize, tvRecruiterCompanyDetailsWebsite;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    RecruiterProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_company_details);

        currentUid = auth.getCurrentUser().getUid();

        tvRecruiterCompanyDetailsCompanyName = findViewById(R.id.tvRecruiterCompanyDetailsCompanyName);
        tvRecruiterCompanyDetailsIndustry = findViewById(R.id.tvRecruiterCompanyDetailsIndustry);
        tvRecruiterCompanyDetailsSize = findViewById(R.id.tvRecruiterCompanyDetailsSize);
        tvRecruiterCompanyDetailsWebsite = findViewById(R.id.tvRecruiterCompanyDetailsWebsite);

        fetchCompanyDetails();

    }

    private void fetchCompanyDetails() {
        db.collection("recruiters")
                .document(currentUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    profile = documentSnapshot.toObject(RecruiterProfile.class);
                    profile.setUid(documentSnapshot.getId());

                    tvRecruiterCompanyDetailsCompanyName.setText(profile.getCompanyDetails().getCompanyName());
                    tvRecruiterCompanyDetailsIndustry.setText(profile.getCompanyDetails().getIndustry());
                    tvRecruiterCompanyDetailsSize.setText(profile.getCompanyDetails().getSize());
                    tvRecruiterCompanyDetailsWebsite.setText(profile.getCompanyDetails().getWebsite());
                });

    }
}