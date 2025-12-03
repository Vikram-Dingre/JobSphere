package com.sphere.jobsphere.Candidate.Adapters;

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
import com.sphere.jobsphere.Candidate.Activities.CandidateJobDetailsActivity;
import com.sphere.jobsphere.Candidate.Models.CandidateJobModel;
import com.sphere.jobsphere.R;

import java.util.List;

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


    }

    @Override
    public int getItemCount() {
        return savedJobs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView companyLogo;
        TextView jobName, companyName, jobSalary, jobLocation;

        public MyViewHolder(View itemView) {
            super(itemView);
            companyLogo = itemView.findViewById(R.id.ivCandidateSavedJobCompanyLogo);
            jobName = itemView.findViewById(R.id.tvCandidateSavedJobJobName);
            companyName = itemView.findViewById(R.id.tvCandidateSavedJobCompanyName);
            jobSalary = itemView.findViewById(R.id.tvCandidateSavedJobSalary);
            jobLocation = itemView.findViewById(R.id.tvCandidateSavedJobLocation);
        }
    }

}
