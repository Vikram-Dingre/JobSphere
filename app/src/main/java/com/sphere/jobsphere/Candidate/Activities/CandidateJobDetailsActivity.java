package com.sphere.jobsphere.Candidate.Activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Models.CandidateApplicationModel;
import com.sphere.jobsphere.Candidate.Models.CandidateJobModel;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Models.RecruiterProfile;

import java.util.HashMap;
import java.util.Map;

public class CandidateJobDetailsActivity extends AppCompatActivity {
    LinearLayout llRequirementsSkillsContainer;
    TabLayout candidateJobDetailsTabLayout;
    LinearLayout candidateJobDetailsJobDescriptionTabContent, candidateJobDetailsCompanyTabContent;
    String currentUid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    CandidateJobModel job;
    ImageView ivCandidateApplicationsCompanyLogo, ivCandidateJobDetailsSaveJob;
    TextView tvCandidateApplicationsJobName, tvCandidateApplicationsCompanyName, tvCandidateApplicationsAboutRole, tvCandidateApplicationsAboutCompany, tvCandidateApplicationsWebsite, tvCandidateApplicationsCity, tvCandidateApplicationsSize, tvCandidateApplicationsZipCode, tvCandidateApplicationsCountry;
    ChipGroup cgCandidateApplicationJobTypeChipGroup;
    AppCompatButton acbCandidateApplicationsApplyButton;
    CandidateApplicationModel application;
    boolean isAlreadyApplied = false;
    private String currentTab;
    private String jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_job_details);

        currentTab = "Job Description";

        currentUid = auth.getCurrentUser().getUid();

        llRequirementsSkillsContainer = findViewById(R.id.llRequirementsSkillsContainer);
        candidateJobDetailsTabLayout = findViewById(R.id.candidateJobDetailsTabLayout);
        candidateJobDetailsJobDescriptionTabContent = findViewById(R.id.candidateJobDetailsJobDescriptionTabContent);
        candidateJobDetailsCompanyTabContent = findViewById(R.id.candidateJobDetailsCompanyTabContent);
        ivCandidateApplicationsCompanyLogo = findViewById(R.id.ivCandidateApplicationsCompanyLogo);
        tvCandidateApplicationsJobName = findViewById(R.id.tvCandidateApplicationsJobName);
        tvCandidateApplicationsCompanyName = findViewById(R.id.tvCandidateApplicationsCompanyName);
        tvCandidateApplicationsAboutRole = findViewById(R.id.tvCandidateApplicationsAboutRole);
        cgCandidateApplicationJobTypeChipGroup = findViewById(R.id.cgCandidateApplicationJobTypeChipGroup);
        tvCandidateApplicationsAboutCompany = findViewById(R.id.tvCandidateApplicationsAboutCompany);
        tvCandidateApplicationsWebsite = findViewById(R.id.tvCandidateApplicationsWebsite);
        tvCandidateApplicationsCity = findViewById(R.id.tvCandidateApplicationsCity);
        tvCandidateApplicationsSize = findViewById(R.id.tvCandidateApplicationsSize);
        tvCandidateApplicationsZipCode = findViewById(R.id.tvCandidateApplicationsZipCode);
        tvCandidateApplicationsCountry = findViewById(R.id.tvCandidateApplicationsCountry);
        acbCandidateApplicationsApplyButton = findViewById(R.id.acbCandidateApplicationsApplyButton);
        ivCandidateJobDetailsSaveJob = findViewById(R.id.ivCandidateJobDetailsSaveJob);


        jobId = getIntent().getStringExtra("jobId");

        fetchJobDetails();
        tabsRelated();


        ivCandidateJobDetailsSaveJob.setOnClickListener(v -> {

            FirebaseFirestore.getInstance().collection("candidateSavedJobs")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("savedJobs")
                    .whereEqualTo("jobId", job.getId())
                    .get()
                    .addOnSuccessListener(snapshots -> {
                        if (snapshots.size() != 0) {
                            for (DocumentSnapshot doc : snapshots.getDocuments()) {
                                doc.getReference().delete().addOnSuccessListener(aVoid -> {
                                    Glide.with(this)
                                            .load(R.drawable.bookmark)
                                            .into(ivCandidateJobDetailsSaveJob);
                                    makeText(this, "Unsaved", LENGTH_SHORT).show();
                                });
                            }
                        } else {
                            Map<String, Object> savedJob = new HashMap<>();
                            savedJob.put("jobId", job.getId());
                            FirebaseFirestore.getInstance().collection("candidateSavedJobs")
                                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .collection("savedJobs")
                                    .add(savedJob)
                                    .addOnSuccessListener(aVoid -> {
                                        Glide.with(this)
                                                .load(R.drawable.savedjob)
                                                .into(ivCandidateJobDetailsSaveJob);
                                        makeText(this, "Saved", LENGTH_SHORT).show();
                                    });
                        }
                    });

//            FirebaseFirestore.getInstance().collection("candidateSavedJobs")
//                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                    .collection("savedJobs")
//                    .get()
//                    .addOnSuccessListener(snapshots -> {
//                        int a = 0;
//                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
//
//                            if (doc.getString("jobId").equals(job.getId())) {
//                                Glide.with(context)
//                                        .load(R.drawable.bookmark)
//                                        .into(holder.saveJob);
//
//                                FirebaseFirestore.getInstance().collection("candidateSavedJobs")
//                                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                        .collection("savedJobs")
//                                        .whereEqualTo("jobId",job.getId())
//                                        .get()
//                                        .addOnSuccessListener(query ->{
//                                            for (DocumentSnapshot saveJobDoc : query.getDocuments()){
//                                                saveJobDoc.getReference().delete().addOnSuccessListener(aVoid ->{
//                                                    makeText(context, "Unsaved Successfully.", LENGTH_SHORT).show();
//                                                });
//                                            }
//                                        });
//                                a = 1;
//                                break;
//                            }
//                        }
//                        if (a == 0) {
//                            Glide.with(context)
//                                    .load(R.drawable.savedjob)
//                                    .into(holder.saveJob);
//
//                            Map<String, Object> savedJob = new HashMap<>();
//                            savedJob.put("jobId", job.getId());
//                            FirebaseFirestore.getInstance().collection("candidateSavedJobs")
//                                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .collection("savedJobs")
//                                    .add(savedJob);
//                            makeText(context, "Saved Successfully.", LENGTH_SHORT).show();
//                        }
//                    });


        });

