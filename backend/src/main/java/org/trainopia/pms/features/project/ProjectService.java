package org.trainopia.pms.features.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.trainopia.pms.features.project.dto.CreateProjectDTO;
import org.trainopia.pms.features.project.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
    Project findById(int id);

    Project create(CreateProjectDTO createProjectDTO);

    Page<ProjectDTO> findAll(Pageable pageable);

//    List<Project> findAllWithExpenses();
//
//    Project findWithExpensesById(int id);
//
//    Project update(Project project);
//
//    void deleteById(int id);
}
