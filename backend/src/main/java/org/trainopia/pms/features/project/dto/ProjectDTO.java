package org.trainopia.pms.features.project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.trainopia.pms.features.projectDetails.ProjectDetails;
import org.trainopia.pms.utility.BaseDTO;

import java.time.LocalDateTime;

public class ProjectDTO extends BaseDTO {

    @NotBlank(message = "title shouldn't be empty")
    private String title;

    @Min(value = 1, message = "minAge must be greater than 1")
    private int minAge;

    @Min(value = 1, message = "maxAge must be greater than 1")
    private int maxAge;

    @Positive(message = "price must be a positive number")
    private double price;

    @NotBlank(message = "location shouldn't be empty")
    private String location;
    private ProjectDetails projectDetails;

    public ProjectDTO(int id, LocalDateTime createdAt, LocalDateTime updatedAt, String title, int minAge, int maxAge, double price,
                      String location, ProjectDetails projectDetails) {
        super(id, createdAt, updatedAt);
        this.title = title;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.price = price;
        this.location = location;
        this.projectDetails = projectDetails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProjectDetails getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(ProjectDetails projectDetails) {
        this.projectDetails = projectDetails;
    }
}