package com.sphere.jobsphere.Candidate.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sphere.jobsphere.Candidate.Activities.CandidateProfileSetupActivity;
import com.sphere.jobsphere.R;

public class CandidateProfileSetupStep1Fragment extends Fragment {

    AppCompatButton acbCandidateProfileSetupStep1Next;
    EditText etCandidateProfileSetupStep1Name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidate_profile_setup_step1, container, false);

        acbCandidateProfileSetupStep1Next = view.findViewById(R.id.acbCandidateProfileSetupStep1Next);
        etCandidateProfileSetupStep1Name = view.findViewById(R.id.etCandidateProfileSetupStep1Name);

        acbCandidateProfileSetupStep1Next.setOnClickListener(v -> {
            String name = etCandidateProfileSetupStep1Name.getText().toString();
            CandidateProfileSetupActivity activity = (CandidateProfileSetupActivity) getActivity();
            activity.candidateProfileSetupData.fullName = name;

            // Move to next step

            getParentFragmentManager().beginTransaction().replace(R.id.flCandidateProfileSetupFrameContainer,new CandidateProfileSetupStep2Fragment()).addToBackStack(null).commit();
        });

        return view;
    }
}