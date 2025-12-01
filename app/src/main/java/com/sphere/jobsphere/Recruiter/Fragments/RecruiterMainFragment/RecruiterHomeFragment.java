package com.sphere.jobsphere.Recruiter.Fragments.RecruiterMainFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Adapters.RecruiterHomeRecentJobAdapter;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterJobModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RecruiterHomeFragment extends Fragment {

    List<RecruiterJobModel> recentJobs=new ArrayList<>();
    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;
    RecruiterHomeRecentJobAdapter adapter;
    //Adapter_recyclerview adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recruiter_home, container, false);
        recyclerView=view.findViewById(R.id.rvRecruiterHomeRecentJobs);
        loadData();
        loadRecyclerView();
        return view;
    }

    private void loadRecyclerView() {

        linearLayoutManager=new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new RecruiterHomeRecentJobAdapter(recentJobs,getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadData() {

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