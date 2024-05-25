package org.trainopia.pms.features.project.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.trainopia.pms.features.project.Project;
import org.trainopia.pms.features.project.dto.ProjectDTO;

@Repository
public class ProjectDAOImpl implements ProjectDAO {

  private final EntityManager entityManager;

  @Autowired
  public ProjectDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Page<ProjectDTO> findAll(Pageable pageable) {
    String countQuery = "SELECT COUNT(u) FROM Project u";
    Long total = entityManager.createQuery(countQuery, Long.class).getSingleResult();

    StringBuilder selectQuery =
        new StringBuilder(
            """
                SELECT NEW org.trainopia.pms.features.project.dto.ProjectDTO(
                p.id, p.createdAt, p.updatedAt, p.title, p.minAge, p.maxAge, p.price, p.location, p.projectDetails)
                FROM Project p
                """);

    // Append sorting information
    if (pageable.getSort().isSorted()) {
      selectQuery.append(" ORDER BY ");
      selectQuery.append(
          pageable.getSort().stream()
              .map(order -> order.getProperty() + " " + (order.isAscending() ? "ASC" : "DESC"))
              .collect(Collectors.joining(", ")));
    }

    List<ProjectDTO> result =
        entityManager
            .createQuery(selectQuery.toString(), ProjectDTO.class)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();

    return new PageImpl<>(result, pageable, total);
  }

  @Override
  public Optional<Project> findById(int id) {
    List<Project> resultList =
        entityManager
            .createQuery(
                "FROM Project p "
                    + "left join fetch  p.projectExpenses join fetch"
                    + " p.projectDetails where p.id=:id",
                Project.class)
            .setParameter("id", id)
            .getResultList();
    return !resultList.isEmpty() ? Optional.of(resultList.get(0)) : Optional.empty();
  }

  @Override
  public Project findProxyById(int id) {
    return entityManager.getReference(Project.class, id);
  }

  @Override
  public Project save(Project project) {
    entityManager.persist(project);
    return project;
  }

  @Override
  public Project merge(Project project) {
    return entityManager.merge(project);
  }

  @Override
  public void delete(Project project) {
    entityManager.remove(project);
  }
}
