package org.trainopia.pms.features.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trainopia.pms.features.project.dto.ProjectDTO;
import org.trainopia.pms.features.project.dto.UpsertProjectDTO;
import org.trainopia.pms.features.projectDetails.ProjectDetails;
import org.trainopia.pms.features.projectExpense.ProjectExpenseRepository;
import org.trainopia.pms.utility.AppException;
import org.trainopia.pms.utility.CommonError;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectExpenseRepository projectExpenseRepository;

    public ProjectServiceImpl(
        ProjectRepository projectRepository, ProjectExpenseRepository projectExpenseRepository) {
        this.projectRepository = projectRepository;
        this.projectExpenseRepository = projectExpenseRepository;
    }

    @Override
    public Project findById(int id) {
        Project project = projectRepository.findById(id).orElse(null);
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
    public Project findProxyById(int id) {
        return projectRepository.getReferenceById(id);
    }

    @Override
    public Project create(UpsertProjectDTO upsertProjectDTO) {
        Project newProject =
            new Project(
                upsertProjectDTO.getTitle(),
                upsertProjectDTO.getMinAge(),
                upsertProjectDTO.getMaxAge(),
                upsertProjectDTO.getPrice(),
                upsertProjectDTO.getLocation());
        ProjectDetails newProjectDetails =
            new ProjectDetails(
                upsertProjectDTO.getProjectDetails().getDescription(),
                upsertProjectDTO.getProjectDetails().getImagesFolderURL());
        newProject.setProjectDetails(newProjectDetails);
        projectRepository.save(newProject);
        return newProject;
    }

    @Override
    @Transactional
    public Project update(int projectId, UpsertProjectDTO updateProjectDTO) {
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
        projectRepository.save(existingProject);
        return existingProject;
    }

    @Override
    public Page<ProjectDTO> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable paging = PageRequest.of(pageNo, pageSize, sort);
        return projectRepository.findAllProjects(paging);
    }

    @Override
    @Transactional
    public void deleteById(int projectId) {
        projectExpenseRepository.deleteAllByProjectId(projectId);
        int affectedRows = projectRepository.deleteProjectById(projectId);
        if (affectedRows == 0) {
            throw new AppException(
                "deleteById",
                this.getClass().getSimpleName(),
                "Project with id: " + projectId + " couldn't be deleted",
                CommonError.PROJECT_COULD_NOT_BE_DELETED,
                HttpStatus.NOT_FOUND);
        }
    }
}
