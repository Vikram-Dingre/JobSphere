package com.sphere.jobsphere.Candidate.Fragments.CandidateHomeFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Activities.CandidateSavedJobsActivity;
import com.sphere.jobsphere.R;

public class CandidateProfileFragment extends Fragment {
    AppCompatButton acbCandidateProfileSavedJobs;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidate_profile, container, false);

        currentUid = auth.getCurrentUser().getUid();

        acbCandidateProfileSavedJobs = view.findViewById(R.id.acbCandidateProfileSavedJobs);

        calculateBoxCounts();

        acbCandidateProfileSavedJobs.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CandidateSavedJobsActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void calculateBoxCounts() {

        db.collection("candidateSavedJobs")
                .document(currentUid)
                .collection("savedJobs")
                .addSnapshotListener((snapshots, error) -> {

                    if (error != null || snapshots == null) return;

                    long count = snapshots.size();  // realtime count

                    acbCandidateProfileSavedJobs.setText(String.valueOf(count));
                });


        //        db.collection("candidateSavedJobs")
//                .document(currentUid)
//                .collection("savedJobs")
//                .count()
//                .get(AggregateSource.SERVER)
//                .addOnSuccessListener(snapshot -> {
//                    long count = snapshot.getCount();
//                    acbCandidateProfileSavedJobs.setText(count + "");
//                })
//                .addOnFailureListener(e -> Log.e("COUNT", "Error:", e));
    }
}