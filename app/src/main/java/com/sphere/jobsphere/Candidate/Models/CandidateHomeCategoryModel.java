package com.sphere.jobsphere.Candidate.Models;

public class CandidateHomeCategoryModel {
    private int categoryImage;
    private String categoryName;

    public CandidateHomeCategoryModel() {
    }

    public CandidateHomeCategoryModel(int categoryImage, String categoryName) {
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
