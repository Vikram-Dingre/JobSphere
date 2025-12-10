package com.sphere.jobsphere.Recruiter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileInfoActivities.RecruiterProfileUpdateActivities.RecruiterUpdateComapnyLocationActivity;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileInfoActivities.RecruiterProfileUpdateActivities.RecruiterUpdateCompanyDetailActivity;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileInfoActivities.RecruiterProfileUpdateActivities.RecruiterUpdatePersonalInfoActivity;

public class RecruiterProfileUpdateActivity extends AppCompatActivity {
    ImageView ivUpdatePersonalInfo, ivUpdateCompanyDetail, ivUpdateCompanyLocation, ivRecruiterUpdateProfileSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_profile_update);
        ivUpdatePersonalInfo = findViewById(R.id.ivRecruiterProfileUpdatePersonalInformationUpdate);
        ivUpdateCompanyDetail = findViewById(R.id.ivRecruiterProfileUpdateCompanyDetailsUpdate);
        ivUpdateCompanyLocation = findViewById(R.id.ivRecruiterProfileUpdateCompanyLocationUpdate);
        ivRecruiterUpdateProfileSave = findViewById(R.id.ivRecruiterUpdateProfileSave);

        ivUpdatePersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecruiterProfileUpdateActivity.this, RecruiterUpdatePersonalInfoActivity.class);
                startActivity(i);
            }
        });

        ivUpdateCompanyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecruiterProfileUpdateActivity.this, RecruiterUpdateCompanyDetailActivity.class);
                startActivity(i);
            }
        });

        ivRecruiterUpdateProfileSave.setOnClickListener(v -> {

            // update the profile image url here using profile object and then run next query

//            db.collection("candidates")
//                    .document(currentUid)
//                    .set(profile);

            Intent intent = new Intent(this, RecruiterMainActivity.class);
            intent.putExtra("openFragment", "profile");
            startActivity(intent);

        });

        ivUpdateCompanyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecruiterProfileUpdateActivity.this, RecruiterUpdateComapnyLocationActivity.class);
                startActivity(i);
            }
        });
    }
}