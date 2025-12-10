package com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileInfoActivities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Models.RecruiterProfile;

public class RecruiterCompanyLocationInfoActivity extends AppCompatActivity {
    TextView tvRecruiterCompanyLocationCity, tvRecruiterCompanyLocationState, tvRecruiterCompanyLocationCountry, tvRecruiterCompanyLocationZipcode, tvRecruiterCompanyLocationCompanyAddress, tvRecruiterCompanyLocationAboutYourCompany;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    RecruiterProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_company_location_info);


        currentUid = auth.getCurrentUser().getUid();

        tvRecruiterCompanyLocationCity = findViewById(R.id.tvRecruiterCompanyLocationCity);
        tvRecruiterCompanyLocationState = findViewById(R.id.tvRecruiterCompanyLocationState);
        tvRecruiterCompanyLocationCountry = findViewById(R.id.tvRecruiterCompanyLocationCountry);
        tvRecruiterCompanyLocationZipcode = findViewById(R.id.tvRecruiterCompanyLocationZipcode);
        tvRecruiterCompanyLocationCompanyAddress = findViewById(R.id.tvRecruiterCompanyLocationCompanyAddress);
        tvRecruiterCompanyLocationAboutYourCompany = findViewById(R.id.tvRecruiterCompanyLocationAboutYourCompany);

        fetcCompanylocation();
    }

    private void fetcCompanylocation() {
        db.collection("recruiters")
                .document(currentUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    profile = documentSnapshot.toObject(RecruiterProfile.class);
                    profile.setUid(documentSnapshot.getId());

                    tvRecruiterCompanyLocationCity.setText(profile.getCompanyLocation().getCity());
                    tvRecruiterCompanyLocationState.setText(profile.getCompanyLocation().getState());
                    tvRecruiterCompanyLocationCountry.setText(profile.getCompanyLocation().getCountry());
                    tvRecruiterCompanyLocationZipcode.setText(profile.getCompanyLocation().getZipCode());
                    tvRecruiterCompanyLocationCompanyAddress.setText(profile.getCompanyLocation().getAddress());
                    tvRecruiterCompanyLocationAboutYourCompany.setText(profile.getCompanyLocation().getAbout());
                });

    }
}