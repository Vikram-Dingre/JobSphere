package com.sphere.jobsphere.Recruiter.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sphere.jobsphere.R;

public class RecruiterApplicantDetailActivity extends AppCompatActivity {
    String[] item = {"shivam", "hariom"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_applicant_detail);
        autoCompleteTextView = findViewById(R.id.actRecruiterApplicantSetStatus);
        adapterItems = new ArrayAdapter<>(this, R.layout.recruiter_applicant_status_items, item);
        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(RecruiterApplicantDetailActivity.this, "Item" + item, Toast.LENGTH_SHORT).show();
            }
        });

    }
}