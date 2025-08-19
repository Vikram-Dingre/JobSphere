package com.sphere.jobsphere.Candidate.Fragments.CandidateHomeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.sphere.jobsphere.Candidate.Adapters.CandidateJobsPageAdapter;
import com.sphere.jobsphere.Candidate.Models.CandidateJobModel;
import com.sphere.jobsphere.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CandidateJobsFragment extends Fragment {

    private TabLayout tabLayout;
    private AutoCompleteTextView customSpinner;
    private RecyclerView jobsRecycler;
    private TextView tvCandidateJobsAllResultCount;

    private String currentTab = "All";
    private String currentSort = "Date";

    List<CandidateJobModel> jobs = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidate_jobs, container, false);

        tabLayout = view.findViewById(R.id.jobsTabLayout);
        customSpinner = view.findViewById(R.id.sortDropDown);
        jobsRecycler = view.findViewById(R.id.rvCandidateJobsReycler);
        tvCandidateJobsAllResultCount = view.findViewById(R.id.tvCandidateJobsAllResultCount);

        jobsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        for (int i = 1; i <= 20; i++) {
            jobs.add(new CandidateJobModel(
                    "job" + i, // id
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
                    .5000 + i, // minSalary
                    .10000 + i, // maxSalary
                    i % 2 == 0 ? "Mid" : "Entry", // experienceLevel
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

        tvCandidateJobsAllResultCount.setText(jobs.size() + "");

        CandidateJobsPageAdapter jobsPageAdapter = new CandidateJobsPageAdapter(jobs, getActivity());
        jobsRecycler.setAdapter(jobsPageAdapter);


        // Tabs
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("Suggested"));
        tabLayout.addTab(tabLayout.newTab().setText("Recent"));
        tabLayout.addTab(tabLayout.newTab().setText("Applied"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getText().toString();
                // loadJobs(currentTab, currentSort);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        String[] options = {"Date", "Title", "Salary"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line,
                options
        );

        customSpinner.setAdapter(adapter);

// Open dropdown when clicking anywhere
        customSpinner.setOnClickListener(v -> customSpinner.showDropDown());

        customSpinner.setOnItemClickListener((parent, v, position, id) -> {
            String selected = options[position];
            customSpinner.setText(selected, false); // update selected text
            // handle selection here
        });


        return view;
    }
}
