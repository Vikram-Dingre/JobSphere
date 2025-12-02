package com.sphere.jobsphere.Candidate.Fragments.CandidateHomeFragments;

// REMAINING FEATURES IN THIS FRAGMENT
//TODO
// 1. Sorting Feature
// 2. Save Jobs
// 3.


import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Activities.CandidateHomeActivity;
import com.sphere.jobsphere.Candidate.Adapters.CandidateJobsPageAdapter;
import com.sphere.jobsphere.Candidate.Classes.CandidateJobFilterModel;
import com.sphere.jobsphere.Candidate.Classes.JobFiltersBottomSheet;
import com.sphere.jobsphere.Candidate.Models.CandidateJobModel;
import com.sphere.jobsphere.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CandidateJobsFragment extends Fragment {

    private final String currentSort = "Date";
    private final Set<String> loadedTabs = new HashSet<>();
    List<CandidateJobModel> jobs = new ArrayList<>();
    CandidateJobsPageAdapter jobsPageAdapter;
    List<CandidateJobModel> cachedAllJobs = new ArrayList<>();
    List<CandidateJobModel> cachedSuggestedJobs = new ArrayList<>();
    List<CandidateJobModel> cachedRecentJobs = new ArrayList<>();
    List<CandidateJobModel> cachedAppliedJobs = new ArrayList<>();
    JobFiltersBottomSheet jobFiltersBottomSheet;
    boolean isFragmentStartingFirstTime = true;
    CandidateHomeActivity activity;
    private TabLayout tabLayout;
    private AutoCompleteTextView customSpinner;
    private RecyclerView jobsRecycler;
    private TextView tvCandidateJobsAllResultCount;
    private String currentTab;
    private AlertDialog progressDialog;
    private EditText etCandidateJobsSearch;
    private ImageView ivCandidateJobsFilter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidate_jobs, container, false);

        if (getArguments() != null) {
            currentTab = getArguments().getString("currentJobsTab");
        } else {
            currentTab = "All";
        }

        tabLayout = view.findViewById(R.id.jobsTabLayout);
        customSpinner = view.findViewById(R.id.sortDropDown);
        jobsRecycler = view.findViewById(R.id.rvCandidateJobsReycler);
        tvCandidateJobsAllResultCount = view.findViewById(R.id.tvCandidateJobsAllResultCount);
        etCandidateJobsSearch = view.findViewById(R.id.etCandidateJobsSearch);
        ivCandidateJobsFilter = view.findViewById(R.id.ivCandidateJobsFilter);

        jobsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvCandidateJobsAllResultCount.setText(jobs.size() + "");
        jobsPageAdapter = new CandidateJobsPageAdapter(jobs, getActivity());
        jobsRecycler.setAdapter(jobsPageAdapter);

        activity = (CandidateHomeActivity) getActivity();
