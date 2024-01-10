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
import org.trainopia.pms.utility.AppException;
import org.trainopia.pms.utility.CommonError;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDAO projectDAO;

    @Autowired
    public ProjectServiceImpl(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Override
    public Project findById(int id) {
        Project project =
            projectDAO.findById(id).orElse(null);
        if (project == null) {
            throw new AppException("findById", this.getClass().getSimpleName(), "Project with id: " + id + " couldn't be found",
                                   CommonError.PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return project;
    }

    @Override
    public Project create(CreateProjectDTO createProjectDTO) {
        Project newProject = new Project(createProjectDTO.getTitle(), createProjectDTO.getMinAge(), createProjectDTO.getMaxAge(),
                                         createProjectDTO.getPrice(), createProjectDTO.getLocation());
        ProjectDetails newProjectDetails = new ProjectDetails(createProjectDTO.getProjectDetails().getDescription(),
                                                              createProjectDTO.getProjectDetails().getImagesFolderURL());
        newProject.setProjectDetails(newProjectDetails);
        projectDAO.save(newProject);
        return newProject;

    }

    @Override
    @Transactional
    public Page<ProjectDTO> findAll(Pageable pageable) {
        return projectDAO.findAll(pageable);
    }

}
