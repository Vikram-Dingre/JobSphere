package com.sphere.jobsphere.Candidate.Fragments.CandidateProfileSetupFragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.sphere.jobsphere.Candidate.Activities.CandidateHomeActivity;
import com.sphere.jobsphere.Candidate.Activities.CandidateProfileSetupActivity;
import com.sphere.jobsphere.Candidate.Models.CandidateProfileSetupModels.CandidateEducation;
import com.sphere.jobsphere.R;

import java.util.ArrayList;
import java.util.List;


public class CandidateStep3EducationFragment extends Fragment {

    TextInputEditText tieCandidateProfileSetupStep3Qualifications, tieCandidateProfileSetupStep3Specialization, tieCandidateProfileSetupStep3University, tieCandidateProfileSetupStep3GraduationYear;
    AppCompatButton acbCandidateProfileSetupStep3Next;
    CandidateProfileSetupActivity activity;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidate_step3_education, container, false);

        pref = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        editor = pref.edit();

        activity = (CandidateProfileSetupActivity) getActivity();
        ProgressBar progressBar = activity.findViewById(R.id.stepProgressBar);
        progressBar.setProgress(100);

        tieCandidateProfileSetupStep3Qualifications = view.findViewById(R.id.tieCandidateProfileSetupStep3Qualifications);
        tieCandidateProfileSetupStep3Specialization = view.findViewById(R.id.tieCandidateProfileSetupStep3Specialization);
        tieCandidateProfileSetupStep3University = view.findViewById(R.id.tieCandidateProfileSetupStep3University);
        tieCandidateProfileSetupStep3GraduationYear = view.findViewById(R.id.tieCandidateProfileSetupStep3GraduationYear);
        acbCandidateProfileSetupStep3Next = view.findViewById(R.id.acbCandidateProfileSetupStep3Next);

        acbCandidateProfileSetupStep3Next.setOnClickListener(v -> {

            String qualification = tieCandidateProfileSetupStep3Qualifications.getText().toString();
            String university = tieCandidateProfileSetupStep3University.getText().toString();
            String graduationYear = tieCandidateProfileSetupStep3GraduationYear.getText().toString();

            if (!qualification.isEmpty() && !university.isEmpty() && !graduationYear.isEmpty()) {
                CandidateEducation edu = new CandidateEducation();
                edu.setQualification(qualification);
                edu.setSpecialization(tieCandidateProfileSetupStep3Specialization.getText().toString());
                edu.setUniversity(university);
                edu.setGraduationYear(Integer.parseInt(graduationYear));

                List<CandidateEducation> eduList = new ArrayList<>();
                eduList.add(edu);

                activity.candidateProfile.setEducation(eduList);
                activity.saveProfileToFirestore();

                editor.putBoolean("isProfileSetupCompleted", true).apply();
                new Handler().postDelayed(() -> {
                    getActivity().startActivity(new Intent(getActivity(), CandidateHomeActivity.class));
                    getActivity().finish();
                }, 1000);
            } else {
                Toast.makeText(getActivity(), "Fill the Required(*) Fields.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

