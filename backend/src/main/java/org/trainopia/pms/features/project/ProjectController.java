package org.trainopia.pms.features.project;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.trainopia.pms.features.project.dto.UpsertProjectDTO;
import org.trainopia.pms.features.project.dto.ProjectDTO;

@RestController
@RequestMapping("/projects")
public class ProjectController {

  private final ProjectService projectService;

  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @GetMapping
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
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProject(@Valid @PositiveOrZero @PathVariable int projectId) {
    projectService.deleteById(projectId);
  }
}
