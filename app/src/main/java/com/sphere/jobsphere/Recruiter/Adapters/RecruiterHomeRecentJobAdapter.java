package com.sphere.jobsphere.Recruiter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterJobModel;

import java.util.List;

public class RecruiterHomeRecentJobAdapter extends RecyclerView.Adapter<RecruiterHomeRecentJobAdapter.MyViewHolder> {

    List<RecruiterJobModel> recentJobs;
    Context context;

    public RecruiterHomeRecentJobAdapter(List<RecruiterJobModel> recentJobs, Context context) {
        this.recentJobs = recentJobs;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recruiter_jobs_layout, parent, false);
        return new MyViewHolder(view);
    }


    public void onBindViewHolder(@NonNull RecruiterHomeRecentJobAdapter.MyViewHolder holder, int position) {
        RecruiterJobModel recentJob = recentJobs.get(position);

        Glide.with(context)
                .load(recentJob.getCompanyLogo())
                .into(holder.companyLogo);

        holder.jobTitle.setText(recentJob.getTitle());
        holder.companyName.setText(recentJob.getCompanyName());
        holder.jobLocation.setText(recentJob.getLocation());
        holder.workingType.setText(recentJob.getJobType());
        holder.experienceLevel.setText(recentJob.getExperienceLevel());
        holder.minSalary.setText("₹" + recentJob.getMinSalary());
        holder.maxSalary.setText("₹" + recentJob.getMaxSalary());
        holder.vacancyCount.setText("5");
        holder.postedDate.setText(recentJob.getPostedAt() + "");
    }

    @Override
    public int getItemCount() {
        return recentJobs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView companyLogo, saveJob;
        TextView jobTitle, companyName, jobLocation, workingType, experienceLevel, minSalary, maxSalary, vacancyCount, postedDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyLogo = itemView.findViewById(R.id.ivRecruiterJobsCompanyLogo);
            saveJob = itemView.findViewById(R.id.ivRecruiterJobsSaveJob);
            jobTitle = itemView.findViewById(R.id.tvRecruiterJobsJobTitle);
            companyName = itemView.findViewById(R.id.tvRecruiterJobsCompanyName);
            jobLocation = itemView.findViewById(R.id.tvRecruiterJobsLocation);
            workingType = itemView.findViewById(R.id.tvRecruiterJobsWorkingType);
            experienceLevel = itemView.findViewById(R.id.tvRecruiterJobsExperience);
            minSalary = itemView.findViewById(R.id.tvRecruiterJobsMinSalary);
            maxSalary = itemView.findViewById(R.id.tvRecruiterJobsMaxSalary);
            vacancyCount = itemView.findViewById(R.id.tvRecruiterJobsVacancyNumber);
            postedDate = itemView.findViewById(R.id.tvRecruiterJobsPostedDate);

        }
    }
}
