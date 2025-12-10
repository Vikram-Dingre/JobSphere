package com.sphere.jobsphere.Recruiter.Fragments.RecruiterMainFragment;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.sphere.jobsphere.Common.Activities.CommonLoginActivity;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileInfoActivities.RecruiterCompanyDetailsActivity;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileInfoActivities.RecruiterCompanyLocationInfoActivity;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileInfoActivities.RecruiterPersonalInfoActivity;
import com.sphere.jobsphere.Recruiter.Activities.RecruiterProfileUpdateActivity;


public class RecruiterProfileFragment extends Fragment {
    ImageView ivEditProfile, ivOpenPersonalInfo, ivOpenCompanyDetails, ivOpenCompanyLocation;
    LinearLayout llRecruiterProfileLogout;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruiter_profile, container, false);
        ivEditProfile = view.findViewById(R.id.ivRecruiterProfileEditProfile);
        ivOpenPersonalInfo = view.findViewById(R.id.ivRecruiterProfilePersonalInformation);
        ivOpenCompanyDetails = view.findViewById(R.id.ivRecruiterProfileCompanyDetails);
        ivOpenCompanyLocation = view.findViewById(R.id.ivRecruiterProfileCompanyLocation);
        llRecruiterProfileLogout = view.findViewById(R.id.llRecruiterProfileLogout);

        pref = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        editor = pref.edit();

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

        llRecruiterProfileLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            editor.putBoolean("isLoggedIn", false).apply();
            makeText(getActivity(), "Logged out Successfully.", LENGTH_SHORT).show();
            new Handler().postDelayed(() -> {
                startActivity(new Intent(getActivity(), CommonLoginActivity.class));
                getActivity().finish();
            }, 2000);
        });
        return view;
    }
}