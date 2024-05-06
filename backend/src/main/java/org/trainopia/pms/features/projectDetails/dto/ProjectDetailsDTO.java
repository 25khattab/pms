package org.trainopia.pms.features.projectDetails.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProjectDetailsDTO {

  @NotBlank(message = "description mustn't be empty")
  private String description;

  @NotBlank(message = "imagesFolderURL mustn't be empty")
  private String imagesFolderURL;

  public ProjectDetailsDTO(String description, String imagesFolderURL) {
    this.description = description;
    this.imagesFolderURL = imagesFolderURL;
  }
}
