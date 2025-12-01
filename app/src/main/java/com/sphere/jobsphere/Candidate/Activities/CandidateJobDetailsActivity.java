package com.sphere.jobsphere.Candidate.Activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.sphere.jobsphere.R;

import java.util.Arrays;
import java.util.List;

public class CandidateJobDetailsActivity extends AppCompatActivity {
    LinearLayout llRequirementsSkillsContainer;
    TabLayout candidateJobDetailsTabLayout;
    LinearLayout candidateJobDetailsJobDescriptionTabContent,candidateJobDetailsCompanyTabContent;
    private String currentTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_job_details);

        llRequirementsSkillsContainer = findViewById(R.id.llRequirementsSkillsContainer);
        candidateJobDetailsTabLayout = findViewById(R.id.candidateJobDetailsTabLayout);
        candidateJobDetailsJobDescriptionTabContent = findViewById(R.id.candidateJobDetailsJobDescriptionTabContent);
        candidateJobDetailsCompanyTabContent = findViewById(R.id.candidateJobDetailsCompanyTabContent);


        currentTab = "Job Description";

        addDummySkills();

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

                if (currentTab.equalsIgnoreCase("Job Description")){
                    candidateJobDetailsJobDescriptionTabContent.setVisibility(VISIBLE);
                    candidateJobDetailsCompanyTabContent.setVisibility(GONE);
                }else if (currentTab.equalsIgnoreCase("Company")){
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

    private void addDummySkills() {
        List<String> requirements = Arrays.asList(
                "Strong Java knowledge",
                "Experience with Android Studio",
                "Knowledge of Firebase",
                "Good communication skills",
                "Kiss With Ritu Baby",
                "I Love You Ritu Jaanu"
        );
        // Add each requirement as a bullet point
        for (String req : requirements) {
            TextView tv = new TextView(this);
            tv.setText("✅   " + req);
            tv.setTextSize(14);
            tv.setBackgroundResource(R.drawable.candidate_job_details_skills_shape);
            tv.setPadding(30, 30, 30, 30);

// Create LayoutParams for margins
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

// Set margin (left, top, right, bottom)
            params.setMargins(0, 8, 0, 8);

// Apply LayoutParams to TextView
            tv.setLayoutParams(params);

// Add to container
            llRequirementsSkillsContainer.addView(tv);

        }

    }
}