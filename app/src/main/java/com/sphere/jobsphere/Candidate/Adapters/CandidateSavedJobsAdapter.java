package com.sphere.jobsphere.Candidate.Adapters;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Activities.CandidateJobDetailsActivity;
import com.sphere.jobsphere.Candidate.Models.CandidateJobModel;
import com.sphere.jobsphere.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandidateSavedJobsAdapter extends RecyclerView.Adapter<CandidateSavedJobsAdapter.MyViewHolder> {
    List<CandidateJobModel> savedJobs;
    Context context;

    public CandidateSavedJobsAdapter(Context context, List<CandidateJobModel> savedJobs) {
        this.context = context;
        this.savedJobs = savedJobs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_saved_job_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CandidateJobModel job = savedJobs.get(position);

        Glide.with(context)
                .load(job.getCompanyLogo())
                .into(holder.companyLogo);

        holder.jobName.setText(job.getTitle());
        holder.companyName.setText(job.getCompanyName());
        holder.jobSalary.setText(job.getSalary());
        holder.jobLocation.setText(job.getLocation());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CandidateJobDetailsActivity.class);
            intent.putExtra("jobId", job.getId());
            context.startActivity(intent);

        });

        FirebaseFirestore.getInstance().collection("candidateSavedJobs")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("savedJobs")
                .whereEqualTo("jobId", job.getId())
                .get()
                .addOnSuccessListener(snapshots -> {
                    if (snapshots.size() != 0) {
                        Glide.with(context)
                                .load(R.drawable.savedjob)
                                .into(holder.saveJob);
                    } else {
                        Glide.with(context)
                                .load(R.drawable.bookmark)
                                .into(holder.saveJob);
                    }
                });

        holder.saveJob.setOnClickListener(v -> {

            FirebaseFirestore.getInstance().collection("candidateSavedJobs")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("savedJobs")
                    .whereEqualTo("jobId", job.getId())
                    .get()
                    .addOnSuccessListener(snapshots -> {
                        if (snapshots.size() != 0) {
                            for (DocumentSnapshot doc : snapshots.getDocuments()) {
                                doc.getReference().delete().addOnSuccessListener(aVoid -> {
                                    Glide.with(context)
                                            .load(R.drawable.bookmark)
                                            .into(holder.saveJob);
                                    makeText(context, "Unsaved", LENGTH_SHORT).show();
                                });
                            }
                        } else {
                            Map<String, Object> savedJob = new HashMap<>();
                            savedJob.put("jobId", job.getId());
                            FirebaseFirestore.getInstance().collection("candidateSavedJobs")
                                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .collection("savedJobs")
                                    .add(savedJob)
                                    .addOnSuccessListener(aVoid -> {
                                        Glide.with(context)
                                                .load(R.drawable.savedjob)
                                                .into(holder.saveJob);
                                        makeText(context, "Saved", LENGTH_SHORT).show();
                                    });
                        }
                    });

//            FirebaseFirestore.getInstance().collection("candidateSavedJobs")
//                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                    .collection("savedJobs")
//                    .get()
//                    .addOnSuccessListener(snapshots -> {
//                        int a = 0;
//                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
//
//                            if (doc.getString("jobId").equals(job.getId())) {
//                                Glide.with(context)
//                                        .load(R.drawable.bookmark)
//                                        .into(holder.saveJob);
//
//                                FirebaseFirestore.getInstance().collection("candidateSavedJobs")
//                                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                        .collection("savedJobs")
//                                        .whereEqualTo("jobId",job.getId())
//                                        .get()
//                                        .addOnSuccessListener(query ->{
//                                            for (DocumentSnapshot saveJobDoc : query.getDocuments()){
//                                                saveJobDoc.getReference().delete().addOnSuccessListener(aVoid ->{
//                                                    makeText(context, "Unsaved Successfully.", LENGTH_SHORT).show();
//                                                });
//                                            }
//                                        });
//                                a = 1;
//                                break;
//                            }
//                        }
//                        if (a == 0) {
//                            Glide.with(context)
//                                    .load(R.drawable.savedjob)
//                                    .into(holder.saveJob);
//
//                            Map<String, Object> savedJob = new HashMap<>();
//                            savedJob.put("jobId", job.getId());
//                            FirebaseFirestore.getInstance().collection("candidateSavedJobs")
//                                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .collection("savedJobs")
//                                    .add(savedJob);
//                            makeText(context, "Saved Successfully.", LENGTH_SHORT).show();
//                        }
//                    });


        });


    }

    @Override
    public int getItemCount() {
        return savedJobs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView companyLogo, saveJob;
        TextView jobName, companyName, jobSalary, jobLocation;

        public MyViewHolder(View itemView) {
            super(itemView);
            companyLogo = itemView.findViewById(R.id.ivCandidateSavedJobCompanyLogo);
            jobName = itemView.findViewById(R.id.tvCandidateSavedJobJobName);
            companyName = itemView.findViewById(R.id.tvCandidateSavedJobCompanyName);
            jobSalary = itemView.findViewById(R.id.tvCandidateSavedJobSalary);
            saveJob = itemView.findViewById(R.id.ivCandidateSavedJobSaveImage);
            jobLocation = itemView.findViewById(R.id.tvCandidateSavedJobLocation);
        }
    }

}
