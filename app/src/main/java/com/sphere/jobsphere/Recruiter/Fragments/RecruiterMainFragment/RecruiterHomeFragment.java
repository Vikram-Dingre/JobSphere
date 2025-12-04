package com.sphere.jobsphere.Recruiter.Fragments.RecruiterMainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Adapters.RecruiterHomeApplicantAdapter;
import com.sphere.jobsphere.Recruiter.Adapters.RecruiterHomeRecentChatsAdapter;
import com.sphere.jobsphere.Recruiter.Adapters.RecruiterHomeRecentJobAdapter;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterApplicantsModel;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterJobModel;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterRecentChatsModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RecruiterHomeFragment extends Fragment {

    List<RecruiterJobModel> recentJobs = new ArrayList<>();
    List<RecruiterApplicantsModel> applicants = new ArrayList<>();
    List<RecruiterRecentChatsModel> recentChats = new ArrayList<>();
    RecyclerView PostedJobsRecyclerView, applicantsRecyclerView, recentChatsRecyclerView;

    LinearLayoutManager linearLayoutManager;
    RecruiterHomeRecentJobAdapter recentJobAdapter;
    RecruiterHomeApplicantAdapter applicantAdapter;
    RecruiterHomeRecentChatsAdapter recentChatsAdapter;
    //Adapter_recyclerview adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruiter_home, container, false);
        PostedJobsRecyclerView = view.findViewById(R.id.rvRecruiterHomePostedJobs);
        applicantsRecyclerView = view.findViewById(R.id.rvRecruiterHomeApplicants);
        recentChatsRecyclerView = view.findViewById(R.id.rvHomeRecentChats);

        // For Posted Jobs
        loadPostedJobsData();
        loadPostedJobRecyclerView();

        //For Applicants
        loadApplicantsData();
        loadApplicantsRecyclerView();

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
        applicantAdapter.notifyDataSetChanged();
    }

    private void loadApplicantsData() {
        for (int i = 1; i <= 5; i++) {
            applicants.add(new RecruiterApplicantsModel("https://dummyimage.com/100x100/000/fff&text=" + i,
                    i % 2 == 1 ? "Shivam Thosar" : "Vikram Dingre",
                    i % 2 == 1 ? "Frontend Devloper" : "Backend Devloper"));
        }
    }

    private void loadPostedJobRecyclerView() {

        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        PostedJobsRecyclerView.setLayoutManager(linearLayoutManager);
        recentJobAdapter = new RecruiterHomeRecentJobAdapter(recentJobs, getActivity());
        PostedJobsRecyclerView.setAdapter(recentJobAdapter);
        recentJobAdapter.notifyDataSetChanged();
    }

    private void loadPostedJobsData() {

        for (int i = 1; i <= 30; i++) {

            recentJobs.add(new RecruiterJobModel("job" + i, // id
                    "Software Engineer " + i, // title
                    "We are looking for a passionate Software Engineer to join our dynamic team and work on exciting projects " + i, // description
                    "Company " + i, // companyName
                    "https://dummyimage.com/100x100/000/fff&text=" + i, // companyLogo
                    i % 2 == 0 ? "Bangalore" : "Mumbai", // location
                    i % 2 == 0 ? "Full-Time" : "Part-Time", // jobType
                    Arrays.asList("Remote", "Hybrid"), // jobTypes
                    i % 3 == 0 ? "IT" : "Finance", // category
                    "Java, Spring Boot, SQL", // skillsRequired
                    Arrays.asList("Java", "Spring Boot", "SQL", "Firebase"), // skillsList
                    "₹" + (5 + i) + " LPA", // salary
                    5000.0 + i, // minSalary
                    10000.0 + i, // maxSalary
                    i % 2 == 0 ? "Mid Level" : "Entry Level", // experienceLevel
                    "B.Tech / M.Tech", // education
                    System.currentTimeMillis() - (i * 86400000L), // postedAt (i days ago)
                    System.currentTimeMillis() + (30 * 86400000L), // deadline (30 days from now)
                    "recruiter" + i, // recruiterId
                    "Recruiter " + i, // recruiterName
                    "recruiter" + i + "@company.com", // recruiterEmail
                    i * 3, // applicantsCount
                    Arrays.asList("cand1", "cand2", "cand3"), // applicants
                    Math.random() * 100 // matchScore
            ));
        }

    }
}