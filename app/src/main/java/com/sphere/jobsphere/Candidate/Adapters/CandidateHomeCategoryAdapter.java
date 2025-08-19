package com.sphere.jobsphere.Candidate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sphere.jobsphere.Candidate.Models.CandidateHomeCategoryModel;
import com.sphere.jobsphere.R;

import java.util.List;

public class CandidateHomeCategoryAdapter extends RecyclerView.Adapter<CandidateHomeCategoryAdapter.MyViewHolder> {
    List<CandidateHomeCategoryModel> categories;
    Context context;

    public CandidateHomeCategoryAdapter(List<CandidateHomeCategoryModel> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_home_category_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CandidateHomeCategoryModel category = categories.get(position);

//        Glide.with(context)
//             .load(category.getCategoryImage())
//             .into(holder.categoryImage);

        holder.categoryImage.setImageResource(category.getCategoryImage());

        holder.categoryName.setText(category.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.ivCandidateHomeCategoryImage);
            categoryName = itemView.findViewById(R.id.tvCandidateHomeCategoryName);
        }
    }
}
