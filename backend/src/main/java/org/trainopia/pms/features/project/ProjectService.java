package org.trainopia.pms.features.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.trainopia.pms.features.project.dto.CreateProjectDTO;
import org.trainopia.pms.features.project.dto.ProjectDTO;
import org.trainopia.pms.features.projectExpense.dto.ProjectExpenseDTO;

public interface ProjectService {
  Project findById(int id);

  Project create(CreateProjectDTO createProjectDTO);

  Project update(int projectId, CreateProjectDTO createProjectDTO);

  Page<ProjectDTO> findAll(Pageable pageable);

  void deleteById(int projectId);

  Project createExpense(int projectId, ProjectExpenseDTO projectExpense);

  Project updateExpense(int projectId, int projectExpenseID, ProjectExpenseDTO projectExpense);

  void deleteExpense(int projectId, int projectExpenseID);
}
