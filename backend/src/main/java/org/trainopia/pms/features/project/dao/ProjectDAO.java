package org.trainopia.pms.features.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.trainopia.pms.features.project.Project;
import org.trainopia.pms.features.project.dto.ProjectDTO;

import java.util.Optional;

public interface ProjectDAO {
  /**
   * @return List of all projects
   */
  Page<ProjectDTO> findAll(Pageable pageable);

  /**
   * Find project with given id
   *
   * @param id - id of the project
   * @return Optional of Project Entity
   */
  Optional<Project> findById(int id);

  /**
   * Save Project to database
   *
   * @param project - the project Object
   */
  Project save(Project project);

  /**
   * Save Project to database
   *
   * @param project - the project Object
   */
  Project merge(Project project);

  /**
   * Delete given project
   *
   * @param project - the project Object
   */
  void delete(Project project);
}
