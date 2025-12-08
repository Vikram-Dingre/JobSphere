package com.sphere.jobsphere.Recruiter.Activities;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Models.RecruiterProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecruiterCreateNewJobActivity extends AppCompatActivity {

    String[] jobTypeItems = {"Full-Time", "Part-Time", "Internship", "On-Site", "Hybrid", "Remote", "Freelance"};
    String[] jobCategoryItems = {"Select Category", "IT & Software", "AI & ML", "UI/UX", "Web Developement", "App Developement", "Data Science", "Human Resource (HR)", "Sales & Marketing"};
    String[] experienceLevelItems = {"Select Experience Level", "Entry", "Mid", "Senior", "Manager", "Sr. Manager"};

    String selectedCategory = "", selectedExperienceLevel = "", selectedJobType = "";

    AutoCompleteTextView actJobType, actJobCategory, actExperienceLevel;
    ArrayAdapter<String> adapterJobTypeItems, adapterJobCategoryItems, adapterExperienceLevelItems;

    EditText tieRecruiterCreateNewJobCompanyLocation, tieRecruiterCreateNewJobMinimumSalary, tieRecruiterCreateNewJobMaximumSalary, tieRecruiterCreateNewJobEducationRequired;


    ChipGroup cgRecruiterCreateNewJobSkillList, cgRecruiterCreateNewJobJobTypes;
    LinearLayout llRecruiterCreateJobAddSkillButton;
    EditText etRecruiterCreateJobAddSkills, tieRecruiterCreateNewJobTitle, tieRecruiterCreateNewJobDescription, tieRecruiterCreateNewJobCompanyName, tieRecruiterCreateNewJobSalary;

    AppCompatButton acbRecruiterCreateJob;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_create_new_job);

        currentUid = auth.getCurrentUser().getUid();

//        actJobType = findViewById(R.id.actRecruiterCreateNewJobJobType);
        actJobCategory = findViewById(R.id.actRecruiterCreateNewJobJobCategory);
        actExperienceLevel = findViewById(R.id.actRecruiterCreateNewJobExperienceLevel);
        tieRecruiterCreateNewJobTitle = findViewById(R.id.tieRecruiterCreateNewJobTitle);
        tieRecruiterCreateNewJobDescription = findViewById(R.id.tieRecruiterCreateNewJobDescription);
        tieRecruiterCreateNewJobCompanyName = findViewById(R.id.tieRecruiterCreateNewJobCompanyName);
        tieRecruiterCreateNewJobCompanyLocation = findViewById(R.id.tieRecruiterCreateNewJobCompanyLocation);
        tieRecruiterCreateNewJobMinimumSalary = findViewById(R.id.tieRecruiterCreateNewJobMinimumSalary);
        tieRecruiterCreateNewJobMaximumSalary = findViewById(R.id.tieRecruiterCreateNewJobMaximumSalary);
        tieRecruiterCreateNewJobEducationRequired = findViewById(R.id.tieRecruiterCreateNewJobEducationRequired);
        acbRecruiterCreateJob = findViewById(R.id.acbRecruiterCreateJob);
        llRecruiterCreateJobAddSkillButton = findViewById(R.id.llRecruiterCreateJobAddSkillButton);
        etRecruiterCreateJobAddSkills = findViewById(R.id.etRecruiterCreateJobAddSkills);
        cgRecruiterCreateNewJobSkillList = findViewById(R.id.cgRecruiterCreateNewJobSkillList);
        cgRecruiterCreateNewJobJobTypes = findViewById(R.id.cgRecruiterCreateNewJobJobTypes);
        tieRecruiterCreateNewJobSalary = findViewById(R.id.tieRecruiterCreateNewJobSalary);


        actJobCategory.setText(jobCategoryItems[0]);
        actExperienceLevel.setText(experienceLevelItems[0]);


        llRecruiterCreateJobAddSkillButton.setOnClickListener(v -> {
            String skill = etRecruiterCreateJobAddSkills.getText().toString();

            if (skill.isEmpty()) {
                makeText(this, "Enter Skill Name First...", LENGTH_SHORT).show();
                return;
            }

            Chip chip = new Chip(this);
            chip.setText(skill);

            // Style the chip
            chip.setChipBackgroundColorResource(R.color.lightPurple);
            chip.setTextColor(ContextCompat.getColor(this, R.color.black));
            chip.setClickable(false);
            chip.setCheckable(false);


            cgRecruiterCreateNewJobSkillList.addView(chip);

            etRecruiterCreateJobAddSkills.setText("");

        });


