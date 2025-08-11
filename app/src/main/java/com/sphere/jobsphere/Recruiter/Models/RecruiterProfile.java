package com.sphere.jobsphere.Recruiter.Models;

public class RecruiterProfile {
    private String uid;
    private RecruiterPersonalInfo personalInfo;
    private RecruiterCompanyDetails companyDetails;
    private RecruiterCompanyLocation companyLocation;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public RecruiterPersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(RecruiterPersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public RecruiterCompanyDetails getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(RecruiterCompanyDetails companyDetails) {
        this.companyDetails = companyDetails;
    }

    public RecruiterCompanyLocation getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(RecruiterCompanyLocation companyLocation) {
        this.companyLocation = companyLocation;
    }
}
