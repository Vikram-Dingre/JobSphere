package com.sphere.jobsphere.Candidate.Models;

import java.util.List;

public class CandiateProfessionalDetails {
    private String jobTitle, currentCompany, experience, industry, department;
    private List<String> skills;
    private double expectedSalary;
    private List<String> jobTypePreference;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCurrentCompany() {
        return currentCompany;
    }

    public void setCurrentCompany(String currentCompany) {
        this.currentCompany = currentCompany;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public double getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(double expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public List<String> getJobTypePreference() {
        return jobTypePreference;
    }

    public void setJobTypePreference(List<String> jobTypePreference) {
        this.jobTypePreference = jobTypePreference;
    }
}
