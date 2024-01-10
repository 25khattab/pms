package org.trainopia.pms.features.projectDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import org.trainopia.pms.utility.BaseEntity;

@Entity
@Table(name = "project_details")
@NoArgsConstructor
public class ProjectDetails extends BaseEntity {

    @Column(name = "description")
    private String description;

    @Column(name = "images_folder_url")
    private String imagesFolderURL;

    public ProjectDetails(String description, String imgFolderURL) {
        this.description = description;
        this.imagesFolderURL = imgFolderURL;

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

    @Override
    public String toString() {
        return "ProjectDetails{" +
            "description='" + description + '\'' +
            ", imgFolderUrl='" + imagesFolderURL + '\'' +
            '}';
    }
}
