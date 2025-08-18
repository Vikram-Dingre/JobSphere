package com.sphere.jobsphere.Candidate.Fragments.CandidateHomeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sphere.jobsphere.Candidate.Adapters.CandidateHomeCategoryAdapter;
import com.sphere.jobsphere.Candidate.Adapters.CandidateHomeRecentJobsAdapter;
import com.sphere.jobsphere.Candidate.Adapters.CandidateHomeSuggestedJobsAdapter;
import com.sphere.jobsphere.Candidate.Models.CandidateHomeCategoryModel;
import com.sphere.jobsphere.Candidate.Models.CandidateJobModel;
import com.sphere.jobsphere.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CandidateHomeFragment extends Fragment {
    RecyclerView candidateCategoriesRecycler;
    RecyclerView candidateSuggestedJobsRecycler;
    RecyclerView candidateRecentJobsRecycler;
    List<CandidateHomeCategoryModel> categories = new ArrayList<>();
    List<CandidateJobModel> suggestedJobs = new ArrayList<>();
    List<CandidateJobModel> recentJobs = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidate_home, container, false);

        candidateCategoriesRecycler = view.findViewById(R.id.rvCandidateHomeCategories);
        candidateSuggestedJobsRecycler = view.findViewById(R.id.rvCandidateHomeSuggestedJobs);
        candidateRecentJobsRecycler = view.findViewById(R.id.rvCandidateHomeRecentJobs);

        candidateSuggestedJobsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        candidateCategoriesRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        candidateRecentJobsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        categories.add(new CandidateHomeCategoryModel(R.drawable.remote, "Remote"));
        categories.add(new CandidateHomeCategoryModel(R.drawable.full_time_job, "Full Time"));
        categories.add(new CandidateHomeCategoryModel(R.drawable.part_time_job, "Part Time"));
        categories.add(new CandidateHomeCategoryModel(R.drawable.freelance, "Freelancer"));


        suggestedJobs.add(new CandidateJobModel(
                "1",
                "Software Engineer",
                "We are looking for a passionate Software Engineer to join our team.",
                "Google",
                "https://logo.clearbit.com/google.com",   // companyLogo URL
                "Bangalore, India",
                "Full-time",                             // jobType (optional, fallback)
                Arrays.asList("Full-time", "Remote"),    // jobTypes
                "IT",
                "Java, Kotlin, Firebase",
                Arrays.asList("Java", "Kotlin", "Firebase"),
                "₹20 LPA",
                1800000.0,
                2500000.0,
                "Mid",
                "B.Tech",
                System.currentTimeMillis(),
                System.currentTimeMillis() + 604800000,  // +7 days
                "rec001",
                "Alice Johnson",
                "alice.hr@google.com",
                25,
                Arrays.asList("cand01", "cand02"),
                92.5
        ));

        suggestedJobs.add(new CandidateJobModel(
                "3",
                "Backend Developer",
                "Work on scalable APIs and database systems for enterprise apps.",
                "Amazon",
                "https://logo.clearbit.com/amazon.com",
                "Hyderabad, India",
                "Part-time",
                Arrays.asList("Part-time", "Remote"),
                "Software Development",
                "Node.js, Express, SQL",
                Arrays.asList("Node.js", "Express", "MySQL"),
                "₹12 LPA",
                900000.0,
                1500000.0,
                "Junior",
                "B.Sc / B.Tech",
                System.currentTimeMillis(),
                System.currentTimeMillis() + 2592000000L,  // +30 days
                "rec003",
                "Priya Sharma",
                "priya.hr@amazon.com",
                18,
                Arrays.asList("cand08", "cand09", "cand10"),
                78.0
        ));


        suggestedJobs.add(new CandidateJobModel(
                "2",
                "UI/UX Designer",
                "Design user-centric web and mobile app interfaces.",
                "Adobe",
                "https://logo.clearbit.com/adobe.com",
                "Remote",
                "Freelance",
                Arrays.asList("Freelance"),
                "Design",
                "Figma, Photoshop",
                Arrays.asList("Figma", "Photoshop"),
                "$30/hr",
                null,
                null,
                "Senior",
                "Any design degree",
                System.currentTimeMillis(),
                System.currentTimeMillis() + 1209600000, // +14 days
                "rec002",
                "John Doe",
                "john.hr@adobe.com",
                12,
                Arrays.asList("cand05", "cand07"),
                85.0
        ));


        recentJobs.add(new CandidateJobModel(
                "101",
                "Frontend Developer",
                "Build responsive web UIs with ReactJS and Tailwind.",
                "Google",
                "https://logo.clearbit.com/google.com",
                "Bengaluru, India",
                "Full-time",
                Arrays.asList("Full-time", "Remote"),
                "Software Development",
                "React, Redux, TailwindCSS",
                Arrays.asList("React", "Redux", "Tailwind"),
                "₹18 LPA",
                12000.0,
                18000.0,
                "Mid-Level",
                "B.Tech / M.Tech",
                System.currentTimeMillis(),
                System.currentTimeMillis() + 2592000000L,
                "rec101",
                "Rohit Mehta",
                "rohit.hr@google.com",
                30,
                Arrays.asList("cand21", "cand22"),
                85.0
        ));

        recentJobs.add(new CandidateJobModel(
                "102",
                "UI/UX Designer",
                "Design intuitive mobile app experiences and prototypes.",
                "Zomato",
                "https://logo.clearbit.com/zomato.com",
                "Gurgaon, India",
                "Freelance",
                Arrays.asList("Freelance", "Remote"),
                "Design",
                "Figma, Adobe XD",
                Arrays.asList("Figma", "Adobe XD"),
                "₹8 LPA",
                50000.0,
                90000.0,
                "Junior",
                "Any Design Degree",
                System.currentTimeMillis(),
                System.currentTimeMillis() + 2592000000L,
                "rec102",
                "Sneha Kapoor",
                "sneha.hr@zomato.com",
                10,
                Arrays.asList("cand11", "cand12", "cand13"),
                72.0
        ));

        recentJobs.add(new CandidateJobModel(
                "103",
                "Data Analyst",
                "Analyze business data and create dashboards using PowerBI.",
                "Flipkart",
                "https://logo.clearbit.com/flipkart.com",
                "Bengaluru, India",
                "Part-time",
                Arrays.asList("Part-time"),
                "Data Analytics",
                "SQL, Excel, PowerBI",
                Arrays.asList("SQL", "PowerBI", "Excel"),
                "₹10 LPA",
                700000.0,
                1200000.0,
                "Entry-Level",
                "B.Sc / BBA",
                System.currentTimeMillis(),
                System.currentTimeMillis() + 2592000000L,
                "rec103",
                "Karan Verma",
                "karan.hr@flipkart.com",
                22,
                Arrays.asList("cand31", "cand32"),
                69.0
        ));

        CandidateHomeCategoryAdapter adapter = new CandidateHomeCategoryAdapter(categories, getActivity());
        candidateCategoriesRecycler.setAdapter(adapter);

        CandidateHomeSuggestedJobsAdapter suggestedJobsAdapter = new CandidateHomeSuggestedJobsAdapter(getActivity(), suggestedJobs);
        candidateSuggestedJobsRecycler.setAdapter(suggestedJobsAdapter);

        CandidateHomeRecentJobsAdapter recentJobsAdapter = new CandidateHomeRecentJobsAdapter(recentJobs, getActivity());
        candidateRecentJobsRecycler.setAdapter(recentJobsAdapter);
        return view;
    }
}

