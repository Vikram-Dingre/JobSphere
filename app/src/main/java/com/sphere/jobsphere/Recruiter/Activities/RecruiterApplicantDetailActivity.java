package com.sphere.jobsphere.Recruiter.Activities;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterApplicantsModel;

public class RecruiterApplicantDetailActivity extends AppCompatActivity {
    String[] item = {"Pending", "Rejected", "Accepted"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    String jobId, applicantId, currentUid, status = "";

    ImageView ivApplicantActivityApplicantProfilePhoto;
    TextView tvApplicantActivityApplicantName, tvApplicantActivityApplicantSpecification;
    EditText etRecruiterApplicantDetailsMessage;
    AppCompatButton acbApplicantActivityApplicantSeeResume, acbRecruiterApplicantDetailsSendButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    RecruiterApplicantsModel applicant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_applicant_detail);

        currentUid = auth.getCurrentUser().getUid();

        jobId = getIntent().getStringExtra("jobId");
        applicantId = getIntent().getStringExtra("applicantId");

        autoCompleteTextView = findViewById(R.id.actRecruiterApplicantSetStatus);
        ivApplicantActivityApplicantProfilePhoto = findViewById(R.id.ivApplicantActivityApplicantProfilePhoto);
        tvApplicantActivityApplicantName = findViewById(R.id.tvApplicantActivityApplicantName);
        tvApplicantActivityApplicantSpecification = findViewById(R.id.tvApplicantActivityApplicantSpecification);
        etRecruiterApplicantDetailsMessage = findViewById(R.id.etRecruiterApplicantDetailsMessage);
        acbApplicantActivityApplicantSeeResume = findViewById(R.id.acbApplicantActivityApplicantSeeResume);
        acbRecruiterApplicantDetailsSendButton = findViewById(R.id.acbRecruiterApplicantDetailsSendButton);


        adapterItems = new ArrayAdapter<>(this, R.layout.recruiter_applicant_status_items, item);
        autoCompleteTextView.setAdapter(adapterItems);

        fetchApplicantInfo();

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                status = parent.getItemAtPosition(position).toString();
//                makeText(RecruiterApplicantDetailActivity.this, "Item" + status, LENGTH_SHORT).show();
            }
        });

        acbRecruiterApplicantDetailsSendButton.setOnClickListener(v -> {
            String msg = etRecruiterApplicantDetailsMessage.getText().toString();

//            etRecruiterApplicantDetailsMessage.setText("");

            db.collection("CandidateApplications")
                    .document(applicant.getApplicantId())
                    .collection("applications")
                    .document(applicant.getCandidateApplicationId())
                    .update("recruiterMessage", msg, "applicationStatus", status)
                    .addOnSuccessListener(aVoid -> {
                        db.collection("recruiterJobApplicants")
                                .document(jobId)
                                .collection("jobApplicants")
                                .document(applicantId)
                                .update("message", msg, "status", status);
                        makeText(this, "Information Sent to Applicant.", LENGTH_SHORT).show();

                    });


        });

    }

    private void fetchApplicantInfo() {
        db.collection("recruiterJobApplicants")
                .document(jobId)
                .collection("jobApplicants")
                .document(applicantId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    applicant = documentSnapshot.toObject(RecruiterApplicantsModel.class);
                    applicant.id = documentSnapshot.getId();

                    Glide.with(this)
                            .load(applicant.getApplicantProfilePhoto())
                            .into(ivApplicantActivityApplicantProfilePhoto);

                    tvApplicantActivityApplicantName.setText(applicant.getApplicantName());
                    tvApplicantActivityApplicantSpecification.setText(applicant.getApplicantEmail());
                    etRecruiterApplicantDetailsMessage.setText(applicant.getMessage());
//                    autoCompleteTextView.setSelection(applicant.getStatus().equalsIgnoreCase("Pending") ? 1 : applicant.getStatus().equals("Rejected") ? 2 : applicant.getStatus().equals("Accepted") ? 3 : 0);
                    status = applicant.getStatus();

                    if ("pending".equalsIgnoreCase(status)) {
                        autoCompleteTextView.setText("Pending", false);
                    } else if ("rejected".equalsIgnoreCase(status)) {
                        autoCompleteTextView.setText("Rejected", false);
                    } else if ("accepted".equalsIgnoreCase(status)) {
                        autoCompleteTextView.setText("Accepted", false);
                    } else {
                        autoCompleteTextView.setText("Select Status", false);
                    }

                });
    }
}