//        adapterJobTypeItems = new ArrayAdapter<>(this, R.layout.recruiter_applicant_status_items, jobTypeItems);
//        actJobType.setAdapter(adapterJobTypeItems);
//
//        actJobType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selectedJobType = parent.getItemAtPosition(position).toString();
//            }
//        });

        adapterJobCategoryItems = new ArrayAdapter<>(this, R.layout.recruiter_applicant_status_items, jobCategoryItems);
        actJobCategory.setAdapter(adapterJobCategoryItems);
        actJobCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
            }
        });

        adapterExperienceLevelItems = new ArrayAdapter<>(this, R.layout.recruiter_applicant_status_items, experienceLevelItems);
        actExperienceLevel.setAdapter(adapterExperienceLevelItems);
        actExperienceLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedExperienceLevel = parent.getItemAtPosition(position).toString();
            }
        });

        acbRecruiterCreateJob.setOnClickListener(v -> {

            List<String> skills = new ArrayList<>();
            List<String> jobTypes = new ArrayList<>();

            int skillsCount = cgRecruiterCreateNewJobSkillList.getChildCount();
            int jobTypesCount = cgRecruiterCreateNewJobJobTypes.getChildCount();

            for (int i = 0; i < skillsCount; i++) {
                Chip chip = (Chip) cgRecruiterCreateNewJobSkillList.getChildAt(i);
                skills.add(chip.getText().toString());
            }

            for (int j = 0; j < jobTypesCount; j++) {
                Chip chip = (Chip) cgRecruiterCreateNewJobJobTypes.getChildAt(j);
                if (chip.isChecked()) {
                    jobTypes.add(chip.getText().toString());
                }
            }

            db.collection("recruiters")
                    .document(currentUid)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {

                        RecruiterProfile profile = documentSnapshot.toObject(RecruiterProfile.class);

                        if (profile == null) {
                            return;
                        }

                        Map<String, Object> job = new HashMap<>();
                        job.put("title", tieRecruiterCreateNewJobTitle.getText().toString());
                        job.put("description", tieRecruiterCreateNewJobDescription.getText().toString());
                        job.put("companyName", tieRecruiterCreateNewJobCompanyName.getText().toString());
                        job.put("companyLogo", "");
                        job.put("location", tieRecruiterCreateNewJobCompanyLocation.getText().toString());
                        job.put("jobType", jobTypes.get(0));
                        job.put("jobTypes", jobTypes);
                        job.put("category", selectedCategory);
                        job.put("skillsRequired", "");
                        job.put("skillsList", skills);
                        job.put("salary", tieRecruiterCreateNewJobSalary.getText().toString());
                        job.put("minSalary", Double.parseDouble(tieRecruiterCreateNewJobMinimumSalary.getText().toString()));
                        job.put("maxSalary", Double.parseDouble(tieRecruiterCreateNewJobMaximumSalary.getText().toString()));
                        job.put("experienceLevel", selectedExperienceLevel);
                        job.put("education", tieRecruiterCreateNewJobEducationRequired.getText().toString());
                        job.put("postedAt", 11233434);
                        job.put("deadline", 232432543);
                        job.put("recruiterId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        job.put("recruiterName", profile.getPersonalInfo().getFullName());
                        job.put("recruiterEmail", profile.getPersonalInfo().getWorkEmail());
                        job.put("applicantsCount", 0);
                        job.put("applicants", new ArrayList<>());

                        db.collection("jobs")
                                .add(job)
                                .addOnSuccessListener(aVoid -> {
                                    makeText(this, "Job Created SuccessFully.", LENGTH_SHORT).show();

//                                    Map<String,Object> recruiterPostedJob = new HashMap<>();
//
//                                    recruiterPostedJob.put("JobId")
//
//                                    db.collection("recruiterPostedJobs")
//                                            .document(currentUid)
//                                            .collection("postedJobs")
//                                            .add()

                                    Intent intent = new Intent(this, RecruiterMainActivity.class);
                                    intent.putExtra("openFragment", "applications");
                                    startActivity(intent);
                                });
                    });


        });

    }
}