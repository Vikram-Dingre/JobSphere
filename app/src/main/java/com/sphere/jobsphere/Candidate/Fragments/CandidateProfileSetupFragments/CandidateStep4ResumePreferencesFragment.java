package com.sphere.jobsphere.Candidate.Fragments.CandidateProfileSetupFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.sphere.jobsphere.Candidate.Activities.CandidateProfileSetupActivity;
import com.sphere.jobsphere.Candidate.Models.CandidateProfileSetupModels.CandidateResumePreferences;
import com.sphere.jobsphere.R;

public class CandidateStep4ResumePreferencesFragment extends Fragment {
    EditText etResume, etPortfolio, etLinkedIn, etGithub, etNotice, etWorkAuth, etCareer;
    Button btnFinish;
    CandidateProfileSetupActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidate_step4_resume_preferences, container, false);

        activity = (CandidateProfileSetupActivity) getActivity();

        ProgressBar progressBar = activity.findViewById(R.id.stepProgressBar);
        progressBar.setProgress(100);


        etResume = view.findViewById(R.id.etResume);
        etPortfolio = view.findViewById(R.id.etPortfolio);
        etLinkedIn = view.findViewById(R.id.etLinkedIn);
        etGithub = view.findViewById(R.id.etGithub);
        etNotice = view.findViewById(R.id.etNotice);
        etWorkAuth = view.findViewById(R.id.etWorkAuth);
        etCareer = view.findViewById(R.id.etCareer);
        btnFinish = view.findViewById(R.id.btnFinish);

        btnFinish.setOnClickListener(v -> {
            CandidateResumePreferences rp = new CandidateResumePreferences();
            rp.setResumeUrl(etResume.getText().toString());
            rp.setPortfolioUrl(etPortfolio.getText().toString());
            rp.setLinkedinUrl(etLinkedIn.getText().toString());
            rp.setGithubUrl(etGithub.getText().toString());
            rp.setNoticePeriod(etNotice.getText().toString());
            rp.setWorkAuthorization(etWorkAuth.getText().toString());
            rp.setCareerObjective(etCareer.getText().toString());
            rp.setRelocation(true); // can be from checkbox

            activity.candidateProfile.setResumePreferences(rp);
//            activity.saveProfileToFirestore();

            Toast.makeText(activity, "This Step is Not Included.", Toast.LENGTH_SHORT).show();
        });


        return view;
    }
}