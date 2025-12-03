package com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels;

public class RecruiterRecentChatsModel {
    private String candidateProfilePhoto;
    private String candidateName;
    private String recentChat;
    private String recentChatTime;

    public RecruiterRecentChatsModel(String candidateName, String candidateProfilePhoto, String recentChat, String recentChatTime) {
        this.candidateName = candidateName;
        this.candidateProfilePhoto = candidateProfilePhoto;
        this.recentChat = recentChat;
        this.recentChatTime = recentChatTime;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCandidateProfilePhoto() {
        return candidateProfilePhoto;
    }

    public void setCandidateProfilePhoto(String candidateProfilePhoto) {
        this.candidateProfilePhoto = candidateProfilePhoto;
    }

    public String getRecentChat() {
        return recentChat;
    }

    public void setRecentChat(String recentChat) {
        this.recentChat = recentChat;
    }

    public String getRecentChatTime() {
        return recentChatTime;
    }

    public void setRecentChatTime(String recentChatTime) {
        this.recentChatTime = recentChatTime;
    }
}
