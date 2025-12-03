package com.sphere.jobsphere.Recruiter.Models.MainActivityHomeFragmentModels;

public class RecruiterApplicantsModel {
    public String applicantProfilePhoto;
    public String applicantName;
    public String applicantSpecification;

    public RecruiterApplicantsModel(String applicantProfilePhoto, String applicantName , String applicantSpecification){
        this.applicantProfilePhoto=applicantProfilePhoto;
        this.applicantName=applicantName;
        this.applicantSpecification=applicantSpecification;
    }

    public String getApplicantProfilePhoto() {
        return applicantProfilePhoto;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getApplicantSpecification() {
        return applicantSpecification;
    }

    public void setApplicantProfilePhoto(String applicantProfilePhoto) {
        this.applicantProfilePhoto = applicantProfilePhoto;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public void setApplicantSpecification(String applicantSpecification) {
        this.applicantSpecification = applicantSpecification;
    }
}
