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
import com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels.RecruiterRecentChatsModel;

import org.w3c.dom.Text;

import java.util.List;

public class RecruiterHomeRecentChatsAdapter extends RecyclerView.Adapter<RecruiterHomeRecentChatsAdapter.MyViewHolder> {

    List<RecruiterRecentChatsModel> recentChats;
    Context context;

    public RecruiterHomeRecentChatsAdapter(Context context, List<RecruiterRecentChatsModel> recentChats) {
        this.context = context;
        this.recentChats = recentChats;
    }


    @NonNull
    @Override
    public RecruiterHomeRecentChatsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recruiter_recent_chat_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecruiterHomeRecentChatsAdapter.MyViewHolder holder, int position) {
        RecruiterRecentChatsModel recentChat=recentChats.get(position);
        Glide.with(context)
                .load(recentChat.getCandidateProfilePhoto())
                .into(holder.candidateProfilePhoto);

        holder.candidateName.setText(recentChat.getCandidateName());
        holder.recentChat.setText(recentChat.getRecentChat());
        holder.recentChatTime.setText(recentChat.getRecentChatTime());
    }


    @Override
    public int getItemCount() {
        return recentChats.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView candidateProfilePhoto;
        TextView candidateName,recentChat,recentChatTime;
        public MyViewHolder(View view) {
            super(view);
            candidateProfilePhoto=view.findViewById(R.id.ivChatProfilePhoto);
            candidateName=view.findViewById(R.id.tvMessageSenderName);
            recentChat=view.findViewById(R.id.tvRecentChat);
            recentChatTime=view.findViewById(R.id.tvRecentChatTime);
        }
    }
}
