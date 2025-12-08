package com.sphere.jobsphere.Recruiter.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sphere.jobsphere.Candidate.Classes.CandidateJobFilterModel;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Fragments.RecruiterMainFragment.RecruiterApplicationsFragment;
import com.sphere.jobsphere.Recruiter.Fragments.RecruiterMainFragment.RecruiterChatFragment;
import com.sphere.jobsphere.Recruiter.Fragments.RecruiterMainFragment.RecruiterHomeFragment;
import com.sphere.jobsphere.Recruiter.Fragments.RecruiterMainFragment.RecruiterProfileFragment;

public class RecruiterMainActivity extends AppCompatActivity {

    public BottomNavigationView bnvRecruiterMainActivityBottomMenu;
    public CandidateJobFilterModel lastAppliedFilter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_main);

        bnvRecruiterMainActivityBottomMenu = findViewById(R.id.bnvRecruiterMainActivityBottomMenu);

        String openFragment = getIntent().getStringExtra("openFragment");

        if ("applications".equals(openFragment)) {
            bnvRecruiterMainActivityBottomMenu.setSelectedItemId(R.id.recruiter_main_bottom_menu_applications);
            getSupportFragmentManager().beginTransaction().replace(R.id.flRecruiterMainactivityFrameContainer, new RecruiterApplicationsFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.flRecruiterMainactivityFrameContainer, new RecruiterHomeFragment()).commit();
        }


        bnvRecruiterMainActivityBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                if (menuItem.getItemId() == R.id.recruiter_main_bottom_menu_home) {
                    fragment = new RecruiterHomeFragment();
                } else if (menuItem.getItemId() == R.id.recruiter_main_bottom_menu_applications) {
                    fragment = new RecruiterApplicationsFragment();
                } else if (menuItem.getItemId() == R.id.recruiter_main_bottom_menu_chats) {
                    fragment = new RecruiterChatFragment();
                } else {
                    fragment = new RecruiterProfileFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.flRecruiterMainactivityFrameContainer, fragment).commit();
                return true;
            }
        });
    }
}