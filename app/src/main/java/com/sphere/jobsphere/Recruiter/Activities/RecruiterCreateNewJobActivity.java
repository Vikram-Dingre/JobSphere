package com.sphere.jobsphere.Recruiter.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sphere.jobsphere.R;

public class RecruiterCreateNewJobActivity extends AppCompatActivity {

    String[] jobTypeItems = {"Full-Time", "Part-Time","Remote","Freelance"};
    String[] jobCategoryItems = {"IT", "Finance","Marketing","etc"};
    String[] experienceLevelItems = {"Fresher", "mid","Senior"};
    AutoCompleteTextView actJobType,actJobCategory,actExperienceLevel;
    ArrayAdapter<String> adapterJobTypeItems,adapterJobCategoryItems,adapterExperienceLevelItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_create_new_job);

        actJobType=findViewById(R.id.actRecruiterCreateNewJobJobType);
        actJobCategory=findViewById(R.id.actRecruiterCreateNewJobJobCategory);
        actExperienceLevel=findViewById(R.id.actRecruiterCreateNewJobExperienceLevel);

        adapterJobTypeItems=new ArrayAdapter<>(this,R.layout.recruiter_applicant_status_items,jobTypeItems);
        actJobType.setAdapter(adapterJobTypeItems);
        actJobType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=parent.getItemAtPosition(position).toString();
                Toast.makeText(RecruiterCreateNewJobActivity.this,"Selected job type:"+item,Toast.LENGTH_SHORT).show();
            }
        });

        adapterJobCategoryItems=new ArrayAdapter<>(this,R.layout.recruiter_applicant_status_items,jobCategoryItems);
        actJobCategory.setAdapter(adapterJobCategoryItems);
        actJobCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=parent.getItemAtPosition(position).toString();
                Toast.makeText(RecruiterCreateNewJobActivity.this,"Selected job category:"+item,Toast.LENGTH_SHORT).show();
            }
        });

        adapterExperienceLevelItems=new ArrayAdapter<>(this,R.layout.recruiter_applicant_status_items,experienceLevelItems);
        actExperienceLevel.setAdapter(adapterExperienceLevelItems);
        actExperienceLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=parent.getItemAtPosition(position).toString();
                Toast.makeText(RecruiterCreateNewJobActivity.this,"Selected Experience Level:"+item,Toast.LENGTH_SHORT).show();
            }
        });
    }
}