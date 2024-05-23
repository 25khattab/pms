package org.trainopia.pms.features.project;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.trainopia.pms.features.projectDetails.ProjectDetails;
import org.trainopia.pms.features.projectExpense.ProjectExpense;
import org.trainopia.pms.utility.BaseEntity;

@Entity
@Table(name = "project")
@Getter
@Setter
public class Project extends BaseEntity {

  @Column(name = "title")
  private String title;

  @Column(name = "min_age")
  private int minAge;

  @Column(name = "max_age")
  private int maxAge;

  @Column(name = "price")
  private double price;

  @Column(name = "location")
  private String location;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "project_details_id")
  private ProjectDetails projectDetails;

  @OneToMany(
      mappedBy = "project",
      fetch = FetchType.LAZY,
      orphanRemoval = true, // to remove the entity entirely when setting project null
      cascade = {CascadeType.ALL})
  private List<ProjectExpense> projectExpenses = new ArrayList<>();

  public Project() {}

  public Project(String title, int minAge, int maxAge, double price, String location) {
    this.title = title;
    this.minAge = minAge;
    this.maxAge = maxAge;
    this.price = price;
    this.location = location;
  }

  public void addProjectExpense(ProjectExpense tempProjectExpense) {
    projectExpenses.add(tempProjectExpense);
    tempProjectExpense.setProject(this);
  }

  public void deleteProjectExpense(ProjectExpense tempProjectExpense) {
    projectExpenses.remove(tempProjectExpense);
    tempProjectExpense.setProject(null);
  }

  @Override
  public String toString() {
    return "Project{"
        + "id="
        + getId()
        + ", title='"
        + title
        + '\''
        + ", minAge="
        + minAge
        + ", maxAge="
        + maxAge
        + ", price="
        + price
        + ", location='"
        + location
        + '\''
        + ", projectDetails="
        + projectDetails
        + ", projectExpenses="
        + projectExpenses
        + ", createdAt="
        + getCreatedAt()
        + ", updatedAt="
        + getUpdatedAt()
        + '}';
  }
}
