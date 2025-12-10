package com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileInfoActivities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Models.RecruiterProfile;

public class RecruiterPersonalInfoActivity extends AppCompatActivity {
    TextView tvRecruiterPersonalInfoFullName, tvRecruiterPersonalInfJobTitle, tvRecruiterPersonalInfoEmail, tvRecruiterPersonalInfoPhone;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    RecruiterProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_personal_info);

        currentUid = auth.getCurrentUser().getUid();
        tvRecruiterPersonalInfoFullName = findViewById(R.id.tvRecruiterPersonalInfoFullName);
        tvRecruiterPersonalInfJobTitle = findViewById(R.id.tvRecruiterPersonalInfJobTitle);
        tvRecruiterPersonalInfoEmail = findViewById(R.id.tvRecruiterPersonalInfoEmail);
        tvRecruiterPersonalInfoPhone = findViewById(R.id.tvRecruiterPersonalInfoPhone);

        fetchPersonalInfo();
    }

    private void fetchPersonalInfo() {
        db.collection("recruiters")
                .document(currentUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    profile = documentSnapshot.toObject(RecruiterProfile.class);
                    profile.setUid(documentSnapshot.getId());

                    tvRecruiterPersonalInfoFullName.setText(profile.getPersonalInfo().getFullName());
                    tvRecruiterPersonalInfJobTitle.setText(profile.getPersonalInfo().getJobTitle());
                    tvRecruiterPersonalInfoEmail.setText(profile.getPersonalInfo().getWorkEmail());
                    tvRecruiterPersonalInfoPhone.setText(profile.getPersonalInfo().getWorkPhone());
                });

    }
}