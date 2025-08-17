package com.sphere.jobsphere.Candidate.Fragments.CandidateHomeFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sphere.jobsphere.R;


public class CandidateApplicationsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_candidate_applications, container, false);


        return view;
    }
}