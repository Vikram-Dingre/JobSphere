package com.sphere.jobsphere.Recruiter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Activities.CandidateJobDetailsActivity;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Adapters.RecruiterHomeApplicantAdapter;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterApplicantsModel;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterApplicationsModel;

import java.util.ArrayList;
import java.util.List;

public class RecruiterApplicationActivity extends AppCompatActivity {
    List<RecruiterApplicantsModel> applicants = new ArrayList<>();
    RecyclerView applicantsRecyclerView;

    LinearLayoutManager linearLayoutManager;
    RecruiterHomeApplicantAdapter applicantAdapter;
    CardView cvApplication;

    ImageView ivRecruiterApplicationActivityCompanyLogo;
    TextView tvRecruiterApplicationActivityJobTitle, tvRecruiterApplicationActivityCompanyName, tvRecruiterApplicationActivityJobLocation, tvRecruiterApplicationActivitySalary;

    String jobId;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    String currentUid;

    RecruiterApplicationsModel job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_application);

        currentUid = auth.getCurrentUser().getUid();

        applicantsRecyclerView = findViewById(R.id.rvRecruiterApplicationActivityApplicants);
        cvApplication = findViewById(R.id.cvApplication);
        ivRecruiterApplicationActivityCompanyLogo = findViewById(R.id.ivRecruiterApplicationActivityCompanyLogo);
        tvRecruiterApplicationActivityJobTitle = findViewById(R.id.tvRecruiterApplicationActivityJobTitle);
        tvRecruiterApplicationActivityCompanyName = findViewById(R.id.tvRecruiterApplicationActivityCompanyName);
        tvRecruiterApplicationActivityJobLocation = findViewById(R.id.tvRecruiterApplicationActivityJobLocation);
        tvRecruiterApplicationActivitySalary = findViewById(R.id.tvRecruiterApplicationActivitySalary);


        jobId = getIntent().getStringExtra("jobId");
        loadApplicantsRecyclerView();

        fetchJobDetails();

        loadApplicantsData();

        cvApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecruiterApplicationActivity.this, CandidateJobDetailsActivity.class);
                i.putExtra("jobId", jobId);
                startActivity(i);
            }
        });
    }

    private void fetchJobDetails() {
        db.collection("jobs")
                .document(jobId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    job = documentSnapshot.toObject(RecruiterApplicationsModel.class);
                    job.id = documentSnapshot.getId();

                    Glide.with(this)
                            .load(job.getCompanyLogo())
                            .into(ivRecruiterApplicationActivityCompanyLogo);

                    tvRecruiterApplicationActivityJobTitle.setText(job.getTitle());
                    tvRecruiterApplicationActivityCompanyName.setText(job.getCompanyName());
                    tvRecruiterApplicationActivityJobLocation.setText(job.getLocation());
                    tvRecruiterApplicationActivitySalary.setText(job.getSalary());

                });
    }

    private void loadApplicantsRecyclerView() {
        applicantsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        applicantAdapter = new RecruiterHomeApplicantAdapter(applicants, this);
        applicantsRecyclerView.setAdapter(applicantAdapter);

    }

    private void loadApplicantsData() {
        applicants.clear();
        db.collection("recruiterJobApplicants")
                .document(jobId)
                .collection("jobApplicants")
                .get()
                .addOnSuccessListener(snapshots -> {
                    for (DocumentSnapshot documentSnapshot : snapshots.getDocuments()) {
                        RecruiterApplicantsModel applicant = documentSnapshot.toObject(RecruiterApplicantsModel.class);
                        applicant.id = documentSnapshot.getId();
                        applicants.add(applicant);
                    }
                    applicantAdapter.notifyDataSetChanged();
                });

//        db.collection("jobs")
//                .document(jobId)
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    RecruiterApplicationsModel job = documentSnapshot.toObject(RecruiterApplicationsModel.class);
//                    job.id = documentSnapshot.getId();
//
//                    int size = job.getApplicantsCount();
//                    AtomicInteger loaded = new AtomicInteger(0);
//
//                    if (size == 0) {
//                        return;
//                    }
//                    for (String id : job.getApplicants()) {
//                        db.collection("candidates")
//                                .document(id)
//                                .get()
//                                .addOnSuccessListener(cand -> {
//                                    CandidateProfile c = cand.toObject(CandidateProfile.class);
//                                    c.setUid(id);
//
//                                    RecruiterApplicantsModel applicant = new RecruiterApplicantsModel();
//                                    applicant.setApplicantId(id);
//                                    applicant.setApplicantProfilePhoto(c.getPersonalInfo().getProfilePhotoUrl());
//                                    applicant.setApplicantName(c.getPersonalInfo().getFullName());
//                                    applicant.setApplicantEmail(c.getPersonalInfo().getEmail());
//
//                                    db.collection("CandidateApplications")
//                                            .document(id)
//                                            .collection("applications")
//                                            .whereEqualTo("jobId", jobId)
//                                            .get()
//                                            .addOnSuccessListener(applicationCand -> {
//                                                for (DocumentSnapshot candApplication : applicationCand) {
////                                                                                           Toast.makeText(this, ""+candApplication.getString("resume"), Toast.LENGTH_SHORT).show();
//                                                    applicant.setApplicantResume(candApplication.getString("resume"));
//                                                }
//
//                                                applicants.add(applicant);
//
//                                                if (loaded.incrementAndGet() == size) {
//                                                    applicantAdapter.notifyDataSetChanged();
//                                                }
//
//                                            });
//                                });
//                    }
//
//
//                });
    }
}