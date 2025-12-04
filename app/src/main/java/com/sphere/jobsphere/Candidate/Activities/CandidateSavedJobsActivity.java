package com.sphere.jobsphere.Candidate.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

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
    List<CandidateJobModel> filteredSavedJobs = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    EditText etCandidateSavedJobsSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_saved_jobs);
        currentUid = auth.getCurrentUser().getUid();

        rvCandidateSavedJobsRecycler = findViewById(R.id.rvCandidateSavedJobsRecycler);
        etCandidateSavedJobsSearch = findViewById(R.id.etCandidateSavedJobsSearch);

        rvCandidateSavedJobsRecycler.setLayoutManager(new LinearLayoutManager(this));

        savedJobsAdapter = new CandidateSavedJobsAdapter(this, filteredSavedJobs);
        rvCandidateSavedJobsRecycler.setAdapter(savedJobsAdapter);

        fetchSavedJobs();

        etCandidateSavedJobsSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void filterList(String text) {
        filteredSavedJobs.clear();
        if (text == null || text.trim().isEmpty()) {
            filteredSavedJobs.addAll(savedJobs);
        } else {
            String query = text.toLowerCase();
            for (CandidateJobModel job : savedJobs) {
                if (job.getTitle().toLowerCase().contains(query) ||
                        job.getCompanyName().toLowerCase().contains(query) ||
                        job.getLocation().toLowerCase().contains(query)) {

                    filteredSavedJobs.add(job);
                }
            }
        }
        savedJobsAdapter.notifyDataSetChanged();
    }

    private void fetchSavedJobs() {
//        savedJobs.clear();
//        filteredSavedJobs.clear();
//        db.collection("candidateSavedJobs")
//                .document(currentUid)
//                .collection("savedJobs")
//                .get()
//                .addOnSuccessListener(snapshots -> {
//
//                    int total = snapshots.size();
//                    AtomicInteger loaded = new AtomicInteger(0);
//
//                    for (DocumentSnapshot savedJobId : snapshots.getDocuments()) {
//
//                        db.collection("jobs")
//                                .document(savedJobId.getString("jobId"))
//                                .get()
//                                .addOnSuccessListener(documentSnapshot -> {
//
//                                    CandidateJobModel job = documentSnapshot.toObject(CandidateJobModel.class);
//                                    job.id = documentSnapshot.getId();
//                                    savedJobs.add(job);
//
//                                    // When all jobs loaded → update UI
//                                    if (loaded.incrementAndGet() == total) {
//                                        savedJobsAdapter.notifyDataSetChanged();
//                                    }
//                                });
//                    }
//                });

        // this query useful for realtime event like when unsave it will remove

        db.collection("candidateSavedJobs")
                .document(currentUid)
                .collection("savedJobs")
                .addSnapshotListener((snapshots, error) -> {

                    if (error != null || snapshots == null) return;

                    int total = snapshots.size();
                    AtomicInteger loaded = new AtomicInteger(0);

                    savedJobs.clear();
                    filteredSavedJobs.clear();

                    for (DocumentSnapshot savedJobId : snapshots.getDocuments()) {

                        db.collection("jobs")
                                .document(savedJobId.getString("jobId"))
                                .get()
                                .addOnSuccessListener(documentSnapshot -> {

                                    CandidateJobModel job = documentSnapshot.toObject(CandidateJobModel.class);
                                    job.id = documentSnapshot.getId();
                                    savedJobs.add(job);
                                    filteredSavedJobs.add(job);

                                    // When all jobs loaded → update UI
                                    if (loaded.incrementAndGet() == total) {
                                        savedJobsAdapter.notifyDataSetChanged();
                                    }
                                });
                    }
                });

    }
}