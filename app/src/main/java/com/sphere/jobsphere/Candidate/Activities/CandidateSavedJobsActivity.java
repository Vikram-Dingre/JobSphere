package com.sphere.jobsphere.Candidate.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Adapters.CandidateSavedJobsAdapter;
import com.sphere.jobsphere.Candidate.Models.CandidateJobModel;
import com.sphere.jobsphere.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateSavedJobsActivity extends AppCompatActivity {
    RecyclerView rvCandidateSavedJobsRecycler;
    CandidateSavedJobsAdapter savedJobsAdapter;
    List<CandidateJobModel> savedJobs = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_saved_jobs);
        currentUid = auth.getCurrentUser().getUid();

        rvCandidateSavedJobsRecycler = findViewById(R.id.rvCandidateSavedJobsRecycler);
        rvCandidateSavedJobsRecycler.setLayoutManager(new LinearLayoutManager(this));

        savedJobsAdapter = new CandidateSavedJobsAdapter(this, savedJobs);
        rvCandidateSavedJobsRecycler.setAdapter(savedJobsAdapter);

        fetchSavedJobs();
    }

    private void fetchSavedJobs() {
        savedJobs.clear();
        db.collection("candidateSavedJobs")
                .document(currentUid)
                .collection("savedJobs")
                .get()
                .addOnSuccessListener(snapshots -> {

                    int total = snapshots.size();
                    AtomicInteger loaded = new AtomicInteger(0);

                    for (DocumentSnapshot savedJobId : snapshots.getDocuments()) {

                        db.collection("jobs")
                                .document(savedJobId.getString("jobId"))
                                .get()
                                .addOnSuccessListener(documentSnapshot -> {

                                    CandidateJobModel job = documentSnapshot.toObject(CandidateJobModel.class);
                                    job.id = documentSnapshot.getId();
                                    savedJobs.add(job);

                                    // When all jobs loaded → update UI
                                    if (loaded.incrementAndGet() == total) {
                                        savedJobsAdapter.notifyDataSetChanged();
                                    }
                                });
                    }
                });
    }
}