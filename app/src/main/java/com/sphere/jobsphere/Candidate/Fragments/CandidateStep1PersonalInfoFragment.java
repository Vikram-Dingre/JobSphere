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
import com.sphere.jobsphere.Candidate.Models.CandidatePersonalInfo;
import com.sphere.jobsphere.R;

public class CandidateStep1PersonalInfoFragment extends Fragment {

    AppCompatButton acbCandidateProfileSetupStep1Next;
    TextInputEditText tieCandidateProfileSetupStep1Name;

    EditText etName, etEmail, etPhone, etLocation;
    Button btnNext;
    CandidateProfileSetupActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidate_step1_personal_info, container, false);

        acbCandidateProfileSetupStep1Next = view.findViewById(R.id.acbCandidateProfileSetupStep1Next);
        tieCandidateProfileSetupStep1Name = view.findViewById(R.id.tieCandidateProfileSetupStep1Name);
            activity = (CandidateProfileSetupActivity) getActivity();

        ProgressBar progressBar = activity.findViewById(R.id.stepProgressBar);
        progressBar.setProgress(25);

        acbCandidateProfileSetupStep1Next.setOnClickListener(v -> {
            String name = tieCandidateProfileSetupStep1Name.getText().toString();
//            CandidateProfileSetupActivity activity1 = (CandidateProfileSetupActivity) getActivity();
//            activity1.candidateProfileSetupData.fullName = name;

            // Move to next step
            activity.loadFragment(new CandidateStep2ProfessionalDetailsFragment());
//            getParentFragmentManager().beginTransaction().replace(R.id.flCandidateProfileSetupFrameContainer,new CandidateStep2ProfessionalDetailsFragment()).addToBackStack(null).commit();


            /// ////////////////////////////////////////////////////////////////////////////////////////////

//            activity = (CandidateProfileSetupActivity) getActivity();
//
//
//
//            etName = view.findViewById(R.id.etName);
//            etEmail = view.findViewById(R.id.etEmail);
//            etPhone = view.findViewById(R.id.etPhone);
//            etLocation = view.findViewById(R.id.etLocation);
//            btnNext = view.findViewById(R.id.btnNext);
//
//            btnNext.setOnClickListener(v -> {
//                CandidatePersonalInfo personalInfo = new CandidatePersonalInfo();
//                personalInfo.setFullName(etName.getText().toString());
//                personalInfo.setEmail(etEmail.getText().toString());
//                personalInfo.setPhone(etPhone.getText().toString());
//                personalInfo.setCurrentLocation(etLocation.getText().toString());
//
//                activity.candidateProfile.setPersonalInfo(personalInfo);
////                activity.saveProfileToFirestore(false); // save draft
//
//                activity.loadFragment(new CandidateStep2ProfessionalDetailsFragment());
//            });


        });

        return view;
    }
}