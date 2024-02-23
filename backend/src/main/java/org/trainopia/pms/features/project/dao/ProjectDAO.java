package org.trainopia.pms.features.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.trainopia.pms.features.project.Project;
import org.trainopia.pms.features.project.dto.ProjectDTO;

import java.util.List;
import java.util.Optional;

public interface ProjectDAO {
    /**
     * @return List of all projects
     */
    public Page<ProjectDTO> findAll(Pageable pageable);

    /**
     * Find project with given Id
     *
     * @param id
     * @return Optional of Project Entity
     */
    public Optional<Project> findById(int id);
    /**
     * Save Project to database
     *
     * @param project
     */
    public Project save(Project project);

    /**
     * Delete project by given Id
     *
     * @param id
     */
    public void deleteById(int id);

}
