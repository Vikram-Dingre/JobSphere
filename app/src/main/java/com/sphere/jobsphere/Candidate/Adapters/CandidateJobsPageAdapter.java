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

public class CandidateJobsPageAdapter extends RecyclerView.Adapter<CandidateJobsPageAdapter.MyViewHolder> {

    List<CandidateJobModel> jobs;
    Context context;

    public CandidateJobsPageAdapter(List<CandidateJobModel> jobs, Context context) {
        this.jobs = jobs;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_jobs_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CandidateJobModel job = jobs.get(position);

        Glide.with(context)
                .load(job.getCompanyLogo())
                .into(holder.companyLogo);

        holder.jobTitle.setText(job.getTitle());
        holder.companyName.setText(job.getCompanyName());
        holder.jobLocation.setText(job.getLocation());
        holder.workingType.setText(job.getJobType());
        holder.experienceLevel.setText(job.getExperienceLevel());
        holder.minSalary.setText("₹" + job.getMinSalary());
        holder.maxSalary.setText("₹" + job.getMaxSalary());
        holder.vacancyCount.setText("5");
        holder.postedDate.setText(job.getPostedAt() + "");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CandidateJobDetailsActivity.class);
            intent.putExtra("jobId", job.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView companyLogo, saveJob;
        TextView jobTitle, companyName, jobLocation, workingType, experienceLevel, minSalary, maxSalary, vacancyCount, postedDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyLogo = itemView.findViewById(R.id.ivCandidateJobsCompanyLogo);
            saveJob = itemView.findViewById(R.id.ivCandidateJobsSaveJob);
            jobTitle = itemView.findViewById(R.id.tvCandidateJobsJobTitle);
            companyName = itemView.findViewById(R.id.tvCandidateJobsCompanyName);
            jobLocation = itemView.findViewById(R.id.tvCandidateJobsLocation);
            workingType = itemView.findViewById(R.id.tvCandidateJobsWorkingType);
            experienceLevel = itemView.findViewById(R.id.tvCandidateJobsExperience);
            minSalary = itemView.findViewById(R.id.tvCandidateJobsMinSalary);
            maxSalary = itemView.findViewById(R.id.tvCandidateJobsMaxSalary);
            vacancyCount = itemView.findViewById(R.id.tvCandidateJobsVacancyNumber);
            postedDate = itemView.findViewById(R.id.tvCandidateJobsPostedDate);

        }
    }

}
