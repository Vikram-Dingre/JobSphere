package com.sphere.jobsphere.Candidate.Activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Models.CandidateApplicationModel;
import com.sphere.jobsphere.Candidate.Models.CandidateJobModel;
import com.sphere.jobsphere.R;

public class CandidateJobDetailsUploadResumeActivity extends AppCompatActivity {
    AppCompatButton acbCandidateJobDetailsUploadResumeApplyButton, acbCandidateJobDetailsUploadResumeSeeApplicationsButton, acbCandidateJobDetailsUploadResumeFindMoreJobsButton;
    ImageView ivCandidateJobDetailsUploadResumeCompanyLogo;
    TextView tvCandidateJobDetailsUploadResumeJobName, tvCandidateJobDetailsUploadResumeCompanyName, tvCandidateJobDetailsUploadResumeSalary, tvCandidateJobDetailsUploadResumeLocation;

    LinearLayout llCandidateJobDetailsUploadResumeTextViews, llCandidateJobDetailsUploadResumeSuccessMessage, llCandidateJobDetailsUploadResumeYourResume;

    CardView cvCandidateJobDetailsUploadResumeCardViewButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    CandidateApplicationModel application;

    String jobId, currentUid, resumeLink = ""; // this link will come from cloudinary , when user will click on that your resume ll then open new activity and pass this link into the intent and show that resume so find how to show an resume if its link is coming from cloudinary
    CandidateJobModel job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_job_details_upload_resume);

        currentUid = auth.getCurrentUser().getUid();


        acbCandidateJobDetailsUploadResumeApplyButton = findViewById(R.id.acbCandidateJobDetailsUploadResumeApplyButton);
        ivCandidateJobDetailsUploadResumeCompanyLogo = findViewById(R.id.ivCandidateJobDetailsUploadResumeCompanyLogo);
        tvCandidateJobDetailsUploadResumeJobName = findViewById(R.id.tvCandidateJobDetailsUploadResumeJobName);
        tvCandidateJobDetailsUploadResumeCompanyName = findViewById(R.id.tvCandidateJobDetailsUploadResumeCompanyName);
        tvCandidateJobDetailsUploadResumeSalary = findViewById(R.id.tvCandidateJobDetailsUploadResumeSalary);
        tvCandidateJobDetailsUploadResumeLocation = findViewById(R.id.tvCandidateJobDetailsUploadResumeLocation);
        llCandidateJobDetailsUploadResumeTextViews = findViewById(R.id.llCandidateJobDetailsUploadResumeTextViews);
        llCandidateJobDetailsUploadResumeSuccessMessage = findViewById(R.id.llCandidateJobDetailsUploadResumeSuccessMessage);
        cvCandidateJobDetailsUploadResumeCardViewButton = findViewById(R.id.cvCandidateJobDetailsUploadResumeCardViewButton);
        llCandidateJobDetailsUploadResumeYourResume = findViewById(R.id.llCandidateJobDetailsUploadResumeYourResume);
        acbCandidateJobDetailsUploadResumeSeeApplicationsButton = findViewById(R.id.acbCandidateJobDetailsUploadResumeSeeApplicationsButton);
        acbCandidateJobDetailsUploadResumeFindMoreJobsButton = findViewById(R.id.acbCandidateJobDetailsUploadResumeFindMoreJobsButton);


        jobId = getIntent().getStringExtra("jobId");

        fetchJobDetails();

        cvCandidateJobDetailsUploadResumeCardViewButton.setOnClickListener(v -> {
            makeText(this, "Uploading...", LENGTH_SHORT).show();

            new Handler().postDelayed(() -> {
                llCandidateJobDetailsUploadResumeYourResume.setVisibility(VISIBLE);
            }, 2000);

        });

        acbCandidateJobDetailsUploadResumeSeeApplicationsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CandidateHomeActivity.class);
            intent.putExtra("openFragment", "applications");
            startActivity(intent);
            finish(); // optional
        });

        acbCandidateJobDetailsUploadResumeFindMoreJobsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CandidateHomeActivity.class);
            intent.putExtra("openFragment", "jobs");
            startActivity(intent);
            finish(); // optional
        });

        acbCandidateJobDetailsUploadResumeApplyButton.setOnClickListener(v -> {

            createAndSaveApplicationToFirebase();
            updateJobApplicantsAndCount();

            llCandidateJobDetailsUploadResumeTextViews.setVisibility(GONE);
            cvCandidateJobDetailsUploadResumeCardViewButton.setVisibility(GONE);
            acbCandidateJobDetailsUploadResumeApplyButton.setVisibility(GONE);
            llCandidateJobDetailsUploadResumeYourResume.setVisibility(VISIBLE);
            llCandidateJobDetailsUploadResumeSuccessMessage.setVisibility(VISIBLE);


        });

    }

    private void fetchJobDetails() {
        db.collection("jobs")
                .document(jobId)
                .get()
                .addOnSuccessListener((doc) -> {
                    job = doc.toObject(CandidateJobModel.class);
                    job.id = doc.getId();

                    Glide.with(this).load(job.getCompanyLogo()).into(ivCandidateJobDetailsUploadResumeCompanyLogo);
                    tvCandidateJobDetailsUploadResumeJobName.setText(job.getTitle());
                    tvCandidateJobDetailsUploadResumeCompanyName.setText(job.getCompanyName());
                    tvCandidateJobDetailsUploadResumeSalary.setText(job.getSalary() + " LPA");
                    tvCandidateJobDetailsUploadResumeLocation.setText(job.getLocation());
                });
    }

    private void createAndSaveApplicationToFirebase() {

//        application.setJobId(jobId);
//        application.setCompanyLogo(job.getCompanyLogo());
//        application.setJobName(job.getTitle());
//        application.setCompanyName(job.getCompanyName());
//        application.setJobSalary(job.getSalary());
//        application.setJobType(job.getJobType());
//        application.setJobLocation(job.getLocation());
//        application.setRecruiterMessage("");
//        application.setApplicationStatus("On the Way");

        application = new CandidateApplicationModel("", jobId, job.getCompanyLogo(), job.getTitle(), job.getCompanyName(), job.getSalary(), job.getJobType(), job.getLocation(), "", "Pending", resumeLink);

        db.collection("CandidateApplications")
                .document(currentUid)
                .collection("applications")
                .add(application);

    }

    private void updateJobApplicantsAndCount() {
        db.collection("jobs")
                .document(jobId)
                .update(
                        "applicantsCount", FieldValue.increment(1),
                        "applicants", FieldValue.arrayUnion(currentUid)
                );
    }

}