//        ivCandidateJobDetailsSaveJob.setOnClickListener(v -> {
//            Map<String, Object> savedJob = new HashMap<>();
//
//            savedJob.put("jobId", job.getId());
//
//            db.collection("candidateSavedJobs")
//                    .document(currentUid)
//                    .collection("savedJobs")
//                    .add(savedJob);
//            makeText(this, "Job Saved", LENGTH_SHORT).show();
//
//        });

        acbCandidateApplicationsApplyButton.setOnClickListener(v -> {

            if (job.getApplicants().contains(currentUid)) {

                db.collection("CandidateApplications")
                        .document(currentUid)
                        .collection("applications")
                        .get()
                        .addOnSuccessListener(snapshots -> {
                            for (DocumentSnapshot doc : snapshots.getDocuments()) {
                                if (doc.getString("jobId").equals(jobId)) {
                                    Intent intent = new Intent(this, CandidateApplicationDetailsActivity.class);
                                    intent.putExtra("applicationId", doc.getId());
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

////                Intent intent = new Intent(this, CandidateApplicationsFragment.class);
////                startActivity(intent);
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.flRecruiterProfileSetupFrameContainer, new CandidateApplicationsFragment())
//                        .commit();
////                finish();
////                Toast.makeText(this, "Already Applied to this job...", Toast.LENGTH_SHORT).show();
                return;
            }

            createAndSaveApplicationToFirebase();

            updateJobApplicantsAndCount();

            makeText(this, "Job Applied Successfully.", LENGTH_SHORT).show();

        });
    }

    private void updateJobApplicantsAndCount() {
        db.collection("jobs")
                .document(jobId)
                .update(
                        "applicantsCount", FieldValue.increment(1),
                        "applicants", FieldValue.arrayUnion(currentUid)
                );
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

        application = new CandidateApplicationModel("", jobId, job.getCompanyLogo(), job.getTitle(), job.getCompanyName(), job.getSalary(), job.getJobType(), job.getLocation(), "", "Pending");

        db.collection("CandidateApplications")
                .document(currentUid)
                .collection("applications")
                .add(application);

    }

    private void fetchJobDetails() {

        db.collection("jobs")
                .document(jobId)
                .get()
                .addOnSuccessListener((doc) -> {
                    job = doc.toObject(CandidateJobModel.class);
                    job.id = doc.getId();

                    Glide.with(this).load(job.getCompanyLogo()).into(ivCandidateApplicationsCompanyLogo);
                    tvCandidateApplicationsJobName.setText(job.getTitle());
                    tvCandidateApplicationsCompanyName.setText(job.getCompanyName());
                    tvCandidateApplicationsAboutRole.setText(job.getDescription());

                    for (String type : job.getJobTypes()) {
                        Chip chip = new Chip(this);
                        chip.setText(type);

// Remove default chip background tint + material overlay
                        chip.setChipBackgroundColor(ColorStateList.valueOf(Color.TRANSPARENT));
                        chip.setBackground(null); // removes white layer

// Remove ripple by disabling interaction
                        chip.setClickable(false);
                        chip.setFocusable(false);

// Text color
                        chip.setTextColor(ContextCompat.getColor(this, R.color.white));

// Radius
                        chip.setChipCornerRadius(20f);

// Not checkable
                        chip.setCheckable(false);

                        cgCandidateApplicationJobTypeChipGroup.addView(chip);

                    }

                    addDummySkills();

                    if (job.getApplicants().contains(currentUid)) {
                        acbCandidateApplicationsApplyButton.setText("View Application");
//                        acbCandidateApplicationsApplyButton.setBackgroundColor(ContextCompat.getColor(this,R.color.dadada));
//                        acbCandidateApplicationsApplyButton.setTextColor(ContextCompat.getColor(this,R.color.black));
                    }

                    db.collection("candidateSavedJobs")
                            .document(currentUid)
                            .collection("savedJobs")
                            .whereEqualTo("jobId", job.getId())
                            .get()
                            .addOnSuccessListener(snapshots -> {
                                if (snapshots.size() != 0) {
                                    Glide.with(this)
                                            .load(R.drawable.savedjob)
                                            .into(ivCandidateJobDetailsSaveJob);
                                } else {
                                    Glide.with(this)
                                            .load(R.drawable.bookmark)
                                            .into(ivCandidateJobDetailsSaveJob);
                                }
                            });

                    fetchCompanyDetails();

                });
    }

//    private void fetchCompanyDetails() {

//        db.collection("recruiters")
//                .document(job.getRecruiterId())
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {

    /// /                    Toast.makeText(this, "companyLocation " +documentSnapshot, Toast.LENGTH_SHORT).show();
//                    RecruiterProfile rp = documentSnapshot.toObject(RecruiterProfile.class);
//
//                    rp.setUid(documentSnapshot.getId());
//                    tvCandidateApplicationsAboutCompany.setText(rp.getCompanyLocation().getAbout());
//                    tvCandidateApplicationsWebsite.setText(rp.getCompanyDetails().getWebsite());
//                    tvCandidateApplicationsCity.setText(rp.getCompanyLocation().getCity());
//                    tvCandidateApplicationsSize.setText(rp.getCompanyDetails().getSize());
//                    tvCandidateApplicationsZipCode.setText(rp.getCompanyLocation().getZipCode());
//                    tvCandidateApplicationsCountry.setText(rp.getCompanyLocation().getCountry());
//
//                });
//    }
    private void fetchCompanyDetails() {

        db.collection("recruiters")
                .document(job.getRecruiterId())
                .get()
                .addOnSuccessListener(documentSnapshot -> {

                    if (!documentSnapshot.exists()) {
                        Log.e("FETCH", "Recruiter doc does not exist");
                        return;
                    }

                    RecruiterProfile rp = documentSnapshot.toObject(RecruiterProfile.class);

                    if (rp == null) {
                        makeText(this, "Null pointer Exception", LENGTH_SHORT).show();
                        Log.e("FETCH", "RecruiterProfile is null (mapping error)");
                        return;
                    }

                    rp.setUid(documentSnapshot.getId());

                    // SAFE GETTERS – avoid NPE
                    if (rp.getCompanyLocation() != null) {
                        tvCandidateApplicationsAboutCompany.setText(rp.getCompanyLocation().getAbout());
                        tvCandidateApplicationsCity.setText(rp.getCompanyLocation().getCity());
                        tvCandidateApplicationsZipCode.setText(rp.getCompanyLocation().getZipCode());
                        tvCandidateApplicationsCountry.setText(rp.getCompanyLocation().getCountry());
                    }

                    if (rp.getCompanyDetails() != null) {
                        tvCandidateApplicationsWebsite.setText(rp.getCompanyDetails().getWebsite());
                        tvCandidateApplicationsSize.setText(rp.getCompanyDetails().getSize());
                    }
                });
    }


    private void addDummySkills() {
//        List<String> requirements = Arrays.asList(
//                "Strong Java knowledge",
//                "Experience with Android Studio",
//                "Knowledge of Firebase",
//                "Good communication skills",
//                "Having Hands On Experience",
//                "Effective Decision Making",
//                "Good Problem Solving SKills"
//        );

        // Add each requirement as a bullet point
        for (String req : job.getSkillsList()) {
            TextView tv = new TextView(this);
            tv.setText("✅   " + req);
            tv.setTextSize(14);
            tv.setBackgroundResource(R.drawable.candidate_job_details_skills_shape);
            tv.setPadding(30, 30, 30, 30);

// Create LayoutParams for margins
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

// Set margin (left, top, right, bottom)
            params.setMargins(0, 8, 0, 8);

// Apply LayoutParams to TextView
            tv.setLayoutParams(params);

// Add to container
            llRequirementsSkillsContainer.addView(tv);

        }

    }

    private void tabsRelated() {
        // Tabs
        candidateJobDetailsTabLayout.addTab(candidateJobDetailsTabLayout.newTab().setText("Job Description"));
        candidateJobDetailsTabLayout.addTab(candidateJobDetailsTabLayout.newTab().setText("Company"));

        if (currentTab.equals("Job Description")) {
            candidateJobDetailsTabLayout.getTabAt(0).select();
        } else if (currentTab.equals("Company")) {
            candidateJobDetailsTabLayout.getTabAt(1).select();
        }

        candidateJobDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                currentTab = tab.getText().toString();

                candidateJobDetailsJobDescriptionTabContent.setVisibility(GONE);
                candidateJobDetailsCompanyTabContent.setVisibility(GONE);

                if (currentTab.equalsIgnoreCase("Job Description")) {
                    candidateJobDetailsJobDescriptionTabContent.setVisibility(VISIBLE);
                    candidateJobDetailsCompanyTabContent.setVisibility(GONE);
                } else if (currentTab.equalsIgnoreCase("Company")) {
                    candidateJobDetailsJobDescriptionTabContent.setVisibility(GONE);
                    candidateJobDetailsCompanyTabContent.setVisibility(VISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}