package com.sphere.jobsphere.Candidate.Fragments.CandidateProfileSetupFragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.sphere.jobsphere.Candidate.Activities.CandidateProfileSetupActivity;
import com.sphere.jobsphere.Candidate.Models.CandidateProfileSetupModels.CandidatePersonalInfo;
import com.sphere.jobsphere.R;

import java.util.Calendar;

public class CandidateStep1PersonalInfoFragment extends Fragment {

    AppCompatButton acbCandidateProfileSetupStep1Next;
    TextInputEditText tieCandidateProfileSetupStep1Name, tieCandidateProfileSetupStep1ProfileUrl, tieCandidateProfileSetupStep1Dob, tieCandidateProfileSetupStep1Email, tieCandidateProfileSetupStep1Phone, tieCandidateProfileSetupStep1CurrentLocation;

    CandidateProfileSetupActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidate_step1_personal_info, container, false);

        activity = (CandidateProfileSetupActivity) getActivity();

        ProgressBar progressBar = activity.findViewById(R.id.stepProgressBar);
        progressBar.setProgress(33);

        tieCandidateProfileSetupStep1Name = view.findViewById(R.id.tieCandidateProfileSetupStep1Name);
        tieCandidateProfileSetupStep1ProfileUrl = view.findViewById(R.id.tieCandidateProfileSetupStep1ProfileUrl);
        tieCandidateProfileSetupStep1Dob = view.findViewById(R.id.tieCandidateProfileSetupStep1Dob);
        tieCandidateProfileSetupStep1Email = view.findViewById(R.id.tieCandidateProfileSetupStep1Email);
        tieCandidateProfileSetupStep1Phone = view.findViewById(R.id.tieCandidateProfileSetupStep1Phone);
        tieCandidateProfileSetupStep1CurrentLocation = view.findViewById(R.id.tieCandidateProfileSetupStep1CurrentLocation);
        acbCandidateProfileSetupStep1Next = view.findViewById(R.id.acbCandidateProfileSetupStep1Next);


        tieCandidateProfileSetupStep1Dob.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(), // or YourActivity.this
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        // Month is 0-based, so +1
                        String dob = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        tieCandidateProfileSetupStep1Dob.setText(dob);

                        // Store in ViewModel / object for database
//                        profileSetupViewModel.setDob(selectedYear, selectedMonth + 1, selectedDay);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });


        acbCandidateProfileSetupStep1Next.setOnClickListener(v -> {
            String name = tieCandidateProfileSetupStep1Name.getText().toString();
            String email = tieCandidateProfileSetupStep1Email.getText().toString();
            String phone = tieCandidateProfileSetupStep1Phone.getText().toString();

            if (!name.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
                CandidatePersonalInfo pd = new CandidatePersonalInfo();
                pd.setFullName(name);
                pd.setProfilePhotoUrl(tieCandidateProfileSetupStep1ProfileUrl.getText().toString());
                pd.setDob(tieCandidateProfileSetupStep1Dob.getText().toString());
                pd.setEmail(email);
                pd.setPhone(phone);
                pd.setCurrentLocation(tieCandidateProfileSetupStep1CurrentLocation.getText().toString());
                activity.loadFragment(new CandidateStep2ProfessionalDetailsFragment());
                activity.candidateProfile.setPersonalInfo(pd);
            } else {
                Toast.makeText(getActivity(), "Fill All Required(*) Fields.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}