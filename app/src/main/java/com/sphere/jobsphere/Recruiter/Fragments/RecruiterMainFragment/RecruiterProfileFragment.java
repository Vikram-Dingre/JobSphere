package com.sphere.jobsphere.Recruiter.Fragments.RecruiterMainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileUpdateActivity;


public class RecruiterProfileFragment extends Fragment {
    ImageView ivEditProfile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_recruiter_profile, container, false);
        ivEditProfile=view.findViewById(R.id.ivRecruiterProfileEditProfile);
        ivEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), RecruiterProfileUpdateActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}