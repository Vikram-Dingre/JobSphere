package com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels;

public class RecruiterApplicantsModel {
    public String id;
    public String applicantId;
    public String candidateApplicationId;
    public String jobId;
    public String applicantProfilePhoto;
    public String applicantName;
    public String applicantEmail;
    public String applicantResume;
    public String status;
    public String message;


    public RecruiterApplicantsModel() {
    }

    public RecruiterApplicantsModel(String applicantId, String candidateApplicationId, String jobId, String applicantProfilePhoto, String applicantName, String applicantEmail, String applicantResume, String status, String message) {
        this.applicantId = applicantId;
        this.candidateApplicationId = candidateApplicationId;
        this.jobId = jobId;
        this.applicantProfilePhoto = applicantProfilePhoto;
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
        this.applicantResume = applicantResume;
        this.status = status;
        this.message = message;
    }

    public String getCandidateApplicationId() {
        return candidateApplicationId;
    }

    public void setCandidateApplicationId(String candidateApplicationId) {
        this.candidateApplicationId = candidateApplicationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getApplicantResume() {
        return applicantResume;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }


    public void setApplicantResume(String applicantResume) {
        this.applicantResume = applicantResume;
    }

    public String getApplicantProfilePhoto() {
        return applicantProfilePhoto;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantProfilePhoto(String applicantProfilePhoto) {
        this.applicantProfilePhoto = applicantProfilePhoto;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }
}
