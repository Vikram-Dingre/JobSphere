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

public class RecruiterUpdatePersonalInfoActivity extends AppCompatActivity {
    TextInputEditText tieRecruiterUpdatePersonalInfoFullName, tieRecruiterUpdatePersonalInfoJobTitle, tieRecruiterUpdatePersonalInfoEmailAddress, tieRecruiterUpdatePersonalInfoPhoneNumber;
    ImageView ivRecruiterUpdatePersonalInfoSave;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    RecruiterProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_update_personal_info);

        currentUid = auth.getCurrentUser().getUid();

        tieRecruiterUpdatePersonalInfoFullName = findViewById(R.id.tieRecruiterUpdatePersonalInfoFullName);
        tieRecruiterUpdatePersonalInfoJobTitle = findViewById(R.id.tieRecruiterUpdatePersonalInfoJobTitle);
        tieRecruiterUpdatePersonalInfoEmailAddress = findViewById(R.id.tieRecruiterUpdatePersonalInfoEmailAddress);
        tieRecruiterUpdatePersonalInfoPhoneNumber = findViewById(R.id.tieRecruiterUpdatePersonalInfoPhoneNumber);
        ivRecruiterUpdatePersonalInfoSave = findViewById(R.id.ivRecruiterUpdatePersonalInfoSave);

        fetchDefaultPersonalInfo();

        ivRecruiterUpdatePersonalInfoSave.setOnClickListener(v -> {
            profile.getPersonalInfo().setFullName(tieRecruiterUpdatePersonalInfoFullName.getText().toString());
            profile.getPersonalInfo().setWorkEmail(tieRecruiterUpdatePersonalInfoEmailAddress.getText().toString());
            profile.getPersonalInfo().setWorkPhone(tieRecruiterUpdatePersonalInfoPhoneNumber.getText().toString());
            profile.getPersonalInfo().setJobTitle(tieRecruiterUpdatePersonalInfoJobTitle.getText().toString());

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

                    tieRecruiterUpdatePersonalInfoFullName.setText(profile.getPersonalInfo().getFullName());
                    tieRecruiterUpdatePersonalInfoJobTitle.setText(profile.getPersonalInfo().getJobTitle());
                    tieRecruiterUpdatePersonalInfoEmailAddress.setText(profile.getPersonalInfo().getWorkEmail());
                    tieRecruiterUpdatePersonalInfoPhoneNumber.setText(profile.getPersonalInfo().getWorkPhone());
                });
    }
}