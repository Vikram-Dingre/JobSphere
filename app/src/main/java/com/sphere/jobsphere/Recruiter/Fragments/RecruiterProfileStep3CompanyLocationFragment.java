package com.sphere.jobsphere.Recruiter.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterHomeActivity;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileSetupActivity;
import com.sphere.jobsphere.Recruiter.Models.RecruiterCompanyLocation;

public class RecruiterProfileStep3CompanyLocationFragment extends Fragment {
    AppCompatButton acbRecruiterProfileStep3Next;
    TextInputEditText tieRecruiterProfileSetupStep3City, tieRecruiterProfileSetupStep3State, tieRecruiterProfileSetupStep3Country, tieRecruiterProfileSetupStep3ZipCode, tieRecruiterProfileSetupStep3Address, tieRecruiterProfileSetupStep3About;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    RecruiterProfileSetupActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruiter_profile_step3_company_location, container, false);

        activity = (RecruiterProfileSetupActivity) getActivity();
        pref = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        editor = pref.edit();
        acbRecruiterProfileStep3Next = view.findViewById(R.id.acbRecruiterProfileStep3Next);
        tieRecruiterProfileSetupStep3City = view.findViewById(R.id.tieRecruiterProfileSetupStep3City);
        tieRecruiterProfileSetupStep3State = view.findViewById(R.id.tieRecruiterProfileSetupStep3State);
        tieRecruiterProfileSetupStep3Country = view.findViewById(R.id.tieRecruiterProfileSetupStep3Country);
        tieRecruiterProfileSetupStep3ZipCode = view.findViewById(R.id.tieRecruiterProfileSetupStep3ZipCode);
        tieRecruiterProfileSetupStep3Address = view.findViewById(R.id.tieRecruiterProfileSetupStep3Address);
        tieRecruiterProfileSetupStep3About = view.findViewById(R.id.tieRecruiterProfileSetupStep3About);

        acbRecruiterProfileStep3Next.setOnClickListener(v -> {
            String city = tieRecruiterProfileSetupStep3City.getText().toString();
            String state = tieRecruiterProfileSetupStep3State.getText().toString();
            String country = tieRecruiterProfileSetupStep3Country.getText().toString();
            String zipCode = tieRecruiterProfileSetupStep3ZipCode.getText().toString();
            String addr = tieRecruiterProfileSetupStep3Address.getText().toString();
            String about = tieRecruiterProfileSetupStep3About.getText().toString();

            if (!city.isEmpty() && !state.isEmpty() && !country.isEmpty() && !zipCode.isEmpty() && !addr.isEmpty() && !about.isEmpty()) {
                RecruiterCompanyLocation cl = new RecruiterCompanyLocation();
                cl.setCity(city);
                cl.setState(state);
                cl.setCountry(country);
                cl.setZipCode(zipCode);
                cl.setAddress(addr);
                cl.setAbout(about);

                activity.recruiterProfile.setCompanyLocation(cl);
                activity.saveProfileToFirestore();
                editor.putBoolean("isProfileSetupCompleted", true).apply();
                new Handler().postDelayed(() -> {
                    activity.startActivity(new Intent(getActivity(), RecruiterHomeActivity.class));
                    activity.finish();
                }, 1000);
            } else {
                Toast.makeText(activity, "Fill All Required(*) Fields.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

