package org.trainopia.pms.features.project.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.trainopia.pms.features.projectDetails.dto.ProjectDetailsDTO;

@Getter
@Setter
public class UpsertProjectDTO {
    @NotBlank(message = "title shouldn't be empty")
    private String title;

    @Min(value = 1, message = "minAge must be greater than 1")
    private int minAge;

    @Min(value = 1, message = "maxAge must be greater than 1")
    private int maxAge;

    @Positive(message = "price must be positive number")
    private double price;

    @NotBlank(message = "location shouldn't be empty")
    private String location;

    @Valid
    @NotNull
    private ProjectDetailsDTO projectDetails;

    public UpsertProjectDTO(
        String title,
        int minAge,
        int maxAge,
        double price,
        String location,
        ProjectDetailsDTO projectDetails) {
        this.title = title;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.price = price;
        this.location = location;
        this.projectDetails = projectDetails;
    }
}
