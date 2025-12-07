package com.sphere.jobsphere.Recruiter.Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
import java.util.List;

public class RecruiterApplicationActivity extends AppCompatActivity {
    List<RecruiterApplicantsModel> applicants = new ArrayList<>();
    RecyclerView  applicantsRecyclerView;

    LinearLayoutManager linearLayoutManager;
    RecruiterHomeApplicantAdapter applicantAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_application);
        applicantsRecyclerView =findViewById(R.id.rvRecruiterApplicationActivityApplicants);

        loadApplicantsData();
        loadApplicantsRecyclerView();

    }

    private void loadApplicantsRecyclerView() {
        applicantsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        applicantAdapter = new RecruiterHomeApplicantAdapter(applicants, this);
        applicantsRecyclerView.setAdapter(applicantAdapter);
        applicantAdapter.notifyDataSetChanged();
    }

    private void loadApplicantsData() {
        for (int i = 1; i <= 8; i++) {
            applicants.add(new RecruiterApplicantsModel("https://dummyimage.com/100x100/000/fff&text=" + i,
                    i % 2 == 1 ? "Shivam Thosar" : "Vikram Dingre",
                    i % 2 == 1 ? "Frontend Devloper" : "Backend Devloper"));
        }
    }
}