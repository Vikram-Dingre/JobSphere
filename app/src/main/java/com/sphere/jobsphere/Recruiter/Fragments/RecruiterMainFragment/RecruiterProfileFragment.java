package com.sphere.jobsphere.Recruiter.Fragments.RecruiterMainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileInfoActivities.RecruiterCompanyDetailsActivity;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileInfoActivities.RecruiterCompanyLocationInfoActivity;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileInfoActivities.RecruiterPersonalInfoActivity;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileUpdateActivity;


public class RecruiterProfileFragment extends Fragment {
    ImageView ivEditProfile, ivOpenPersonalInfo, ivOpenCompanyDetails, ivOpenCompanyLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruiter_profile, container, false);
        ivEditProfile = view.findViewById(R.id.ivRecruiterProfileEditProfile);
        ivOpenPersonalInfo = view.findViewById(R.id.ivRecruiterProfilePersonalInformation);
        ivOpenCompanyDetails = view.findViewById(R.id.ivRecruiterProfileCompanyDetails);
        ivOpenCompanyLocation = view.findViewById(R.id.ivRecruiterProfileCompanyLocation);

        ivEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RecruiterProfileUpdateActivity.class);
                startActivity(i);
            }
        });

        ivOpenPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RecruiterPersonalInfoActivity.class);
                startActivity(i);
            }
        });

        ivOpenCompanyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RecruiterCompanyDetailsActivity.class);
                startActivity(i);
            }
        });

        ivOpenCompanyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RecruiterCompanyLocationInfoActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}