package com.sphere.jobsphere.Candidate.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.sphere.jobsphere.Candidate.Activities.CandidateProfileSetupActivity;
import com.sphere.jobsphere.Candidate.Models.CandiateProfessionalDetails;
import com.sphere.jobsphere.R;

import java.util.Arrays;

public class CandidateStep2ProfessionalDetailsFragment extends Fragment {
    AppCompatButton acbCandidateProfileSetupStep2Next;
    EditText etCandidateProfileSetupStep2Email;

    EditText etTitle, etCompany, etExperience, etIndustry, etDepartment, etSkills, etSalary;
    Button btnNext;
    CandidateProfileSetupActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_candidate_step2_professional_details, container, false);

        activity = (CandidateProfileSetupActivity) getActivity();

        ProgressBar progressBar = activity.findViewById(R.id.stepProgressBar);
        progressBar.setProgress(50);

        etTitle = view.findViewById(R.id.etTitle);
        etCompany = view.findViewById(R.id.etCompany);
        etExperience = view.findViewById(R.id.etExperience);
        etIndustry = view.findViewById(R.id.etIndustry);
        etDepartment = view.findViewById(R.id.etDepartment);
        etSkills = view.findViewById(R.id.etSkills);
        etSalary = view.findViewById(R.id.etSalary);
        btnNext = view.findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            CandiateProfessionalDetails pd = new CandiateProfessionalDetails();
            pd.setJobTitle(etTitle.getText().toString());
            pd.setCurrentCompany(etCompany.getText().toString());
            pd.setExperience(etExperience.getText().toString());
            pd.setIndustry(etIndustry.getText().toString());
            pd.setDepartment(etDepartment.getText().toString());
            pd.setSkills(Arrays.asList(etSkills.getText().toString().split(",")));
            pd.setExpectedSalary(Double.parseDouble(etSalary.getText().toString()));

            activity.candidateProfile.setProfessionalDetails(pd);

            activity.loadFragment(new CandidateStep3EducationFragment());
        });



        /// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//        acbCandidateProfileSetupStep2Next = view.findViewById(R.id.acbCandidateProfileSetupStep2Next);
//        etCandidateProfileSetupStep2Email = view.findViewById(R.id.etCandidateProfileSetupStep2Email);
//
//        acbCandidateProfileSetupStep2Next.setOnClickListener(v -> {
//            String Email = etCandidateProfileSetupStep2Email.getText().toString();
//            CandidateProfileSetupActivity activity = (CandidateProfileSetupActivity) getActivity();
//            activity.candidateProfileSetupData.email = Email;
//
//
//            Toast.makeText(getActivity(), "Name : "+ activity.candidateProfileSetupData.fullName + "\n" + " Email : "+activity.candidateProfileSetupData.email , Toast.LENGTH_SHORT).show();
//
//            // Move to next step
//
////            getParentFragmentManager().beginTransaction().replace(R.id.flCandidateProfileSetupFrameContainer,new CandidateProfileSetupStep2Fragment()).addToBackStack(null).commit();
//
////            startActivity(new Intent(getActivity(), CandidateHomeActivity.class));
//
//        });
        
        return view;
    }
    
}