package com.sphere.jobsphere.Candidate.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.sphere.jobsphere.Candidate.Activities.CandidateProfileSetupActivity;
import com.sphere.jobsphere.Candidate.Models.CandidateEducation;
import com.sphere.jobsphere.R;

import java.util.ArrayList;
import java.util.List;


public class CandidateStep3EducationFragment extends Fragment {


    EditText etQualification, etSpecialization, etUniversity, etYear;
    Button btnNext;
    CandidateProfileSetupActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidate_step3_education, container, false);

        activity = (CandidateProfileSetupActivity) getActivity();
        ProgressBar progressBar = activity.findViewById(R.id.stepProgressBar);
        progressBar.setProgress(75);

        etQualification = view.findViewById(R.id.etQualification);
        etSpecialization = view.findViewById(R.id.etSpecialization);
        etUniversity = view.findViewById(R.id.etUniversity);
        etYear = view.findViewById(R.id.etYear);
        btnNext = view.findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            CandidateEducation edu = new CandidateEducation();
            edu.setQualification(etQualification.getText().toString());
            edu.setSpecialization(etSpecialization.getText().toString());
            edu.setUniversity(etUniversity.getText().toString());
            edu.setGraduationYear(Integer.parseInt(etYear.getText().toString()));

            List<CandidateEducation> eduList = new ArrayList<>();
            eduList.add(edu);

            activity.candidateProfile.setEducation(eduList);

            activity.loadFragment(new CandidateStep4ResumePreferencesFragment());
        });


        return view;
    }
}