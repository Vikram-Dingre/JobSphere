package com.sphere.jobsphere.Candidate.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sphere.jobsphere.Candidate.Activities.CandidateApplicationDetailsActivity;
import com.sphere.jobsphere.Candidate.Models.CandidateApplicationModel;
import com.sphere.jobsphere.R;

import java.util.List;

public class CandidateApplicationsPageAdapter extends RecyclerView.Adapter<CandidateApplicationsPageAdapter.MyViewHolder> {

    List<CandidateApplicationModel> applications;
    Context context;

    public CandidateApplicationsPageAdapter(List<CandidateApplicationModel> applications, Context context) {
        this.applications = applications;
        this.context = context;
    }

    @NonNull
    @Override
    public CandidateApplicationsPageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_applications_layout, parent, false);
        return new CandidateApplicationsPageAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateApplicationsPageAdapter.MyViewHolder holder, int position) {
        CandidateApplicationModel application = applications.get(position);

        Glide.with(context)
                .load(application.getCompanyLogo())
                .into(holder.companyLogo);

        holder.jobName.setText(application.getJobName());
        holder.companyName.setText(application.getCompanyName());
        holder.jobSalary.setText(application.getJobSalary());
        holder.jobLocation.setText(application.getJobLocation());
        holder.applicationStatus.setText(application.getApplicationStatus());

        holder.viewApplication.setOnClickListener(v -> {
            Intent intent = new Intent(context, CandidateApplicationDetailsActivity.class);
            intent.putExtra("applicationId", application.getId());
            context.startActivity(intent);

        });

        if (application.getApplicationStatus().equalsIgnoreCase("Pending")) {

            holder.applicationStatus.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#1B43A047")) // Blue
            );
            holder.applicationStatus.setTextColor(context.getColor(R.color.color_on_the_way_application));

        } else if (application.getApplicationStatus().equalsIgnoreCase("Accepted")) {
            holder.applicationStatus.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#14000000")) // Blue
            );
            holder.applicationStatus.setTextColor(context.getColor(R.color.color_delivered_application));
        } else if (application.getApplicationStatus().equalsIgnoreCase("Rejected")) {
            holder.applicationStatus.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#14FF0000")) // Blue
            );
            holder.applicationStatus.setTextColor(context.getColor(R.color.color_rejected_application));
        }

    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView companyLogo;
        TextView jobName, companyName, jobSalary, jobLocation;
        AppCompatButton applicationStatus, viewApplication;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyLogo = itemView.findViewById(R.id.ivCandidateApplicationsCompanyLogo);
            jobName = itemView.findViewById(R.id.tvCandidateApplicationsJobName);
            companyName = itemView.findViewById(R.id.tvCandidateApplicationsCompanyName);
            jobSalary = itemView.findViewById(R.id.tvCandidateApplicationsSalary);
            jobLocation = itemView.findViewById(R.id.tvCandidateApplicationsLocation);
            applicationStatus = itemView.findViewById(R.id.acbCandidateApplicationsApplicationStatus);
            viewApplication = itemView.findViewById(R.id.acbCandidateApplicationsViewApplicationButton);
        }
    }


}
