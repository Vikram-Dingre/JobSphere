package com.sphere.jobsphere.Candidate.Models;

public class CandidateApplicationModel {
    public String id;
    private String jobId;
    private String companyLogo;
    private String jobName;
    private String companyName;
    private String jobSalary;
    private String jobType;
    private String jobLocation;
    private String recruiterMessage;
    private String applicationStatus;
    private String resume;

    // student apply --> application saved --> displayed at app. frag. --> show details if clicked
    // view job details --> see the job applicants --> see details button --> show deatils and option for msg,status change things --> use that user id an update that status and message into student application

    public CandidateApplicationModel(String id, String jobId, String companyLogo, String jobName, String companyName, String jobSalary, String jobType, String jobLocation, String recruiterMessage, String applicationStatus, String resume) {
        this.id = id;
        this.jobId = jobId;
        this.companyLogo = companyLogo;
        this.jobName = jobName;
        this.companyName = companyName;
        this.jobSalary = jobSalary;
        this.jobType = jobType;
        this.jobLocation = jobLocation;
        this.recruiterMessage = recruiterMessage;
        this.applicationStatus = applicationStatus;
        this.resume = resume;
    }

    public CandidateApplicationModel() {
    }


    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getRecruiterMessage() {
        return recruiterMessage;
    }

    public void setRecruiterMessage(String recruiterMessage) {
        this.recruiterMessage = recruiterMessage;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
