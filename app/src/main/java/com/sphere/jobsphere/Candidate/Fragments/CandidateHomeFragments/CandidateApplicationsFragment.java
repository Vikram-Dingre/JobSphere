package com.sphere.jobsphere.Candidate.Fragments.CandidateHomeFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Adapters.CandidateApplicationsPageAdapter;
import com.sphere.jobsphere.Candidate.Models.CandidateApplicationModel;
import com.sphere.jobsphere.R;

import java.util.ArrayList;
import java.util.List;


public class CandidateApplicationsFragment extends Fragment {

    List<CandidateApplicationModel> applications = new ArrayList<>();
    List<CandidateApplicationModel> filteredApplications = new ArrayList<>();

    RecyclerView applicationRecycler;
    CandidateApplicationsPageAdapter applicationsPageAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    EditText etCandidateApplicationsJobsSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_candidate_applications, container, false);

        currentUid = auth.getCurrentUser().getUid();

        applicationRecycler = view.findViewById(R.id.rvCandidateApplicationsRecycler);
        etCandidateApplicationsJobsSearch = view.findViewById(R.id.etCandidateApplicationsJobsSearch);


//        for (int i = 1; i <= 15; i++) {
//
//            String status;
//            if (i % 3 == 0) status = "Delivered";
//            else if (i % 2 == 0) status = "On the Way";
//            else status = "Rejected";
//
//            applications.add(
//                    new CandidateApplicationModel(
//                            "app" + i, // id
//                            "job"+i,
//                            "https://dummyimage.com/100x100/333/fff&text=C" + i, // companyLogo
//                            "Software Developer " + i, // jobName
//                            "Company " + i, // companyName
//                            "₹" + (4 + i) + " LPA", // jobSalary
//                            "Full-Time",
//                            (i % 2 == 0 ? "Bangalore" : "Mumbai"), // jobLocation
//                           "recruiterMessage",
//                            status // applicationStatus
//                    )
//            );
//        }

        applicationRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        applicationsPageAdapter = new CandidateApplicationsPageAdapter(filteredApplications, getActivity());
        applicationRecycler.setAdapter(applicationsPageAdapter);

        fetchApplications();

        etCandidateApplicationsJobsSearch.addTextChangedListener(new TextWatcher() {
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


        return view;
    }

    private void filterList(String text) {
        filteredApplications.clear();

        if (text == null || text.trim().isEmpty()) {
            filteredApplications.addAll(applications);
        } else {
            String query = text.toLowerCase();

            for (CandidateApplicationModel app : applications) {

                // search in job title, company name, location, status
                if ((app.getJobName() != null && app.getJobName().toLowerCase().contains(query)) ||
                        (app.getCompanyName() != null && app.getCompanyName().toLowerCase().contains(query)) ||
                        (app.getJobLocation() != null && app.getJobLocation().toLowerCase().contains(query)) ||
                        (app.getApplicationStatus() != null && app.getApplicationStatus().toLowerCase().contains(query))) {

                    filteredApplications.add(app);
                }
            }
        }

        applicationsPageAdapter.notifyDataSetChanged();
    }


    private void fetchApplications() {
        applications.clear();
        filteredApplications.clear();
        db.collection("CandidateApplications")
                .document(currentUid)
                .collection("applications")
                .get()
                .addOnSuccessListener(snapshots -> {
                    for (DocumentSnapshot doc : snapshots.getDocuments()) {
                        CandidateApplicationModel application = doc.toObject(CandidateApplicationModel.class);
                        application.id = doc.getId();
                        applications.add(application);
                    }
                    // Initially show everything
                    filteredApplications.addAll(applications);

                    applicationsPageAdapter.notifyDataSetChanged();
                });
    }
}

// How to create a recycler view..?

// create recycler view
// create layout
// create model according to layout
// create adapter using model and id names given in layout
// set layout and adapter to recycler in java file

// model filed names must match the field names that are stored into firebase so data field names must match that field names for data retrieval and storage