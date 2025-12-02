package com.sphere.jobsphere.Candidate.Activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Models.CandidateApplicationModel;
import com.sphere.jobsphere.R;

public class CandidateApplicationDetailsActivity extends AppCompatActivity {
    ImageView ivCandidateApplicationsDetailsCompanyLogo;
    TextView tvCandidateApplicationDetailsJobName, tvCandidateApplicationDetailsCompanyName, tvCandidateApplicationDetailsSalary, tvCandidateApplicationDetailsType, tvCandidateApplicationDetailsLocation, tvCandidateApplicationDetailsRecruiterMessage;
    AppCompatButton acbCandidateApplicationsApplicationStatus, acbCandidateApplicationDetailsButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    CandidateApplicationModel application;

    String applicationId, currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_application_details);

        ivCandidateApplicationsDetailsCompanyLogo = findViewById(R.id.ivCandidateApplicationsDetailsCompanyLogo);
        tvCandidateApplicationDetailsJobName = findViewById(R.id.tvCandidateApplicationDetailsJobName);
        tvCandidateApplicationDetailsCompanyName = findViewById(R.id.tvCandidateApplicationDetailsCompanyName);
        tvCandidateApplicationDetailsSalary = findViewById(R.id.tvCandidateApplicationDetailsSalary);
        tvCandidateApplicationDetailsType = findViewById(R.id.tvCandidateApplicationDetailsType);
        tvCandidateApplicationDetailsLocation = findViewById(R.id.tvCandidateApplicationDetailsLocation);
        tvCandidateApplicationDetailsRecruiterMessage = findViewById(R.id.tvCandidateApplicationDetailsRecruiterMessage);
        acbCandidateApplicationsApplicationStatus = findViewById(R.id.acbCandidateApplicationsApplicationStatus);
        acbCandidateApplicationDetailsButton = findViewById(R.id.acbCandidateApplicationDetailsButton);

        currentUid = auth.getCurrentUser().getUid();
        applicationId = getIntent().getStringExtra("applicationId");


        fetchDetails();

    }

    private void fetchDetails() {
        db.collection("CandidateApplications")
                .document(currentUid)
                .collection("applications")
                .document(applicationId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    application = documentSnapshot.toObject(CandidateApplicationModel.class);
                    application.id = documentSnapshot.getId();

                    Glide.with(this).load(application.getCompanyLogo()).into(ivCandidateApplicationsDetailsCompanyLogo);
                    tvCandidateApplicationDetailsJobName.setText(application.getJobName());
                    tvCandidateApplicationDetailsCompanyName.setText(application.getCompanyName());
                    acbCandidateApplicationsApplicationStatus.setText(application.getApplicationStatus());
                    tvCandidateApplicationDetailsSalary.setText(application.getJobSalary());
                    tvCandidateApplicationDetailsType.setText(application.getJobType());
                    tvCandidateApplicationDetailsLocation.setText(application.getJobLocation());
                    tvCandidateApplicationDetailsRecruiterMessage.setText(application.getRecruiterMessage().isEmpty() ? "Waiting for review..." : application.getRecruiterMessage());

                    if (application.getApplicationStatus().equalsIgnoreCase("on the way")) {

                        acbCandidateApplicationsApplicationStatus.setBackgroundTintList(
                                ColorStateList.valueOf(Color.parseColor("#7943A047")) // Blue
                        );
                        acbCandidateApplicationsApplicationStatus.setTextColor(this.getColor(R.color.color_on_the_way_application));

                    } else if (application.getApplicationStatus().equalsIgnoreCase("delivered")) {
                        acbCandidateApplicationsApplicationStatus.setBackgroundTintList(
                                ColorStateList.valueOf(Color.parseColor("#37000000")) // Blue
                        );
                        acbCandidateApplicationsApplicationStatus.setTextColor(this.getColor(R.color.color_delivered_application));
                    } else if (application.getApplicationStatus().equalsIgnoreCase("rejected")) {
                        acbCandidateApplicationsApplicationStatus.setBackgroundTintList(
                                ColorStateList.valueOf(Color.parseColor("#34FF0000")) // Blue
                        );
                        acbCandidateApplicationsApplicationStatus.setTextColor(this.getColor(R.color.color_rejected_application));
                    }

                });
    }
}