//        fetchJobsForFirstTimeWithFilters();

        etCandidateJobsSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterJobs(s.toString().trim());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });

        ivCandidateJobsFilter.setOnClickListener(v -> {

            jobFiltersBottomSheet = new JobFiltersBottomSheet();

            jobFiltersBottomSheet.setOnFilterAppliedListener(filterModel -> {
                // Save filter in activity
                activity.lastAppliedFilter = filterModel;

                // Apply filter immediately
                applyFilters(filterModel);
            });

            jobFiltersBottomSheet.show(getActivity().getSupportFragmentManager(), "JobFiltersBottomSheet");
        });


        // Tabs
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("Suggested"));
        tabLayout.addTab(tabLayout.newTab().setText("Recent"));
        tabLayout.addTab(tabLayout.newTab().setText("Applied"));

        if (currentTab.equals("All")) {
            tabLayout.getTabAt(0).select();
        } else if (currentTab.equals("Suggested")) {
            tabLayout.getTabAt(1).select();
        } else if (currentTab.equals("Recent")) {
            tabLayout.getTabAt(2).select();
        } else {
            tabLayout.getTabAt(3).select();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getText().toString();

                // Load jobs for selected tab
                loadJobs(currentTab, currentSort);
//                makeText(getActivity(), "currentTab --> "+currentTab+"  "+cachedSuggestedJobs.size(), LENGTH_SHORT).show();


                if (!loadedTabs.contains(currentTab)) {
                    // if fetching jobs for first time in that tab then wait until data fetched and then apply filters
                    new Handler().postDelayed(() -> {
                        // Apply filters if saved in activity
                        if (activity.lastAppliedFilter != null) {
                            applyFilters(activity.lastAppliedFilter);
                        }

                        // Apply search if active
                        String searchQuery = etCandidateJobsSearch.getText().toString().trim();
                        if (!searchQuery.isEmpty()) {
                            filterJobs(searchQuery);
                        }
                    }, 700);
                } else {
                    // if already fetched then directly apply filters

                    // If filters are active, reapply them
                    if (jobFiltersBottomSheet != null) {
                        jobFiltersBottomSheet.applyFilters(false);
                    }

                    // If search is active, reapply it
                    if (!etCandidateJobsSearch.getText().toString().isEmpty()) {
                        filterJobs(etCandidateJobsSearch.getText().toString());
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        String[] options = {"Date", "Title", "Salary"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, options);
        customSpinner.setAdapter(adapter);
        customSpinner.setOnClickListener(v -> customSpinner.showDropDown());
        customSpinner.setOnItemClickListener((parent, v, position, id) -> {
            String selected = options[position];
            customSpinner.setText(selected, false); // update selected text
//            loadJobs(sortType,)
        });
        return view;
    }

//
//    @Override
//    public void onPause() {
//        super.onPause();
//        activity.copyJobFilterBottomSheet = jobFiltersBottomSheet;
//        makeText(activity, "paused"+activity.copyJobFilterBottomSheet, LENGTH_SHORT).show();
//    }

    @Override
    public void onResume() {
        super.onResume();
//        makeText(activity, "Resumed" + activity.lastAppliedFilter, LENGTH_SHORT).show();
        fetchJobsForFirstTimeWithFilters();
    }

    // new code adding

    private void fetchJobsForFirstTimeWithFilters() {

        loadJobs(currentTab, currentSort);

        new Handler().postDelayed(() -> {
            // Reapply last applied filters from activity
            if (activity.lastAppliedFilter != null) {
                applyFilters(activity.lastAppliedFilter);
            }

            // Reapply search if any
            String searchQuery = etCandidateJobsSearch.getText().toString().trim();
            if (!searchQuery.isEmpty()) {
                filterJobs(searchQuery);
            }
        }, 700);
    }


    private void applyFilters(CandidateJobFilterModel filters) {
        List<CandidateJobModel> sourceList;

        // Step 1: Pick source list based on current tab
        switch (currentTab) {
            case "All":
                sourceList = cachedAllJobs;
                break;
            case "Suggested":
                sourceList = cachedSuggestedJobs;
                break;
            case "Recent":
                sourceList = cachedRecentJobs;
                break;
            case "Applied":
                sourceList = cachedAppliedJobs;
                break;
            default:
                sourceList = cachedAllJobs;
        }

        // Step 2: Filter
        List<CandidateJobModel> filteredJobs = new ArrayList<>();
        for (CandidateJobModel job : sourceList) {
            boolean matches = true;

            // ✅ Job Type
            if (filters.getSelectedJobType() != null && !filters.getSelectedJobType().isEmpty()) {
                if (!filters.getSelectedJobType().contains(job.getJobType())) {
                    matches = false;
                }
            }

//            // ✅ Category
//            if (matches && filters.getSelectedCategory() != null && !filters.getSelectedCategory().isEmpty()) {
//                if (!filters.getSelectedCategory().contains(job.getCategory())) {
//                    matches = false;
//                }
//            }

            // ✅ Skills (at least one skill should match)
            if (matches && filters.getSelectedSkills() != null && !filters.getSelectedSkills().isEmpty()) {
                boolean skillMatch = false;
                for (String skill : filters.getSelectedSkills()) {
                    if (job.getSkillsList().contains(skill)) {
                        skillMatch = true;
                        break;
                    }
                }
                if (!skillMatch) matches = false;
            }

            // ✅ Experience
            if (matches && filters.getSelectedExperience() != null && !filters.getSelectedExperience().isEmpty()) {
                if (!job.getExperienceLevel().equalsIgnoreCase(filters.getSelectedExperience())) {
                    matches = false;
                }
            }

            // ✅ Location
//            if (matches && filters.getSelectedLocation() != null && !filters.getSelectedLocation().isEmpty()) {
//                if (!filters.getSelectedLocation().contains(job.getLocation())) {
//                    matches = false;
//                }
//            }

//            // ✅ Salary Range
//            if (matches && (filters.getSelectedMinSalary() != null || filters.getSelectedMaxSalary() != null)) {
//                double jobMin = job.getMinSalary() != null ? job.getMinSalary() : 0;
//                double jobMax = job.getMaxSalary() != null ? job.getMaxSalary() : 0;
//
//                if (filters.getSelectedMinSalary() != null && jobMax < filters.getSelectedMinSalary()) {
//                    matches = false;
//                }
//                if (filters.getSelectedMaxSalary() != null && jobMin > filters.getSelectedMaxSalary()) {
//                    matches = false;
//                }
//            }

            // ✅ Freshness (postedAt within N days)
//            if (matches && filters.getSelectedFreshness() != null && !filters.getSelectedFreshness().isEmpty()) {
//                long days = 0;
//                try {
//                    days = Long.parseLong(filters.getSelectedFreshness()); // e.g. "7" means last 7 days
//                } catch (NumberFormatException ignored) {}
//
//                if (days > 0) {
//                    long daysInMillis = days * 86400000L;
//                    long cutoff = System.currentTimeMillis() - daysInMillis;
//                    if (job.getPostedAt() < cutoff) {
//                        matches = false;
//                    }
//                }
//            }

            if (matches) {
                filteredJobs.add(job);
            }
        }

        // Step 3: Update adapter
        jobs.clear();
        jobs.addAll(filteredJobs);
        jobsPageAdapter.notifyDataSetChanged();
        tvCandidateJobsAllResultCount.setText(jobs.size() + "");
    }

    private void filterJobs(String query) {
        List<CandidateJobModel> filteredJobs = new ArrayList<>();
        List<CandidateJobModel> sourceList;

        // Determine which cached list to use based on currentTab
        switch (currentTab) {
            case "All":
                sourceList = cachedAllJobs;
                break;
            case "Suggested":
                sourceList = cachedSuggestedJobs;
                break;
            case "Recent":
                sourceList = cachedRecentJobs;
                break;
            case "Applied":
                sourceList = cachedAppliedJobs;
                break;
            default:
                sourceList = cachedAllJobs;
        }

        if (query.isEmpty()) {
            filteredJobs.addAll(sourceList); // no filter, show all
        } else {
            for (CandidateJobModel job : sourceList) {
                // Search in title, companyName, skills
                if (job.getTitle().toLowerCase().contains(query.toLowerCase()) || job.getCompanyName().toLowerCase().contains(query.toLowerCase()) || job.getSkillsRequired().toLowerCase().contains(query.toLowerCase())) {
                    filteredJobs.add(job);
                }
            }
        }

        // Update adapter
        jobs.clear();
        jobs.addAll(filteredJobs);
        jobsPageAdapter.notifyDataSetChanged();
        tvCandidateJobsAllResultCount.setText(jobs.size() + "");
    }


    private void loadJobs(String currentTab, String currentSort) {
        if (!loadedTabs.contains(currentTab)) {
            showLoader(); // only show first time
            jobs.clear();

            new Handler().postDelayed(() -> {
                if (currentTab.equals("All")) {
//                    for (int i = 1; i <= 30; i++) {
//                        cachedAllJobs.add(new CandidateJobModel("job" + i, // id
//                                "Software Engineer " + i, // title
//                                "We are looking for a passionate Software Engineer to join our dynamic team and work on exciting projects " + i, // description
//                                "Company " + i, // companyName
//                                "https://dummyimage.com/100x100/000/fff&text=" + i, // companyLogo
//                                i % 2 == 0 ? "Bangalore" : "Mumbai", // location
//                                i % 2 == 0 ? "Full-Time" : "Part-Time", // jobType
//                                Arrays.asList("Remote", "Hybrid"), // jobTypes
//                                i % 3 == 0 ? "IT" : "Finance", // category
//                                "Java, Spring Boot, SQL", // skillsRequired
//                                Arrays.asList("Java", "Spring Boot", "SQL", "Firebase"), // skillsList
//                                "₹" + (5 + i) + " LPA", // salary
//                                5000.0 + i, // minSalary
//                                10000.0 + i, // maxSalary
//                                i % 2 == 0 ? "Mid Level" : "Entry Level", // experienceLevel
//                                "B.Tech / M.Tech", // education
//                                System.currentTimeMillis() - (i * 86400000L), // postedAt (i days ago)
//                                System.currentTimeMillis() + (30 * 86400000L), // deadline (30 days from now)
//                                "recruiter" + i, // recruiterId
//                                "Recruiter " + i, // recruiterName
//                                "recruiter" + i + "@company.com", // recruiterEmail
//                                i * 3, // applicantsCount
//                                Arrays.asList("cand1", "cand2", "cand3")
//                        ));
//                    }

                    db.collection("jobs")
                            .addSnapshotListener((snapshots, e) -> {
                                if (e != null || snapshots == null) {
                                    Log.e("Firestore", "Error loading Jobs.", e);
                                    return;
                                }

                                for (DocumentSnapshot doc : snapshots.getDocuments()) {
                                    CandidateJobModel job = doc.toObject(CandidateJobModel.class);

                                    if (job != null) {
                                        job.id = doc.getId();
                                        cachedAllJobs.add(job);
                                    }
                                }
                            });

                    jobs.addAll(cachedAllJobs);
                } else if (currentTab.equals("Suggested")) {
//                    for (int i = 20; i >= 1; i--) {
//                        cachedSuggestedJobs.add(new CandidateJobModel("job" + i, // id
//                                "Software Engineer " + i, // title
//                                "We are looking for a passionate Software Engineer to join our dynamic team and work on exciting projects " + i, // description
//                                "Company " + i, // companyName
//                                "https://dummyimage.com/100x100/000/fff&text=" + i, // companyLogo
//                                i % 2 == 0 ? "Bangalore" : "Mumbai", // location
//                                i % 2 == 0 ? "Full-Time" : "Part-Time", // jobType
//                                Arrays.asList("Remote", "Hybrid"), // jobTypes
//                                i % 3 == 0 ? "IT" : "Finance", // category
//                                "Java, Spring Boot, SQL", // skillsRequired
//                                Arrays.asList("Java", "Spring Boot", "SQL", "Firebase"), // skillsList
//                                "₹" + (5 + i) + " LPA", // salary
//                                5000.0 + i, // minSalary
//                                10000.0 + i, // maxSalary
//                                i % 2 == 0 ? "Mid" : "Entry", // experienceLevel
//                                "B.Tech / M.Tech", // education
//                                System.currentTimeMillis() - (i * 86400000L), // postedAt (i days ago)
//                                System.currentTimeMillis() + (30 * 86400000L), // deadline (30 days from now)
//                                "recruiter" + i, // recruiterId
//                                "Recruiter " + i, // recruiterName
//                                "recruiter" + i + "@company.com", // recruiterEmail
//                                i * 3, // applicantsCount
//                                Arrays.asList("cand1", "cand2", "cand3")
//                        ));
//                    }
                    db.collection("jobs")
                            .addSnapshotListener((snapshots, e) -> {
                                if (e != null || snapshots == null) {
                                    Log.e("Firestore", "Error loading Jobs.", e);
                                    return;
                                }

                                for (DocumentSnapshot doc : snapshots.getDocuments()) {
                                    CandidateJobModel job = doc.toObject(CandidateJobModel.class);

                                    if (job != null) {
                                        job.id = doc.getId();
                                        cachedSuggestedJobs.add(job);
                                    }
                                }
                            });
                    jobs.addAll(cachedSuggestedJobs);
                } else if (currentTab.equals("Recent")) {
//                    for (int i = 1; i <= 10; i++) {
//                        cachedRecentJobs.add(new CandidateJobModel("job" + i, // id
//                                "Software Engineer " + i, // title
//                                "We are looking for a passionate Software Engineer to join our dynamic team and work on exciting projects " + i, // description
//                                "Company " + i, // companyName
//                                "https://dummyimage.com/100x100/000/fff&text=" + i, // companyLogo
//                                i % 2 == 0 ? "Bangalore" : "Mumbai", // location
//                                i % 2 == 0 ? "Full-Time" : "Part-Time", // jobType
//                                Arrays.asList("Remote", "Hybrid"), // jobTypes
//                                i % 3 == 0 ? "IT" : "Finance", // category
//                                "Java, Spring Boot, SQL", // skillsRequired
//                                Arrays.asList("Java", "Spring Boot", "SQL", "Firebase"), // skillsList
//                                "₹" + (5 + i) + " LPA", // salary
//                                5000.0 + i, // minSalary
//                                10000.0 + i, // maxSalary
//                                i % 2 == 0 ? "Mid" : "Entry", // experienceLevel
//                                "B.Tech / M.Tech", // education
//                                System.currentTimeMillis() - (i * 86400000L), // postedAt (i days ago)
//                                System.currentTimeMillis() + (30 * 86400000L), // deadline (30 days from now)
//                                "recruiter" + i, // recruiterId
//                                "Recruiter " + i, // recruiterName
//                                "recruiter" + i + "@company.com", // recruiterEmail
//                                i * 3, // applicantsCount
//                                Arrays.asList("cand1", "cand2", "cand3")
//                        ));
//                    }
                    db.collection("jobs")
                            .addSnapshotListener((snapshots, e) -> {
                                if (e != null || snapshots == null) {
                                    Log.e("Firestore", "Error loading Jobs.", e);
                                    return;
                                }

                                for (DocumentSnapshot doc : snapshots.getDocuments()) {
                                    CandidateJobModel job = doc.toObject(CandidateJobModel.class);

                                    if (job != null) {
                                        job.id = doc.getId();
                                        cachedRecentJobs.add(job);
                                    }
                                }
                            });
                    jobs.addAll(cachedRecentJobs);
                } else {
                    for (int i = 15; i >= 1; i--) {
                        cachedAppliedJobs.add(new CandidateJobModel("job" + i, // id
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
                                i % 2 == 0 ? "Mid" : "Entry", // experienceLevel
                                "B.Tech / M.Tech", // education
                                System.currentTimeMillis() - (i * 86400000L), // postedAt (i days ago)
                                System.currentTimeMillis() + (30 * 86400000L), // deadline (30 days from now)
                                "recruiter" + i, // recruiterId
                                "Recruiter " + i, // recruiterName
                                "recruiter" + i + "@company.com", // recruiterEmail
                                i * 3, // applicantsCount
                                Arrays.asList("cand1", "cand2", "cand3")
                        ));
                    }
                    jobs.addAll(cachedAppliedJobs);
                }

                jobsPageAdapter.notifyDataSetChanged();
                tvCandidateJobsAllResultCount.setText(jobs.size() + "");

// 🔹 Reapply filters if active
                if (activity.lastAppliedFilter != null) {
                    applyFilters(activity.lastAppliedFilter);
                }

// 🔹 Reapply search if active
                String searchQuery = etCandidateJobsSearch.getText().toString().trim();
                if (!searchQuery.isEmpty()) {
                    filterJobs(searchQuery);
                }
                loadedTabs.add(currentTab);
                hideLoader();
            }, 700); //700
        } else {
            jobs.clear();

            if (currentTab.equals("All")) {
                jobs.addAll(cachedAllJobs);
            } else if (currentTab.equals("Suggested")) {
                jobs.addAll(cachedSuggestedJobs);
            } else if (currentTab.equals("Recent")) {
                jobs.addAll(cachedRecentJobs);
            } else {
                jobs.addAll(cachedAppliedJobs);
            }

            jobsPageAdapter.notifyDataSetChanged();
            jobsRecycler.setVerticalScrollbarPosition(1);
            tvCandidateJobsAllResultCount.setText(jobs.size() + "");

// 🔹 Reapply filters if active
            if (activity.lastAppliedFilter != null) {
                applyFilters(activity.lastAppliedFilter);
            }

// 🔹 Reapply search if active
            String searchQuery = etCandidateJobsSearch.getText().toString().trim();
            if (!searchQuery.isEmpty()) {
                filterJobs(searchQuery);
            }
        }
    }

    private void showLoader() {
        if (progressDialog == null) {
            // Parent layout
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.CENTER);
            layout.setPadding(50, 50, 50, 50);

            // Circular Progress Indicator
            CircularProgressIndicator progressIndicator = new CircularProgressIndicator(getActivity());
            progressIndicator.setIndeterminate(true);
            progressIndicator.setIndicatorSize(120);
            progressIndicator.setTrackThickness(8);
            progressIndicator.setIndicatorColor(getResources().getColor(R.color.white));

            // Add loader to layout
            layout.addView(progressIndicator);

            // Text below loader
            TextView textView = new TextView(getActivity());
            textView.setText("Loading...");
            textView.setTextSize(16f);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0, 20, 0, 0);
            textView.setTextColor(getResources().getColor(R.color.white));
            layout.addView(textView);

            // Build AlertDialog
            progressDialog = new AlertDialog.Builder(getActivity()).setView(layout).setCancelable(false).create();

            if (progressDialog.getWindow() != null) {
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        }
        progressDialog.show();
    }

    private void hideLoader() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}


