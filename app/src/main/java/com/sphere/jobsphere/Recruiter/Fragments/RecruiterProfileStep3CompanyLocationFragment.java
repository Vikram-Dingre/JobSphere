package com.sphere.jobsphere.Recruiter.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterHomeActivity;

public class RecruiterProfileStep3CompanyLocationFragment extends Fragment {
AppCompatButton acbRecruiterProfileStep3Next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruiter_profile_step3_company_location, container, false);

        acbRecruiterProfileStep3Next = view.findViewById(R.id.acbRecruiterProfileStep3Next);

        acbRecruiterProfileStep3Next.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Profile Completed!", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(()->{
                getActivity().startActivity(new Intent(getActivity(), RecruiterHomeActivity.class));
                getActivity().finish();
            },1000);
        });
        return view;
    }
}