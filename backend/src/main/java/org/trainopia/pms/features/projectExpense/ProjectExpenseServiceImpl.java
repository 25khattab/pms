package org.trainopia.pms.features.projectExpense;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trainopia.pms.features.project.Project;
import org.trainopia.pms.features.project.ProjectService;
import org.trainopia.pms.features.projectExpense.dto.ProjectExpenseDTO;
import org.trainopia.pms.utility.AppException;
import org.trainopia.pms.utility.CommonError;

@Service
public class ProjectExpenseServiceImpl implements ProjectExpenseService {

  ProjectExpenseRepository projectExpenseRepository;
  ProjectService projectService;

  public ProjectExpenseServiceImpl(
      ProjectExpenseRepository projectExpenseRepository, ProjectService projectService) {
    this.projectExpenseRepository = projectExpenseRepository;
    this.projectService = projectService;
  }

  @Override
  @Transactional
  public ProjectExpense createExpense(int projectId, ProjectExpenseDTO projectExpense) {
    Project existingProject = projectService.findProxyById(projectId);
    ProjectExpense newProjectExpense =
        new ProjectExpense(projectExpense.getName(), projectExpense.getPrice());
    existingProject.addProjectExpense(newProjectExpense);
    return newProjectExpense;
  }

  @Override
  @Transactional
  public ProjectExpense updateExpense(
      int projectId, int projectExpenseID, ProjectExpenseDTO projectExpense) {
    ProjectExpense existingProjectExpense =
        projectExpenseRepository.findById(projectExpenseID).orElse(null);
    if (existingProjectExpense == null) {
      throw new AppException(
          "updateExpense",
          this.getClass().getSimpleName(),
          "Project Expense with id: " + projectExpenseID + " couldn't be found",
          CommonError.PROJECT_EXPENSE_NOT_FOUND,
          HttpStatus.NOT_FOUND);
    }

    if (existingProjectExpense.getProject().getId() == projectId) {
      existingProjectExpense.setPrice(projectExpense.getPrice());
      existingProjectExpense.setName(projectExpense.getName());
      // we don't need to save the entity since we annotated @Transactional
      // but will call it to be more readable
      projectExpenseRepository.save(existingProjectExpense);
    } else {
      // it means expense doesn't belong to that project
      throw new AppException(
          "updateExpense",
          this.getClass().getSimpleName(),
          "Project Expense with id: " + projectExpenseID + " couldn't be found",
          CommonError.PROJECT_EXPENSE_NOT_FOUND,
          HttpStatus.NOT_FOUND);
    }
    return existingProjectExpense;
  }

  @Override
  @Transactional
  public void deleteExpense(int projectId, int projectExpenseID) {
    ProjectExpense existingProjectExpense =
        projectExpenseRepository.findById(projectExpenseID).orElse(null);
    if (existingProjectExpense == null) {
      throw new AppException(
          "deleteExpense",
          this.getClass().getSimpleName(),
          "Project Expense with id: " + projectExpenseID + " couldn't be found",
          CommonError.PROJECT_EXPENSE_NOT_FOUND,
          HttpStatus.NOT_FOUND);
    }
    Project project = existingProjectExpense.getProject();
    if (project.getId() == projectId) {
      existingProjectExpense.getProject().deleteProjectExpense(existingProjectExpense);
    } else {
      // it means expense doesn't belong to that project
      throw new AppException(
          "deleteExpense",
          this.getClass().getSimpleName(),
          "Project Expense with id: " + projectExpenseID + " couldn't be found",
          CommonError.PROJECT_EXPENSE_NOT_FOUND,
          HttpStatus.NOT_FOUND);
    }
  }
}
