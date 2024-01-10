package org.trainopia.pms.features.projectDetails.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.trainopia.pms.utility.BaseDTO;
import org.trainopia.pms.utility.BaseEntity;

import java.time.LocalDateTime;


public class ProjectDetailsDTO {

    @NotBlank(message = "description mustn't be empty")
    private String description;

    @NotBlank(message = "imagesFolderURL mustn't be empty")
    private String imagesFolderURL;

    public ProjectDetailsDTO( String description, String imagesFolderURL) {
        //super(id, createdAt, updatedAt);
        this.description = description;
        this.imagesFolderURL = imagesFolderURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagesFolderURL() {
        return imagesFolderURL;
    }

    public void setImagesFolderURL(String imagesFolderURL) {
        this.imagesFolderURL = imagesFolderURL;
    }
}
