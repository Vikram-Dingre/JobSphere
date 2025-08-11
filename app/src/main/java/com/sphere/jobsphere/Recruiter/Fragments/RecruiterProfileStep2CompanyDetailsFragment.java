package com.sphere.jobsphere.Recruiter.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileSetupActivity;

public class RecruiterProfileStep2CompanyDetailsFragment extends Fragment {

AppCompatButton acbRecruiterProfileStep2Next;
RecruiterProfileSetupActivity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_recruiter_profile_step2_company_details, container, false);

        activity = (RecruiterProfileSetupActivity) getActivity();
        acbRecruiterProfileStep2Next = view.findViewById(R.id.acbRecruiterProfileStep2Next);

        acbRecruiterProfileStep2Next.setOnClickListener(v -> {
            activity.loadFragment(new RecruiterProfileStep3CompanyLocationFragment());
        });
        return view;

    }
}