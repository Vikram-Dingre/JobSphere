package com.sphere.jobsphere.Candidate.Classes;

import java.util.ArrayList;
import java.util.List;

public class CandidateJobFilterState {

    private static CandidateJobFilterState instance;

    public List<String> selectedCategories = new ArrayList<>();
    public List<String> selectedJobTypes = new ArrayList<>();
    public List<String> selectedSkills = new ArrayList<>();
    public String selectedExperience = "";
    public List<String> selectedLocation = new ArrayList<>();
    public Double minSalary = 0.0;
    public Double maxSalary = 0.0;
    public String freshness = "";

    private CandidateJobFilterState() {
    }

    public static CandidateJobFilterState getInstance() {
        if (instance == null) {
            instance = new CandidateJobFilterState();
        }
        return instance;
    }


}
