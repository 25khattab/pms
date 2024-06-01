package org.trainopia.pms.features.project;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.trainopia.pms.features.auth.accessRoles.IsManager;
import org.trainopia.pms.features.auth.accessRoles.IsProjectManager;
import org.trainopia.pms.features.auth.accessRoles.IsVolunteer;
import org.trainopia.pms.features.project.dto.ProjectDTO;
import org.trainopia.pms.features.project.dto.UpsertProjectDTO;

@RestController
@RequestMapping("/api/v1/projects")
@IsProjectManager
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @IsVolunteer
    public Page<ProjectDTO> getAllProjects(
        @RequestParam(defaultValue = "0") int pageNo,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortDirection) {
        return projectService.findAll(pageNo, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/{projectId}")
    public Project getProjectById(@Valid @PositiveOrZero @PathVariable int projectId) {
        return projectService.findById(projectId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@Valid @RequestBody UpsertProjectDTO projectDTO) {
        return projectService.create(projectDTO);
    }

    @PutMapping("/{projectId}")
    public Project updateProject(
        @Valid @PositiveOrZero @PathVariable int projectId,
        @RequestBody UpsertProjectDTO projectDTO) {
        return projectService.update(projectId, projectDTO);
    }

    @DeleteMapping("/{projectId}")
    @IsManager
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@Valid @PositiveOrZero @PathVariable int projectId) {
        projectService.deleteById(projectId);
    }

    // TODO create multiDelete
}
