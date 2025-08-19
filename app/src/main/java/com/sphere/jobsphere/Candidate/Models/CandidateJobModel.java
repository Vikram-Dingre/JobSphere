package com.sphere.jobsphere.Candidate.Models;

import java.util.List;

public class CandidateJobModel {

    private String id;                  // Firestore document ID
    private String title;               // Job title
    private String description;         // Full job description
    private String companyName;         // Company name
    private String companyLogo;         // Company logo URL
    private String location;            // City, State, Country
    private String jobType;             // Full-time, Part-time, Remote, Freelance
    private List<String> jobTypes;      // If multiple (Remote + Part-time)

    private String vacancies;
//    private String about;

    private String category;            // IT, Finance, Marketing, etc.
    private String skillsRequired;      // Skills needed (comma-separated)
    private List<String> skillsList;     // Skills as list

    private String salary;              // Salary text (e.g., "₹8 LPA")
    private Double minSalary;           // For filtering
    private Double maxSalary;           // For filtering

    private String experienceLevel;     // Fresher, Mid, Senior
    private String education;           // Required degree

    private long postedAt;              // Timestamp job posted
    private long deadline;              // Application deadline timestamp

    private String recruiterId;         // User ID of recruiter
    private String recruiterName;       // Recruiter/HR name
    private String recruiterEmail;      // Recruiter email

    private int applicantsCount;        // Number of applicants
    private List<String> applicants;    // List of candidate userIds

    private Double matchScore;          // For suggested jobs (null for recent)

    public CandidateJobModel() {
    } // Required for Firestore

    public CandidateJobModel(String id, String title, String description, String companyName,
                             String companyLogo, String location, String jobType, List<String> jobTypes,
                             String category, String skillsRequired, List<String> skillsList,
                             String salary, Double minSalary, Double maxSalary,
                             String experienceLevel, String education,
                             long postedAt, long deadline,
                             String recruiterId, String recruiterName, String recruiterEmail,
                             int applicantsCount, List<String> applicants,
                             Double matchScore) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.companyName = companyName;
        this.companyLogo = companyLogo;
        this.location = location;
        this.jobType = jobType;
        this.jobTypes = jobTypes;
        this.category = category;
        this.skillsRequired = skillsRequired;
        this.skillsList = skillsList;
        this.salary = salary;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.experienceLevel = experienceLevel;
        this.education = education;
        this.postedAt = postedAt;
        this.deadline = deadline;
        this.recruiterId = recruiterId;
        this.recruiterName = recruiterName;
        this.recruiterEmail = recruiterEmail;
        this.applicantsCount = applicantsCount;
        this.applicants = applicants;
        this.matchScore = matchScore;
    }

    // 🔹 Getters & Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public List<String> getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(List<String> jobTypes) {
        this.jobTypes = jobTypes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public List<String> getSkillsList() {
        return skillsList;
    }

    public void setSkillsList(List<String> skillsList) {
        this.skillsList = skillsList;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Double minSalary) {
        this.minSalary = minSalary;
    }

    public Double getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public long getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(long postedAt) {
        this.postedAt = postedAt;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(String recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getRecruiterName() {
        return recruiterName;
    }

    public void setRecruiterName(String recruiterName) {
        this.recruiterName = recruiterName;
    }

    public String getRecruiterEmail() {
        return recruiterEmail;
    }

    public void setRecruiterEmail(String recruiterEmail) {
        this.recruiterEmail = recruiterEmail;
    }

    public int getApplicantsCount() {
        return applicantsCount;
    }

    public void setApplicantsCount(int applicantsCount) {
        this.applicantsCount = applicantsCount;
    }

    public List<String> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<String> applicants) {
        this.applicants = applicants;
    }

    public Double getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(Double matchScore) {
        this.matchScore = matchScore;
    }
}
