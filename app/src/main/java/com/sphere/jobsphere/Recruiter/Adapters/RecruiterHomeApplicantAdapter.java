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
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterApplicantsModel;

import java.util.List;

public class RecruiterHomeApplicantAdapter extends RecyclerView.Adapter<RecruiterHomeApplicantAdapter.MyViewHolder> {
    List<RecruiterApplicantsModel> applicants;
    Context context;

    public RecruiterHomeApplicantAdapter(List<RecruiterApplicantsModel> applicants, Context context) {
        this.applicants = applicants;
        this.context = context;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recruiter_side_applicant_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecruiterHomeApplicantAdapter.MyViewHolder holder, int position) {
        RecruiterApplicantsModel applicant = applicants.get(position);

        Glide.with(context)
                .load(applicant.getApplicantProfilePhoto())
                .into(holder.applicantProfilePhoto);
        holder.applicantName.setText(applicant.applicantName);
        holder.applicantSpecificatin.setText(applicant.applicantSpecification);
    }

    @Override
    public int getItemCount() {
        return applicants.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView applicantProfilePhoto;
        TextView applicantName, applicantSpecificatin;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            applicantProfilePhoto = itemView.findViewById(R.id.ivApplicantProfilePhoto);
            applicantName = itemView.findViewById(R.id.tvApplicantName);
            applicantSpecificatin = itemView.findViewById(R.id.tvApplicantSpecification);
        }
    }
}
