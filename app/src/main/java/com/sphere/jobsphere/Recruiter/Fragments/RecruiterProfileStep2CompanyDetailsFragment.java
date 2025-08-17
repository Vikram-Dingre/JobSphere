package com.sphere.jobsphere.Recruiter.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileSetupActivity;
import com.sphere.jobsphere.Recruiter.Models.RecruiterCompanyDetails;

public class RecruiterProfileStep2CompanyDetailsFragment extends Fragment {

    AppCompatButton acbRecruiterProfileStep2Next;
    RecruiterProfileSetupActivity activity;
    TextInputEditText tieRecruiterProfileSetupStep2CompanyName, tieRecruiterProfileSetupStep2LogoUrl, tieRecruiterProfileSetupStep2Industry, tieRecruiterProfileSetupStep2Size, tieRecruiterProfileSetupStep2Website;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruiter_profile_step2_company_details, container, false);

        activity = (RecruiterProfileSetupActivity) getActivity();

        acbRecruiterProfileStep2Next = view.findViewById(R.id.acbRecruiterProfileStep2Next);
        tieRecruiterProfileSetupStep2CompanyName = view.findViewById(R.id.tieRecruiterProfileSetupStep2CompanyName);
        tieRecruiterProfileSetupStep2LogoUrl = view.findViewById(R.id.tieRecruiterProfileSetupStep2LogoUrl);
        tieRecruiterProfileSetupStep2Industry = view.findViewById(R.id.tieRecruiterProfileSetupStep2Industry);
        tieRecruiterProfileSetupStep2Size = view.findViewById(R.id.tieRecruiterProfileSetupStep2Size);
        tieRecruiterProfileSetupStep2Website = view.findViewById(R.id.tieRecruiterProfileSetupStep2Website);


        acbRecruiterProfileStep2Next.setOnClickListener(v -> {

            String name = tieRecruiterProfileSetupStep2CompanyName.getText().toString();
            String logo = tieRecruiterProfileSetupStep2LogoUrl.getText().toString();
            String industry = tieRecruiterProfileSetupStep2Industry.getText().toString();
            String size = tieRecruiterProfileSetupStep2Size.getText().toString();
            String website = tieRecruiterProfileSetupStep2Website.getText().toString();

            if (!name.isEmpty() && !industry.isEmpty() && !size.isEmpty()) {
                RecruiterCompanyDetails cd = new RecruiterCompanyDetails();
                cd.setCompanyName(name);
                cd.setLogoUrl(logo);
                cd.setIndustry(industry);
                cd.setSize(size);
                cd.setWebsite(website);

                activity.recruiterProfile.setCompanyDetails(cd);
                activity.loadFragment(new RecruiterProfileStep3CompanyLocationFragment());
            } else {
                Toast.makeText(activity, "Fill All Required(*) Fields.", Toast.LENGTH_SHORT).show();
            }


        });
        return view;

    }
}