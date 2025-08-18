package com.sphere.jobsphere.Candidate.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.sphere.jobsphere.Candidate.Fragments.CandidateHomeFragments.CandidateApplicationsFragment;
import com.sphere.jobsphere.Candidate.Fragments.CandidateHomeFragments.CandidateHomeFragment;
import com.sphere.jobsphere.Candidate.Fragments.CandidateHomeFragments.CandidateJobSearchFragment;
import com.sphere.jobsphere.Candidate.Fragments.CandidateHomeFragments.CandidateJobsFragment;
import com.sphere.jobsphere.Candidate.Fragments.CandidateHomeFragments.CandidateProfileFragment;
import com.sphere.jobsphere.R;

public class CandidateHomeActivity extends AppCompatActivity {


    FirebaseAuth auth = FirebaseAuth.getInstance();
    AppCompatButton logout;
    String currentUid;

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    BottomNavigationView bnvCandidateHomeActivityBottomMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_home);

        bnvCandidateHomeActivityBottomMenu = findViewById(R.id.bnvCandidateHomeActivityBottomMenu);

        getSupportFragmentManager().beginTransaction().replace(R.id.flCandidateHomeActivityFrameContainer, new CandidateHomeFragment()).commit();

        bnvCandidateHomeActivityBottomMenu.setOnItemSelectedListener((item -> {
            Fragment fragment;

            if (item.getItemId() == R.id.candidate_home_bottom_menu_home) {
                fragment = new CandidateHomeFragment();
            } else if (item.getItemId() == R.id.candidate_home_bottom_menu_jobs) {
                fragment = new CandidateJobsFragment();
            } else if (item.getItemId() == R.id.candidate_home_bottom_menu_applications) {
                fragment = new CandidateApplicationsFragment();
            } else if (item.getItemId() == R.id.candidate_home_bottom_menu_search_jobs) {
                fragment = new CandidateJobSearchFragment();
            } else {
                fragment = new CandidateProfileFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.flCandidateHomeActivityFrameContainer, fragment).commit();


            return true;
        }));
    }
}


//pref = getSharedPreferences("settings",MODE_PRIVATE);
//        editor = pref.edit();
//
//        currentUid = auth.getCurrentUser().getUid();
//        logout = findViewById(R.id.logout);
//
//        logout.setOnClickListener(v -> {
//            auth.signOut();
//            editor.putBoolean("isLoggedIn", false).apply();
//            //editor.remove("isLoggedIn");
//            startActivity(new Intent(this, CommonLoginActivity.class));
//        });