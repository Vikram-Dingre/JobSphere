package com.sphere.jobsphere.Candidate.Models;

import java.util.ArrayList;
import java.util.List;

public class CandidateProfile {

    private CandidatePersonalInfo personalInfo;
    private CandiateProfessionalDetails professionalDetails;
    private List<CandidateEducation> education;
    private List<CandidateCertifications> certifications;
    private CandidateResumePreferences resumePreferences;

    public CandidateProfile() {
        education = new ArrayList<>();
        certifications = new ArrayList<>();
    }

    public CandidatePersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(CandidatePersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public CandiateProfessionalDetails getProfessionalDetails() {
        return professionalDetails;
    }

    public void setProfessionalDetails(CandiateProfessionalDetails professionalDetails) {
        this.professionalDetails = professionalDetails;
    }

    public List<CandidateEducation> getEducation() {
        return education;
    }

    public void setEducation(List<CandidateEducation> education) {
        this.education = education;
    }

    public List<CandidateCertifications> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<CandidateCertifications> certifications) {
        this.certifications = certifications;
    }

    public CandidateResumePreferences getResumePreferences() {
        return resumePreferences;
    }

    public void setResumePreferences(CandidateResumePreferences resumePreferences) {
        this.resumePreferences = resumePreferences;
    }
}
