package org.trainopia.pms.features.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trainopia.pms.features.project.dao.ProjectDAO;
import org.trainopia.pms.features.project.dto.CreateProjectDTO;
import org.trainopia.pms.features.project.dto.ProjectDTO;
import org.trainopia.pms.features.projectDetails.ProjectDetails;
import org.trainopia.pms.features.projectExpense.ProjectExpense;
import org.trainopia.pms.features.projectExpense.ProjectExpenseRepository;
import org.trainopia.pms.features.projectExpense.dto.ProjectExpenseDTO;
import org.trainopia.pms.utility.AppException;
import org.trainopia.pms.utility.CommonError;

@Service
public class ProjectServiceImpl implements ProjectService {

  private final ProjectDAO projectDAO;
  private final ProjectExpenseRepository projectExpenseRepository;

  @Autowired
  public ProjectServiceImpl(
      ProjectDAO projectDAO, ProjectExpenseRepository projectExpenseRepository) {
    this.projectDAO = projectDAO;
    this.projectExpenseRepository = projectExpenseRepository;
  }

  @Override
  public Project findById(int id) {
    Project project = projectDAO.findById(id).orElse(null);
    if (project == null) {
      throw new AppException(
          "findById",
          this.getClass().getSimpleName(),
          "Project with id: " + id + " couldn't be found",
          CommonError.PROJECT_NOT_FOUND,
          HttpStatus.NOT_FOUND);
    }
    return project;
  }

  @Override
  public Project create(CreateProjectDTO createProjectDTO) {
    Project newProject =
        new Project(
            createProjectDTO.getTitle(),
            createProjectDTO.getMinAge(),
            createProjectDTO.getMaxAge(),
            createProjectDTO.getPrice(),
            createProjectDTO.getLocation());
    ProjectDetails newProjectDetails =
        new ProjectDetails(
            createProjectDTO.getProjectDetails().getDescription(),
            createProjectDTO.getProjectDetails().getImagesFolderURL());
    newProject.setProjectDetails(newProjectDetails);
    projectDAO.save(newProject);
    return newProject;
  }

  @Override
  @Transactional
  public Project update(int projectId, CreateProjectDTO updateProjectDTO) {
    Project existingProject = findById(projectId);
    existingProject.setPrice(updateProjectDTO.getPrice());
    existingProject.setTitle(updateProjectDTO.getTitle());
    existingProject.setMinAge(updateProjectDTO.getMinAge());
    existingProject.setMaxAge(updateProjectDTO.getMaxAge());
    existingProject.setLocation(updateProjectDTO.getLocation());
    existingProject
        .getProjectDetails()
        .setDescription(updateProjectDTO.getProjectDetails().getDescription());
    existingProject
        .getProjectDetails()
        .setImagesFolderURL(updateProjectDTO.getProjectDetails().getImagesFolderURL());
    projectDAO.merge(existingProject);
    return existingProject;
  }

  @Override
  public Page<ProjectDTO> findAll(Pageable pageable) {
    return projectDAO.findAll(pageable);
  }

  @Override
  @Transactional
  public void deleteById(int projectId) {
    Project existingProject = findById(projectId);
    projectDAO.delete(existingProject);
  }

  @Override
  @Transactional
  public Project createExpense(int projectId, ProjectExpenseDTO projectExpense) {
    Project existingProject = findById(projectId);
    ProjectExpense newProjectExpense = new ProjectExpense();
    newProjectExpense.setName(projectExpense.getName());
    newProjectExpense.setPrice(projectExpense.getPrice());
    existingProject.addProjectExpense(newProjectExpense);
    return existingProject;
  }

  @Override
  @Transactional
  public Project updateExpense(
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
    return findById(projectId);
  }

  @Override
  @Transactional
  public void deleteExpense(int projectId, int projectExpenseID) {
    ProjectExpense existingProjectExpense =
        projectExpenseRepository.findById(projectExpenseID).orElse(null);
    if (existingProjectExpense == null) {
      throw new AppException(
          "findById",
          this.getClass().getSimpleName(),
          "Project Expense with id: " + projectExpenseID + " couldn't be found",
          CommonError.PROJECT_EXPENSE_NOT_FOUND,
          HttpStatus.NOT_FOUND);
    }
    Project project = findById(projectId);
    project.deleteProjectExpense(existingProjectExpense);
  }
}
