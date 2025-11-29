package com.sphere.jobsphere.Candidate.Classes;

import java.util.List;

public class CandidateJobFilterModel {
    private List<String> selectedJobType;
    private List<String> selectedCategory;
    private List<String> selectedSkills;
    private String selectedExperience;
    private List<String> selectedLocation;
    private Double selectedMinSalary;
    private Double selectedMaxSalary;
    private String selectedFreshness;

    public CandidateJobFilterModel(List<String> selectedJobType, List<String> selectedCategory, List<String> selectedSkills, String selectedExperience, List<String> selectedLocation, Double selectedMinSalary, Double selectedMaxSalary, String selectedFreshness) {
        this.selectedJobType = selectedJobType;
        this.selectedCategory = selectedCategory;
        this.selectedSkills = selectedSkills;
        this.selectedExperience = selectedExperience;
        this.selectedLocation = selectedLocation;
        this.selectedMinSalary = selectedMinSalary;
        this.selectedMaxSalary = selectedMaxSalary;
        this.selectedFreshness = selectedFreshness;
    }

    public List<String> getSelectedJobType() {
        return selectedJobType;
    }

    public void setSelectedJobType(List<String> selectedJobType) {
        this.selectedJobType = selectedJobType;
    }

    public List<String> getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(List<String> selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public List<String> getSelectedSkills() {
        return selectedSkills;
    }

    public void setSelectedSkills(List<String> selectedSkills) {
        this.selectedSkills = selectedSkills;
    }

    public String getSelectedExperience() {
        return selectedExperience;
    }

    public void setSelectedExperience(String selectedExperience) {
        this.selectedExperience = selectedExperience;
    }

    public List<String> getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(List<String> selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public Double getSelectedMinSalary() {
        return selectedMinSalary;
    }

    public void setSelectedMinSalary(Double selectedMinSalary) {
        this.selectedMinSalary = selectedMinSalary;
    }

    public Double getSelectedMaxSalary() {
        return selectedMaxSalary;
    }

    public void setSelectedMaxSalary(Double selectedMazSalary) {
        this.selectedMaxSalary = selectedMazSalary;
    }

    public String getSelectedFreshness() {
        return selectedFreshness;
    }

    public void setSelectedFreshness(String selectedFreshness) {
        this.selectedFreshness = selectedFreshness;
    }
}
