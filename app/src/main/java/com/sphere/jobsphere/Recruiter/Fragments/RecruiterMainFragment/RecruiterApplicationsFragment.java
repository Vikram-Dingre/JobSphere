package com.sphere.jobsphere.Recruiter.Fragments.RecruiterMainFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Adapters.RecruiterApplicationsPageAdapter;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterApplicationsModel;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterJobModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RecruiterApplicationsFragment extends Fragment {
    RecyclerView applicationsRecyclerView;
    List<RecruiterApplicationsModel> applications=new ArrayList<>();

    RecruiterApplicationsPageAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_recruiter_applications, container, false);
        applicationsRecyclerView=view.findViewById(R.id.rvRecruiterAFApplications);

        loadApplicationsData();
        loadApplicationsRecyclerView();
        return view;
    }

    private void loadApplicationsRecyclerView() {
        applicationsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        adapter=new RecruiterApplicationsPageAdapter(applications,getActivity());
        applicationsRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadApplicationsData() {
        for (int i = 1; i <= 30; i++) {

            applications.add(new RecruiterApplicationsModel("job" + i, // id
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