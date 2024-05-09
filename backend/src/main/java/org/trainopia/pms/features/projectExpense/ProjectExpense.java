package org.trainopia.pms.features.projectExpense;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.trainopia.pms.features.project.Project;

@Setter
@Getter
@Entity
@Table(name = "project_expense")
public class ProjectExpense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private int price;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "project_id")
  @JsonIgnore
  private Project project;

  public ProjectExpense() {}

  public ProjectExpense(String name, int price) {
    this.name = name;
    this.price = price;
  }

  @Override
  public String toString() {
    return "ProjectExpense{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ","
        + " price="
        + price
        + ", createdAt="
        + createdAt
        + ", "
        + "updatedAt"
        + "="
        + updatedAt
        + '}';
  }
}
