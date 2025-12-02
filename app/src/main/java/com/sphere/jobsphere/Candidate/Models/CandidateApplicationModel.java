package com.sphere.jobsphere.Candidate.Models;

public class CandidateApplicationModel {
    private String id;
    private String companyId;
    private String companyLogo;
    private String jobName;
    private String companyName;
    private String jobSalary;
    private String jobLocation;
    private String applicationStatus;

    public CandidateApplicationModel(String id, String companyLogo, String jobName, String companyName, String jobSalary, String jobLocation, String applicationStatus) {
        this.id = id;
        this.companyLogo = companyLogo;
        this.jobName = jobName;
        this.companyName = companyName;
        this.jobSalary = jobSalary;
        this.jobLocation = jobLocation;
        this.applicationStatus = applicationStatus;
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
}
