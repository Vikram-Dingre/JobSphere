package com.sphere.jobsphere.Candidate.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.sphere.jobsphere.Candidate.Activities.CandidateJobDetailsActivity;
import com.sphere.jobsphere.Candidate.Models.CandidateJobModel;
import com.sphere.jobsphere.R;

import java.util.List;

public class CandidateHomeSuggestedJobsAdapter extends RecyclerView.Adapter<CandidateHomeSuggestedJobsAdapter.MyViewHolder> {

    Context context;
    List<CandidateJobModel> suggestedJobs;
    int[] colors = new int[]{R.color.one, R.color.two, R.color.three};

    public CandidateHomeSuggestedJobsAdapter(Context context, List<CandidateJobModel> suggestedJobs) {
        this.context = context;
        this.suggestedJobs = suggestedJobs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_home_suggested_jobs_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CandidateJobModel job = suggestedJobs.get(position);

        holder.cvCandidateSuggestedJobCard.setCardBackgroundColor(ContextCompat.getColor(context, colors[position]));

        Glide.with(context)
                .load(job.getCompanyLogo())
                .into(holder.companyLogo);

        holder.apply.setOnClickListener(v -> {
            Intent intent = new Intent(context, CandidateJobDetailsActivity.class);
            intent.putExtra("jobId", job.id);
            context.startActivity(intent);

        });

        holder.companyName.setText(job.getCompanyName());
        holder.jobTitle.setText(job.getTitle());
        holder.salary.setText(job.getSalary());


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
        return suggestedJobs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView companyLogo, saveJob;
        TextView companyName, jobTitle, salary;
        AppCompatButton apply;
        ChipGroup jobTypeChipGroup;
        CardView cvCandidateSuggestedJobCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyLogo = itemView.findViewById(R.id.ivCandidateHomeSuggestedJobImage);
            saveJob = itemView.findViewById(R.id.ivCandidateHomeSuggestedSaveJob);
            jobTitle = itemView.findViewById(R.id.tvCandidateHomeSuggestedJobTitle);
            companyName = itemView.findViewById(R.id.tvCandidateHomeSuggestedCompanyName);
            salary = itemView.findViewById(R.id.tvCandidateHomeSuggestedSalary);
            apply = itemView.findViewById(R.id.acbCandidateHomeSuggestedApplyJob);
            jobTypeChipGroup = itemView.findViewById(R.id.jobTypeChipGroup);
            cvCandidateSuggestedJobCard = itemView.findViewById(R.id.cvCandidateSuggestedJobCard);
        }
    }

}
