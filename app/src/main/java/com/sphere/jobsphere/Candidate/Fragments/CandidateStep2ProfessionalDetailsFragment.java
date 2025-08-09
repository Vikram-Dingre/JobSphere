package com.sphere.jobsphere.Candidate.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.sphere.jobsphere.Candidate.Activities.CandidateProfileSetupActivity;
import com.sphere.jobsphere.Candidate.Models.CandiateProfessionalDetails;
import com.sphere.jobsphere.R;

import java.util.Arrays;

public class CandidateStep2ProfessionalDetailsFragment extends Fragment {
    AppCompatButton acbCandidateProfileSetupStep2Next;
    TextInputEditText tieCandidateProfileSetupStep2JobTitle,tieCandidateProfileSetupStep2CurrentCompany,tieCandidateProfileSetupStep2Experience,tieCandidateProfileSetupStep2Skills,tieCandidateProfileSetupStep2ExpectedSalary;
    CandidateProfileSetupActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_candidate_step2_professional_details, container, false);

        activity = (CandidateProfileSetupActivity) getActivity();

        ProgressBar progressBar = activity.findViewById(R.id.stepProgressBar);
        progressBar.setProgress(66);

        tieCandidateProfileSetupStep2JobTitle = view.findViewById(R.id.tieCandidateProfileSetupStep2JobTitle);
        tieCandidateProfileSetupStep2CurrentCompany = view.findViewById(R.id.tieCandidateProfileSetupStep2CurrentCompany);
        tieCandidateProfileSetupStep2Experience = view.findViewById(R.id.tieCandidateProfileSetupStep2Experience);
        tieCandidateProfileSetupStep2Skills = view.findViewById(R.id.tieCandidateProfileSetupStep2Skills);
        tieCandidateProfileSetupStep2ExpectedSalary = view.findViewById(R.id.tieCandidateProfileSetupStep2ExpectedSalary);
        acbCandidateProfileSetupStep2Next = view.findViewById(R.id.acbCandidateProfileSetupStep2Next);

        acbCandidateProfileSetupStep2Next.setOnClickListener(v -> {
            CandiateProfessionalDetails pd = new CandiateProfessionalDetails();
            pd.setJobTitle(tieCandidateProfileSetupStep2JobTitle.getText().toString());
            pd.setCurrentCompany(tieCandidateProfileSetupStep2CurrentCompany.getText().toString());
            pd.setExperience(tieCandidateProfileSetupStep2Experience.getText().toString());
            pd.setSkills(Arrays.asList(tieCandidateProfileSetupStep2Skills.getText().toString().split(",")));
            pd.setExpectedSalary(Double.parseDouble(tieCandidateProfileSetupStep2ExpectedSalary.getText().toString()));

            activity.candidateProfile.setProfessionalDetails(pd);

            activity.loadFragment(new CandidateStep3EducationFragment());
        });

        
        return view;
    }
    
}