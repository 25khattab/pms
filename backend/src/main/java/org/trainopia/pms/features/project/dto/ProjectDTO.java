package org.trainopia.pms.features.project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.trainopia.pms.features.projectDetails.ProjectDetails;
import org.trainopia.pms.utility.BaseDTO;

import java.time.LocalDateTime;

@Getter
@Setter
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

    public ProjectDTO(
        int id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String title,
        int minAge,
        int maxAge,
        double price,
        String location,
        ProjectDetails projectDetails) {
        super(id, createdAt, updatedAt);
        this.title = title;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.price = price;
        this.location = location;
        this.projectDetails = projectDetails;
    }
}