// afterward use this technique for showing loader and hiding loader to cached tabs

// for that use four different lists and then pass it to adapter and also use it as cached jobs of that tab

//private final Set<String> loadedTabs = new HashSet<>();
//
//private void loadJobs(String currentTab, String currentSort) {
//    if (!loadedTabs.contains(currentTab)) {
//        showLoader(); // only show first time
//    }
//    jobs.clear();
//
//    new Handler().postDelayed(() -> {
//        // Fetch jobs (API/dummy logic)
//        jobsPageAdapter.notifyDataSetChanged();
//        tvCandidateJobsAllResultCount.setText(jobs.size() + " Results");
//
//        hideLoader();
//        loadedTabs.add(currentTab); // mark this tab as loaded
//    }, 1500);
//}


/// ///////////////////////////////////////////////////////////////////////// For Nothing ////////////////////////////////////////////////////////////////////////////////////
//    private void loadJobs(String currentTab, String currentSort) {
//        showLoader();
//        jobs.clear();
//        new Handler().postDelayed(() -> {
//            if (currentTab.equals("All")) {
//                for (int i = 1; i <= 30; i++) {
//                    jobs.add(new CandidateJobModel("job" + i, // id
//                            "Software Engineer " + i, // title
//                            "We are looking for a passionate Software Engineer to join our dynamic team and work on exciting projects " + i, // description
//                            "Company " + i, // companyName
//                            "https://dummyimage.com/100x100/000/fff&text=" + i, // companyLogo
//                            i % 2 == 0 ? "Bangalore" : "Mumbai", // location
//                            i % 2 == 0 ? "Full-Time" : "Part-Time", // jobType
//                            Arrays.asList("Remote", "Hybrid"), // jobTypes
//                            i % 3 == 0 ? "IT" : "Finance", // category
//                            "Java, Spring Boot, SQL", // skillsRequired
//                            Arrays.asList("Java", "Spring Boot", "SQL", "Firebase"), // skillsList
//                            "₹" + (5 + i) + " LPA", // salary
//                            5000.0 + i, // minSalary
//                            10000.0 + i, // maxSalary
//                            i % 2 == 0 ? "Mid" : "Entry", // experienceLevel
//                            "B.Tech / M.Tech", // education
//                            System.currentTimeMillis() - (i * 86400000L), // postedAt (i days ago)
//                            System.currentTimeMillis() + (30 * 86400000L), // deadline (30 days from now)
//                            "recruiter" + i, // recruiterId
//                            "Recruiter " + i, // recruiterName
//                            "recruiter" + i + "@company.com", // recruiterEmail
//                            i * 3, // applicantsCount
//                            Arrays.asList("cand1", "cand2", "cand3"), // applicants
//                            Math.random() * 100 // matchScore
//                    ));
//                }
//            } else if (currentTab.equals("Suggested")) {
//                for (int i = 20; i >= 1; i--) {
//                    jobs.add(new CandidateJobModel("job" + i, // id
//                            "Software Engineer " + i, // title
//                            "We are looking for a passionate Software Engineer to join our dynamic team and work on exciting projects " + i, // description
//                            "Company " + i, // companyName
//                            "https://dummyimage.com/100x100/000/fff&text=" + i, // companyLogo
//                            i % 2 == 0 ? "Bangalore" : "Mumbai", // location
//                            i % 2 == 0 ? "Full-Time" : "Part-Time", // jobType
//                            Arrays.asList("Remote", "Hybrid"), // jobTypes
//                            i % 3 == 0 ? "IT" : "Finance", // category
//                            "Java, Spring Boot, SQL", // skillsRequired
//                            Arrays.asList("Java", "Spring Boot", "SQL", "Firebase"), // skillsList
//                            "₹" + (5 + i) + " LPA", // salary
//                            5000.0 + i, // minSalary
//                            10000.0 + i, // maxSalary
//                            i % 2 == 0 ? "Mid" : "Entry", // experienceLevel
//                            "B.Tech / M.Tech", // education
//                            System.currentTimeMillis() - (i * 86400000L), // postedAt (i days ago)
//                            System.currentTimeMillis() + (30 * 86400000L), // deadline (30 days from now)
//                            "recruiter" + i, // recruiterId
//                            "Recruiter " + i, // recruiterName
//                            "recruiter" + i + "@company.com", // recruiterEmail
//                            i * 3, // applicantsCount
//                            Arrays.asList("cand1", "cand2", "cand3"), // applicants
//                            Math.random() * 100 // matchScore
//                    ));
//                }
//            } else if (currentTab.equals("Recent")) {
//                for (int i = 1; i <= 10; i++) {
//                    jobs.add(new CandidateJobModel("job" + i, // id
//                            "Software Engineer " + i, // title
//                            "We are looking for a passionate Software Engineer to join our dynamic team and work on exciting projects " + i, // description
//                            "Company " + i, // companyName
//                            "https://dummyimage.com/100x100/000/fff&text=" + i, // companyLogo
//                            i % 2 == 0 ? "Bangalore" : "Mumbai", // location
//                            i % 2 == 0 ? "Full-Time" : "Part-Time", // jobType
//                            Arrays.asList("Remote", "Hybrid"), // jobTypes
//                            i % 3 == 0 ? "IT" : "Finance", // category
//                            "Java, Spring Boot, SQL", // skillsRequired
//                            Arrays.asList("Java", "Spring Boot", "SQL", "Firebase"), // skillsList
//                            "₹" + (5 + i) + " LPA", // salary
//                            5000.0 + i, // minSalary
//                            10000.0 + i, // maxSalary
//                            i % 2 == 0 ? "Mid" : "Entry", // experienceLevel
//                            "B.Tech / M.Tech", // education
//                            System.currentTimeMillis() - (i * 86400000L), // postedAt (i days ago)
//                            System.currentTimeMillis() + (30 * 86400000L), // deadline (30 days from now)
//                            "recruiter" + i, // recruiterId
//                            "Recruiter " + i, // recruiterName
//                            "recruiter" + i + "@company.com", // recruiterEmail
//                            i * 3, // applicantsCount
//                            Arrays.asList("cand1", "cand2", "cand3"), // applicants
//                            Math.random() * 100 // matchScore
//                    ));
//                }
//            } else {
//                for (int i = 10; i >= 1; i--) {
//                    jobs.add(new CandidateJobModel("job" + i, // id
//                            "Software Engineer " + i, // title
//                            "We are looking for a passionate Software Engineer to join our dynamic team and work on exciting projects " + i, // description
//                            "Company " + i, // companyName
//                            "https://dummyimage.com/100x100/000/fff&text=" + i, // companyLogo
//                            i % 2 == 0 ? "Bangalore" : "Mumbai", // location
//                            i % 2 == 0 ? "Full-Time" : "Part-Time", // jobType
//                            Arrays.asList("Remote", "Hybrid"), // jobTypes
//                            i % 3 == 0 ? "IT" : "Finance", // category
//                            "Java, Spring Boot, SQL", // skillsRequired
//                            Arrays.asList("Java", "Spring Boot", "SQL", "Firebase"), // skillsList
//                            "₹" + (5 + i) + " LPA", // salary
//                            5000.0 + i, // minSalary
//                            10000.0 + i, // maxSalary
//                            i % 2 == 0 ? "Mid" : "Entry", // experienceLevel
//                            "B.Tech / M.Tech", // education
//                            System.currentTimeMillis() - (i * 86400000L), // postedAt (i days ago)
//                            System.currentTimeMillis() + (30 * 86400000L), // deadline (30 days from now)
//                            "recruiter" + i, // recruiterId
//                            "Recruiter " + i, // recruiterName
//                            "recruiter" + i + "@company.com", // recruiterEmail
//                            i * 3, // applicantsCount
//                            Arrays.asList("cand1", "cand2", "cand3"), // applicants
//                            Math.random() * 100 // matchScore
//                    ));
//                }
//            }
//            jobsPageAdapter.notifyDataSetChanged();
//            jobsRecycler.setVerticalScrollbarPosition(1);
//            tvCandidateJobsAllResultCount.setText(jobs.size() + "");
//            hideLoader();
//        }, 400);
//
//
//    }