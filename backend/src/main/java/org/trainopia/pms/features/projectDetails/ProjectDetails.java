package org.trainopia.pms.features.projectDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.trainopia.pms.utility.BaseEntity;

@Entity
@Table(name = "project_details")
@Getter
@Setter
public class ProjectDetails extends BaseEntity {

  @Column(name = "description")
  private String description;

  @Column(name = "images_folder_url")
  private String imagesFolderURL;

  public ProjectDetails() {}

  public ProjectDetails(String description, String imgFolderURL) {
    this.description = description;
    this.imagesFolderURL = imgFolderURL;
  }

  @Override
  public String toString() {
    return "ProjectDetails{"
        + "description='"
        + description
        + '\''
        + ", imgFolderUrl='"
        + imagesFolderURL
        + '\''
        + '}';
  }
}
