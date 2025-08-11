package com.sphere.jobsphere.Recruiter.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileSetupActivity;


public class RecruiterProfileStep1PersonalInfoFragment extends Fragment {
RecruiterProfileSetupActivity activity;
AppCompatButton acbRecruiterProfileStep1Next;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruiter_profile_step1_personal_info, container, false);

        acbRecruiterProfileStep1Next = view.findViewById(R.id.acbRecruiterProfileStep1Next);
        activity = (RecruiterProfileSetupActivity) getActivity();

        acbRecruiterProfileStep1Next.setOnClickListener(v -> {
            activity.loadFragment(new RecruiterProfileStep2CompanyDetailsFragment());
        });



        return view;
    }
}