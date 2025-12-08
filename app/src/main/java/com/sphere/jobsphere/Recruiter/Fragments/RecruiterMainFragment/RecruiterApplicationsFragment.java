package com.sphere.jobsphere.Recruiter.Fragments.RecruiterMainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterCreateNewJobActivity;
import com.sphere.jobsphere.Recruiter.Adapters.RecruiterApplicationsPageAdapter;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterApplicationsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class RecruiterApplicationsFragment extends Fragment {
    RecyclerView applicationsRecyclerView;
    List<RecruiterApplicationsModel> applications = new ArrayList<>();

    RecruiterApplicationsPageAdapter adapter;
    ImageView ivAddNewJob;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    String currentUid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recruiter_applications, container, false);
        applicationsRecyclerView = view.findViewById(R.id.rvRecruiterAFApplications);

        currentUid = auth.getCurrentUser().getUid();

        ivAddNewJob = view.findViewById(R.id.ivRecruiterApplicationCreateJob);

        applicationsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        adapter = new RecruiterApplicationsPageAdapter(applications, getActivity());
        applicationsRecyclerView.setAdapter(adapter);

        ivAddNewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RecruiterCreateNewJobActivity.class);
                startActivity(i);
            }
        });

        loadApplicationsData();

        return view;
    }

    private void loadApplicationsData() {
        applications.clear();
        db.collection("jobs")
                .whereEqualTo("recruiterId", currentUid)
                .addSnapshotListener((snapshots, e) -> {
                    if (snapshots == null) {
                        return;
                    }
                    int size = snapshots.size();
                    AtomicInteger loaded = new AtomicInteger(0);
                    for (DocumentSnapshot documentSnapshot : snapshots.getDocuments()) {
                        RecruiterApplicationsModel job = documentSnapshot.toObject(RecruiterApplicationsModel.class);
                        job.id = documentSnapshot.getId();
                        applications.add(job);
                        if (loaded.incrementAndGet() == size) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}