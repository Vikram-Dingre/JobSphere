package com.sphere.jobsphere.Recruiter.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileSetupActivity;
import com.sphere.jobsphere.Recruiter.Models.RecruiterPersonalInfo;


public class RecruiterProfileStep1PersonalInfoFragment extends Fragment {

    TextInputEditText tieRecruiterProfileSetupStep1FullName,tieRecruiterProfileSetupStep1JobTitle,tieRecruiterProfileSetupStep1ProfilePhotoUrl,tieRecruiterProfileSetupStep1WorkEmail,tieRecruiterProfileSetupStep1WorkPhone;
    RecruiterProfileSetupActivity activity;
    AppCompatButton acbRecruiterProfileStep1Next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruiter_profile_step1_personal_info, container, false);

        acbRecruiterProfileStep1Next = view.findViewById(R.id.acbRecruiterProfileStep1Next);
        tieRecruiterProfileSetupStep1FullName = view.findViewById(R.id.tieRecruiterProfileSetupStep1FullName);
        tieRecruiterProfileSetupStep1JobTitle = view.findViewById(R.id.tieRecruiterProfileSetupStep1JobTitle);
        tieRecruiterProfileSetupStep1ProfilePhotoUrl = view.findViewById(R.id.tieRecruiterProfileSetupStep1ProfilePhotoUrl);
        tieRecruiterProfileSetupStep1WorkEmail = view.findViewById(R.id.tieRecruiterProfileSetupStep1WorkEmail);
        tieRecruiterProfileSetupStep1WorkPhone = view.findViewById(R.id.tieRecruiterProfileSetupStep1WorkPhone);

        activity = (RecruiterProfileSetupActivity) getActivity();

        acbRecruiterProfileStep1Next.setOnClickListener(v -> {

            String name = tieRecruiterProfileSetupStep1FullName.getText().toString();
            String title = tieRecruiterProfileSetupStep1JobTitle.getText().toString();
            String email = tieRecruiterProfileSetupStep1WorkEmail.getText().toString();
            String phone = tieRecruiterProfileSetupStep1WorkPhone.getText().toString();

           if (!name.isEmpty() && !title.isEmpty() && !email.isEmpty() && !phone.isEmpty()){
               RecruiterPersonalInfo pi = new RecruiterPersonalInfo();

               pi.setFullName(name);
               pi.setJobTitle(title);
               pi.setWorkEmail(email);
               pi.setWorkPhone(phone);

               activity.loadFragment(new RecruiterProfileStep2CompanyDetailsFragment());
               activity.recruiterProfile.setPersonalInfo(pi);

           }else{
               Toast.makeText(activity, "Fill the Reruired(*) Fields.", Toast.LENGTH_SHORT).show();
           }
        });



        return view;
    }
}