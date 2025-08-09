package com.sphere.jobsphere.Candidate.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sphere.jobsphere.Candidate.Activities.CandidateProfileSetupActivity;
import com.sphere.jobsphere.Candidate.Models.CandidateEducation;
import com.sphere.jobsphere.R;

import java.util.ArrayList;
import java.util.List;


public class CandidateStep3EducationFragment extends Fragment {

    TextInputEditText tieCandidateProfileSetupStep3Qualifications,tieCandidateProfileSetupStep3Specialization,tieCandidateProfileSetupStep3University,tieCandidateProfileSetupStep3GraduationYear;
    AppCompatButton acbCandidateProfileSetupStep3Next;
    CandidateProfileSetupActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidate_step3_education, container, false);

        activity = (CandidateProfileSetupActivity) getActivity();
        ProgressBar progressBar = activity.findViewById(R.id.stepProgressBar);
        progressBar.setProgress(100);

        tieCandidateProfileSetupStep3Qualifications = view.findViewById(R.id.tieCandidateProfileSetupStep3Qualifications);
        tieCandidateProfileSetupStep3Specialization = view.findViewById(R.id.tieCandidateProfileSetupStep3Specialization);
        tieCandidateProfileSetupStep3University = view.findViewById(R.id.tieCandidateProfileSetupStep3University);
        tieCandidateProfileSetupStep3GraduationYear = view.findViewById(R.id.tieCandidateProfileSetupStep3GraduationYear);
        acbCandidateProfileSetupStep3Next = view.findViewById(R.id.acbCandidateProfileSetupStep3Next);

        acbCandidateProfileSetupStep3Next.setOnClickListener(v -> {
            CandidateEducation edu = new CandidateEducation();
            edu.setQualification(tieCandidateProfileSetupStep3Qualifications.getText().toString());
            edu.setSpecialization(tieCandidateProfileSetupStep3Specialization.getText().toString());
            edu.setUniversity(tieCandidateProfileSetupStep3University.getText().toString());
            edu.setGraduationYear(Integer.parseInt(tieCandidateProfileSetupStep3GraduationYear.getText().toString()));

            List<CandidateEducation> eduList = new ArrayList<>();
            eduList.add(edu);

            activity.candidateProfile.setEducation(eduList);
            activity.saveProfileToFirestore();

//            Toast.makeText(activity, "Profile Setup Successfull.", Toast.LENGTH_SHORT).show();
//            activity.loadFragment(new CandidateStep4ResumePreferencesFragment());
        });


        return view;
    }
}