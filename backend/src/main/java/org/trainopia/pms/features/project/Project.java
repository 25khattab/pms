package org.trainopia.pms.features.project;

import jakarta.persistence.*;
import org.trainopia.pms.features.projectDetails.ProjectDetails;
import org.trainopia.pms.features.projectExpense.ProjectExpense;
import org.trainopia.pms.utility.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
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

    @OneToMany(mappedBy = "project",
        fetch = FetchType.LAZY,
        cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private List<ProjectExpense> projectExpenses;

    public Project() {

    }

    public Project(String title, int minAge, int maxAge, double price, String location) {
        this.title = title;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.price = price;
        this.location = location;
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

    public List<ProjectExpense> getProjectExpenses() {
        return projectExpenses;
    }

    public void setProjectExpenses(List<ProjectExpense> projectExpenses) {
        this.projectExpenses = projectExpenses;
    }

    public void addProjectExpense(ProjectExpense tempProjectExpense) {
        if (projectExpenses == null) {
            projectExpenses = new ArrayList<>();
        }
        projectExpenses.add(tempProjectExpense);
        tempProjectExpense.setProject(this);
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", title='" + title + '\'' +
            ", minAge=" + minAge +
            ", maxAge=" + maxAge +
            ", price=" + price +
            ", location='" + location + '\'' +
            ", projectDetails=" + projectDetails +
            ", projectExpenses=" + projectExpenses +
            ", createdAt=" + getCreatedAt() +
            ", updatedAt=" + getUpdatedAt() +
            '}';
    }
}
