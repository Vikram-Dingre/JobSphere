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
import com.sphere.jobsphere.Candidate.Models.CandidateJobModel;
import com.sphere.jobsphere.R;
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterApplicationsModel;

import java.util.List;

public class RecruiterApplicationsPageAdapter extends RecyclerView.Adapter<RecruiterApplicationsPageAdapter.MyViewHolder> {

    List<RecruiterApplicationsModel> applications;
    Context context;

    public  RecruiterApplicationsPageAdapter (List<RecruiterApplicationsModel> applications, Context context) {
        this.applications = applications;
        this.context = context;
    }

    @NonNull
    @Override
    public  RecruiterApplicationsPageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recruiter_applications_layout, parent, false);
        return new  RecruiterApplicationsPageAdapter .MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecruiterApplicationsPageAdapter.MyViewHolder holder, int position) {
        RecruiterApplicationsModel application = applications.get(position);

        Glide.with(context)
                .load(application.getCompanyLogo())
                .into(holder.companyLogo);

        holder.applicationTitle.setText(application.getTitle());
        holder.companyName.setText(application.getCompanyName());
        holder.applicationLocation.setText(application.getLocation());
        holder.workingType.setText(application.getJobType());
        holder.experienceLevel.setText(application.getExperienceLevel());
        holder.minSalary.setText("₹" + application.getMinSalary());
        holder.maxSalary.setText("₹" + application.getMaxSalary());
        holder.vacancyCount.setText("5");
        holder.postedDate.setText(application.getPostedAt() + "");
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView companyLogo, saveJob;
        TextView applicationTitle, companyName, applicationLocation, workingType, experienceLevel, minSalary, maxSalary, vacancyCount, postedDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyLogo = itemView.findViewById(R.id.ivRecruiterApplicationCompanyLogo);
            saveJob = itemView.findViewById(R.id.ivRecruiterJobsSaveJob);
            applicationTitle = itemView.findViewById(R.id.tvRecruiterApplicationTitle);
            companyName = itemView.findViewById(R.id.tvRecruiterApplicationCompanyName);
            applicationLocation = itemView.findViewById(R.id.tvRecruiterApplicationLocation);
            workingType = itemView.findViewById(R.id.tvRecruiterApplicationWorkingType);
            experienceLevel = itemView.findViewById(R.id.tvRecruiterApplicationExperience);
            minSalary = itemView.findViewById(R.id.tvRecruiterApplicationMinSalary);
            maxSalary = itemView.findViewById(R.id.tvRecruiterApplicationMaxSalary);
            vacancyCount = itemView.findViewById(R.id.tvRecruiterApplicationVacancyNumber);
            postedDate = itemView.findViewById(R.id.tvRecruiterApplicationPostedDate);

        }
    }

}
