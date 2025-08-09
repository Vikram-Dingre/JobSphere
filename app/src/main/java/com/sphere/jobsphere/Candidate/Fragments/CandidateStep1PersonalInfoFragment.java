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
import com.sphere.jobsphere.Candidate.Models.CandidatePersonalInfo;
import com.sphere.jobsphere.R;

public class CandidateStep1PersonalInfoFragment extends Fragment {

    AppCompatButton acbCandidateProfileSetupStep1Next;
    TextInputEditText tieCandidateProfileSetupStep1Name,tieCandidateProfileSetupStep1ProfileUrl,tieCandidateProfileSetupStep1Dob,tieCandidateProfileSetupStep1Email,tieCandidateProfileSetupStep1Phone,tieCandidateProfileSetupStep1CurrentLocation;

    CandidateProfileSetupActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidate_step1_personal_info, container, false);

        activity = (CandidateProfileSetupActivity) getActivity();

        ProgressBar progressBar = activity.findViewById(R.id.stepProgressBar);
        progressBar.setProgress(33);

        tieCandidateProfileSetupStep1Name = view.findViewById(R.id.tieCandidateProfileSetupStep1Name);
        tieCandidateProfileSetupStep1ProfileUrl = view.findViewById(R.id.tieCandidateProfileSetupStep1ProfileUrl);
        tieCandidateProfileSetupStep1Dob = view.findViewById(R.id.tieCandidateProfileSetupStep1Dob);
        tieCandidateProfileSetupStep1Email = view.findViewById(R.id.tieCandidateProfileSetupStep1Email);
        tieCandidateProfileSetupStep1Phone = view.findViewById(R.id.tieCandidateProfileSetupStep1Phone);
        tieCandidateProfileSetupStep1CurrentLocation = view.findViewById(R.id.tieCandidateProfileSetupStep1CurrentLocation);
        acbCandidateProfileSetupStep1Next = view.findViewById(R.id.acbCandidateProfileSetupStep1Next);

        acbCandidateProfileSetupStep1Next.setOnClickListener(v -> {
            CandidatePersonalInfo pd = new CandidatePersonalInfo();
            pd.setFullName(tieCandidateProfileSetupStep1Name.getText().toString());
            pd.setProfilePhotoUrl(tieCandidateProfileSetupStep1ProfileUrl.getText().toString());
            pd.setDob(tieCandidateProfileSetupStep1Dob.getText().toString());
            pd.setEmail(tieCandidateProfileSetupStep1Email.getText().toString());
            pd.setPhone(tieCandidateProfileSetupStep1Phone.getText().toString());
            pd.setCurrentLocation(tieCandidateProfileSetupStep1CurrentLocation.getText().toString());

            activity.candidateProfile.setPersonalInfo(pd);

            activity.loadFragment(new CandidateStep2ProfessionalDetailsFragment());



        });

        return view;
    }
}