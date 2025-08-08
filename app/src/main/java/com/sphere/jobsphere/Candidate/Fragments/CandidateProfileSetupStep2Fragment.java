package com.sphere.jobsphere.Candidate.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.sphere.jobsphere.Candidate.Activities.CandidateHomeActivity;
import com.sphere.jobsphere.Candidate.Activities.CandidateProfileSetupActivity;
import com.sphere.jobsphere.R;

public class CandidateProfileSetupStep2Fragment extends Fragment {
    AppCompatButton acbCandidateProfileSetupStep2Next;
    EditText etCandidateProfileSetupStep2Email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_candidate_profile_setup_step2, container, false);

        acbCandidateProfileSetupStep2Next = view.findViewById(R.id.acbCandidateProfileSetupStep2Next);
        etCandidateProfileSetupStep2Email = view.findViewById(R.id.etCandidateProfileSetupStep2Email);

        acbCandidateProfileSetupStep2Next.setOnClickListener(v -> {
            String Email = etCandidateProfileSetupStep2Email.getText().toString();
            CandidateProfileSetupActivity activity = (CandidateProfileSetupActivity) getActivity();
            activity.candidateProfileSetupData.email = Email;


            Toast.makeText(getActivity(), "Name : "+ activity.candidateProfileSetupData.fullName + "\n" + " Email : "+activity.candidateProfileSetupData.email , Toast.LENGTH_SHORT).show();

            // Move to next step

//            getParentFragmentManager().beginTransaction().replace(R.id.flCandidateProfileSetupFrameContainer,new CandidateProfileSetupStep2Fragment()).addToBackStack(null).commit();

//            startActivity(new Intent(getActivity(), CandidateHomeActivity.class));

        });
        
        return view;
    }
    
}