package com.sphere.jobsphere.Candidate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.sphere.jobsphere.Candidate.Models.CandidateJobModel;
import com.sphere.jobsphere.R;

import java.util.List;

public class CandidateHomeRecentJobsAdapter extends RecyclerView.Adapter<CandidateHomeRecentJobsAdapter.MyViewHolder> {

    List<CandidateJobModel> recentJobs;
    Context context;
    int[] colors = new int[]{R.color.recentOne, R.color.recentTwo, R.color.recentThree, R.color.recentFour};

    public CandidateHomeRecentJobsAdapter(List<CandidateJobModel> recentJobs, Context context) {
        this.recentJobs = recentJobs;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_home_recent_job_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CandidateJobModel job = recentJobs.get(position);

        holder.ll.setBackgroundColor(ContextCompat.getColor(context, colors[position]));

        Glide.with(context)
                .load(job.getCompanyLogo())
                .into(holder.companyLogo);

        holder.jobTitle.setText(job.getTitle());
        holder.companyName.setText(job.getCompanyName());
        holder.minSalary.setText("₹" + job.getMinSalary());
        holder.maxSalary.setText("₹" + job.getMaxSalary());

        holder.jobTypeChipGroup.removeAllViews(); // clear old chips

        for (String type : job.getJobTypes()) {
            Chip chip = new Chip(context);
            chip.setText(type);
            chip.setChipBackgroundColorResource(R.color.lightPurple); // light bg
            chip.setTextColor(ContextCompat.getColor(context, R.color.white));
            chip.setChipCornerRadius(20f);
            chip.setClickable(false);
            chip.setCheckable(false);

            holder.jobTypeChipGroup.addView(chip);
        }

    }

    @Override
    public int getItemCount() {
        return recentJobs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView companyLogo, exploreJob;
        TextView jobTitle, companyName, minSalary, maxSalary;
        ChipGroup jobTypeChipGroup;
        LinearLayout ll;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyLogo = itemView.findViewById(R.id.ivCandidateHomeRecentJobImage);
            exploreJob = itemView.findViewById(R.id.ivCandidateHomeRecentJobExplore);
            jobTitle = itemView.findViewById(R.id.tvCandidateHomeRecentJobTitle);
            companyName = itemView.findViewById(R.id.tvCandidateHomeRecentJobCompanyName);
            minSalary = itemView.findViewById(R.id.tvCandidateHomeRecentJobMinSalary);
            maxSalary = itemView.findViewById(R.id.tvCandidateHomeRecentJobMaxSalary);
            jobTypeChipGroup = itemView.findViewById(R.id.candidateHomeRecentChipGroup);
            ll = itemView.findViewById(R.id.llCandidateRecentJobColored);
        }
    }

}
