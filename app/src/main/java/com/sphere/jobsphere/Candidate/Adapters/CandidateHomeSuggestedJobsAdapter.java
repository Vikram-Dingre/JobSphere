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
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Activities.CandidateJobDetailsActivity;
import com.sphere.jobsphere.Candidate.Models.CandidateJobModel;
import com.sphere.jobsphere.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // i can add a field in job model khonw as savedByCandidates and in this field i will add the id of the candidate who had saved the job and then here i will check if savedByCandidates.contains(currentUid)


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

//        FirebaseFirestore.getInstance().collection("candidateSavedJobs")
//                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .collection("savedJobs")
//                .get()
//                .addOnSuccessListener(snapshots -> {
//                    int a = 0;
//                    for (DocumentSnapshot doc : snapshots.getDocuments()) {
//                        if (doc.getString("jobId").equals(job.getId())) {
//                            Glide.with(context)
//                                    .load(R.drawable.savedjob)
//                                    .into(holder.saveJob);
//                            a = 1;
//                            break;
//                        }
//                    }
//                    if (a == 0) {
//                        Glide.with(context)
//                                .load(R.drawable.bookmark)
//                                .into(holder.saveJob);
//                    }
//                });

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
