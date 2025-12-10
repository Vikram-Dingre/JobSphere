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

public class RecruiterUpdateComapnyLocationActivity extends AppCompatActivity {
    TextInputEditText tieRecruiterUpdateCompanyLocationCity, tieRecruiterUpdateCompanyLocationState, tieRecruiterUpdateCompanyLocationCountry, tieRecruiterUpdateCompanyLocationZipCode, tieRecruiterUpdateCompanyLocationCompanyAddress, tieRecruiterUpdateCompanyLocationAboutYourCompany;
    ImageView ivRecruiterUpdateCompanyLocationSave;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    RecruiterProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_update_comapny_location);
        currentUid = auth.getCurrentUser().getUid();

        tieRecruiterUpdateCompanyLocationCity = findViewById(R.id.tieRecruiterUpdateCompanyLocationCity);
        tieRecruiterUpdateCompanyLocationState = findViewById(R.id.tieRecruiterUpdateCompanyLocationState);
        tieRecruiterUpdateCompanyLocationCountry = findViewById(R.id.tieRecruiterUpdateCompanyLocationCountry);
        tieRecruiterUpdateCompanyLocationZipCode = findViewById(R.id.tieRecruiterUpdateCompanyLocationZipCode);
        tieRecruiterUpdateCompanyLocationCompanyAddress = findViewById(R.id.tieRecruiterUpdateCompanyLocationCompanyAddress);
        tieRecruiterUpdateCompanyLocationAboutYourCompany = findViewById(R.id.tieRecruiterUpdateCompanyLocationAboutYourCompany);
        ivRecruiterUpdateCompanyLocationSave = findViewById(R.id.ivRecruiterUpdateCompanyLocationSave);

        fetchDefaultPersonalInfo();

        ivRecruiterUpdateCompanyLocationSave.setOnClickListener(v -> {
            profile.getCompanyLocation().setCity(tieRecruiterUpdateCompanyLocationCity.getText().toString());
            profile.getCompanyLocation().setState(tieRecruiterUpdateCompanyLocationState.getText().toString());
            profile.getCompanyLocation().setCountry(tieRecruiterUpdateCompanyLocationCountry.getText().toString());
            profile.getCompanyLocation().setZipCode(tieRecruiterUpdateCompanyLocationZipCode.getText().toString());
            profile.getCompanyLocation().setAddress(tieRecruiterUpdateCompanyLocationCompanyAddress.getText().toString());
            profile.getCompanyLocation().setAbout(tieRecruiterUpdateCompanyLocationAboutYourCompany.getText().toString());

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

                    tieRecruiterUpdateCompanyLocationCity.setText(profile.getCompanyLocation().getCity());
                    tieRecruiterUpdateCompanyLocationState.setText(profile.getCompanyLocation().getState());
                    tieRecruiterUpdateCompanyLocationCountry.setText(profile.getCompanyLocation().getCountry());
                    tieRecruiterUpdateCompanyLocationZipCode.setText(profile.getCompanyLocation().getZipCode());
                    tieRecruiterUpdateCompanyLocationCompanyAddress.setText(profile.getCompanyLocation().getAddress());
                    tieRecruiterUpdateCompanyLocationAboutYourCompany.setText(profile.getCompanyLocation().getAbout());
                });
    }
}