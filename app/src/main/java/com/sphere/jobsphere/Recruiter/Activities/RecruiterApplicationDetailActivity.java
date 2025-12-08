package com.sphere.jobsphere.Recruiter.Activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;
import com.sphere.jobsphere.R;

public class RecruiterApplicationDetailActivity extends AppCompatActivity {
    LinearLayout llRequirementsSkillsContainer;
    TabLayout candidateJobDetailsTabLayout;
    LinearLayout candidateJobDetailsJobDescriptionTabContent, candidateJobDetailsCompanyTabContent;
    String currentUid;

    ImageView ivCandidateApplicationsCompanyLogo;
    TextView tvCandidateApplicationsJobName, tvCandidateApplicationsCompanyName, tvCandidateApplicationsAboutRole, tvCandidateApplicationsAboutCompany, tvCandidateApplicationsWebsite, tvCandidateApplicationsCity, tvCandidateApplicationsSize, tvCandidateApplicationsZipCode, tvCandidateApplicationsCountry;
    ChipGroup cgCandidateApplicationJobTypeChipGroup;

    private String currentTab;
    private String jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_application_detail);

        currentTab = "Job Description";
        llRequirementsSkillsContainer = findViewById(R.id.llRecruiterApplicationDetailRequirementsSkillsContainer);
        candidateJobDetailsTabLayout = findViewById(R.id.tlRecruiterApplicationDetailTabLayout);
        candidateJobDetailsJobDescriptionTabContent = findViewById(R.id.llRecruiterApplicationDetailJobDescriptionTabContent);
        candidateJobDetailsCompanyTabContent = findViewById(R.id.llRecruiterApplicationDetailCompanyTabContent);
        ivCandidateApplicationsCompanyLogo = findViewById(R.id.ivRecruiterApplicationDetailCompanyLogo);
        tvCandidateApplicationsJobName = findViewById(R.id.tvRecruiterApplicationDetailJobName);
        tvCandidateApplicationsCompanyName = findViewById(R.id.tvRecruiterApplicationDetailCompanyName);
        tvCandidateApplicationsAboutRole = findViewById(R.id.tvRecruiterApplicationDetailAboutRole);
        cgCandidateApplicationJobTypeChipGroup = findViewById(R.id.cgRecruiterApplicationDetailJobTypeChipGroup);
        tvCandidateApplicationsAboutCompany = findViewById(R.id.tvRecruiterApplicationDetailAboutCompany);
        tvCandidateApplicationsWebsite = findViewById(R.id.tvRecruiterApplicationDetailWebsite);
        tvCandidateApplicationsCity = findViewById(R.id.tvRecruiterApplicationDetailCity);
        tvCandidateApplicationsSize = findViewById(R.id.tvRecruiterApplicationDetailSize);
        tvCandidateApplicationsZipCode = findViewById(R.id.tvRecruiterApplicationDetailZipCode);
        tvCandidateApplicationsCountry = findViewById(R.id.tvRecruiterApplicationDetailCountry);

        tabsRelated();
    }

    private void tabsRelated() {
        // Tabs
        candidateJobDetailsTabLayout.addTab(candidateJobDetailsTabLayout.newTab().setText("Job Description"));
        candidateJobDetailsTabLayout.addTab(candidateJobDetailsTabLayout.newTab().setText("Company"));

        if (currentTab.equals("Job Description")) {
            candidateJobDetailsTabLayout.getTabAt(0).select();
        } else if (currentTab.equals("Company")) {
            candidateJobDetailsTabLayout.getTabAt(1).select();
        }

        candidateJobDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                currentTab = tab.getText().toString();

                candidateJobDetailsJobDescriptionTabContent.setVisibility(GONE);
                candidateJobDetailsCompanyTabContent.setVisibility(GONE);

                if (currentTab.equalsIgnoreCase("Job Description")) {
                    candidateJobDetailsJobDescriptionTabContent.setVisibility(VISIBLE);
                    candidateJobDetailsCompanyTabContent.setVisibility(GONE);
                } else if (currentTab.equalsIgnoreCase("Company")) {
                    candidateJobDetailsJobDescriptionTabContent.setVisibility(GONE);
                    candidateJobDetailsCompanyTabContent.setVisibility(VISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}