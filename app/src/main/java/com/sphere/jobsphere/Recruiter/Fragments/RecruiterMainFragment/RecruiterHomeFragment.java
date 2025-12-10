package com.sphere.jobsphere.Recruiter.Fragments.RecruiterMainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Adapters.RecruiterApplicationsPageAdapter;
import com.sphere.jobsphere.Recruiter.Adapters.RecruiterHomeApplicantAdapter;
import com.sphere.jobsphere.Recruiter.Adapters.RecruiterHomeRecentChatsAdapter;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterApplicantsModel;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterApplicationsModel;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterRecentChatsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class RecruiterHomeFragment extends Fragment {

    List<RecruiterApplicationsModel> recentJobs = new ArrayList<>();
    List<RecruiterApplicantsModel> applicants = new ArrayList<>();
    List<RecruiterRecentChatsModel> recentChats = new ArrayList<>();
    RecyclerView PostedJobsRecyclerView, applicantsRecyclerView, recentChatsRecyclerView;

    LinearLayoutManager linearLayoutManager;
    RecruiterApplicationsPageAdapter recentJobAdapter;
    RecruiterHomeApplicantAdapter applicantAdapter;
    RecruiterHomeRecentChatsAdapter recentChatsAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    String currentUid;

    //Adapter_recyclerview adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruiter_home, container, false);
        currentUid = auth.getCurrentUser().getUid();

        PostedJobsRecyclerView = view.findViewById(R.id.rvRecruiterHomePostedJobs);
        applicantsRecyclerView = view.findViewById(R.id.rvRecruiterHomeApplicants);
        recentChatsRecyclerView = view.findViewById(R.id.rvHomeRecentChats);

        // For Posted Jobs
        loadPostedJobRecyclerView();
        loadPostedJobsData();

        //For Applicants
        loadApplicantsRecyclerView();
        loadApplicantsData();

        //For Recent Chats
        loadRecentChatData();
        loadRecentChatsRecyclerView();
        return view;
    }

    private void loadRecentChatData() {
        for (int i = 1; i <= 3; i++) {
            recentChats.add(new RecruiterRecentChatsModel(i % 2 == 1 ? "Shivam Thosar" : "Vikram Dingre",
                    "https://dummyimage.com/100x100/000/fff&text=" + i,
                    i % 2 == 0 ? "Ok" : "Bye",
                    i % 2 == 0 ? "12:55" : "4:35"));
        }
    }

    private void loadRecentChatsRecyclerView() {
        recentChatsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recentChatsAdapter = new RecruiterHomeRecentChatsAdapter(getActivity(), recentChats);
        recentChatsRecyclerView.setAdapter(recentChatsAdapter);
        recentChatsAdapter.notifyDataSetChanged();
    }

    private void loadApplicantsRecyclerView() {
        applicantsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        applicantAdapter = new RecruiterHomeApplicantAdapter(applicants, getActivity());
        applicantsRecyclerView.setAdapter(applicantAdapter);
    }

    private void loadApplicantsData() {
//        recruiterJobApplicants
        applicants.clear();

//        db.collection("recruiterJobApplicants")
//                .document("5ulAWvQue7lJlCVz0lqS")
//                .collection("jobApplicants")
//                .get()
//                .addOnSuccessListener(snapshots1 -> {
//                    for (DocumentSnapshot documentSnapshot : snapshots1.getDocuments()) {
//                        RecruiterApplicantsModel applicant = documentSnapshot.toObject(RecruiterApplicantsModel.class);
//                        applicant.id = documentSnapshot.getId();
//                        applicants.add(applicant);
//                    }
//                        applicantAdapter.notifyDataSetChanged();
//                });

        //88888888888888888888888888
//        db.collection("recruiterJobApplicants")
//                .get()
//                .addOnSuccessListener(snapshots -> {
////                    if (snapshots.isEmpty()) {
////                        return;
////                    }
//
//                    int size = snapshots.size();
//                    AtomicInteger loaded = new AtomicInteger(0);
//                    Toast.makeText(getActivity(), "" + snapshots.size(), Toast.LENGTH_SHORT).show();
//
//                    for (DocumentSnapshot doc : snapshots.getDocuments()) {
//
//                        db.collection("recruiterJobApplicants")
//                                .document(doc.getId())
//                                .collection("jobApplicants")
//                                .get()
//                                .addOnSuccessListener(snapshots1 -> {
//                                    for (DocumentSnapshot documentSnapshot : snapshots1.getDocuments()) {
//                                        RecruiterApplicantsModel applicant = documentSnapshot.toObject(RecruiterApplicantsModel.class);
//                                        applicant.id = documentSnapshot.getId();
//                                        applicants.add(applicant);
//                                    }
//                                    if (loaded.incrementAndGet() == size) {
//                                        applicantAdapter.notifyDataSetChanged();
//                                    }
//                                });
//
//                    }
//                });

        db.collection("jobs")
                .whereEqualTo("recruiterId", currentUid)
                .addSnapshotListener((snapshots, e) -> {
                    if (snapshots == null) {
                        return;
                    }
                    int size = snapshots.size();
                    AtomicInteger loaded = new AtomicInteger(0);

                    for (DocumentSnapshot doc : snapshots.getDocuments()) {

                        db.collection("recruiterJobApplicants")
                                .document(doc.getId())
                                .collection("jobApplicants")
                                .get()
                                .addOnSuccessListener(snapshots1 -> {
                                    for (DocumentSnapshot documentSnapshot : snapshots1.getDocuments()) {
                                        RecruiterApplicantsModel applicant = documentSnapshot.toObject(RecruiterApplicantsModel.class);
                                        applicant.id = documentSnapshot.getId();
                                        applicants.add(applicant);
                                    }
                                    if (loaded.incrementAndGet() == size) {
                                        applicantAdapter.notifyDataSetChanged();
                                    }

                                });

                    }
                });

    }

    private void loadPostedJobRecyclerView() {

        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        PostedJobsRecyclerView.setLayoutManager(linearLayoutManager);
        recentJobAdapter = new RecruiterApplicationsPageAdapter(recentJobs, getActivity());
        PostedJobsRecyclerView.setAdapter(recentJobAdapter);
    }

    private void loadPostedJobsData() {
        recentJobs.clear();
        db.collection("jobs")
                .whereEqualTo("recruiterId", currentUid)
                .limit(3)
                .addSnapshotListener((snapshots, e) -> {
                    if (snapshots == null) {
                        return;
                    }
                    int size = snapshots.size();
                    AtomicInteger loaded = new AtomicInteger(0);
                    for (DocumentSnapshot documentSnapshot : snapshots.getDocuments()) {
                        RecruiterApplicationsModel job = documentSnapshot.toObject(RecruiterApplicationsModel.class);
                        job.id = documentSnapshot.getId();
                        recentJobs.add(job);
                        if (loaded.incrementAndGet() == size) {
                            recentJobAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }
}