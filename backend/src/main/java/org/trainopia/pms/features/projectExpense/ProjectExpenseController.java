package org.trainopia.pms.features.projectExpense;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.trainopia.pms.features.project.Project;
import org.trainopia.pms.features.project.ProjectService;
import org.trainopia.pms.features.projectExpense.dto.ProjectExpenseDTO;

@RestController
@RequestMapping("/projects/{projectId}/expenses")
public class ProjectExpenseController {

  ProjectService projectService;

  @Autowired
  public ProjectExpenseController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public Project createProjectExpense(
      @Valid @PositiveOrZero @PathVariable int projectId,
      @Valid @RequestBody ProjectExpenseDTO projectExpense) {
    return projectService.createExpense(projectId, projectExpense);
  }

  @PutMapping("/{expenseId}")
  public Project createProjectExpense(
      @Valid @PositiveOrZero @PathVariable int projectId,
      @Valid @PositiveOrZero @PathVariable int expenseId,
      @Valid @RequestBody ProjectExpenseDTO projectExpense) {
    return projectService.updateExpense(projectId, expenseId, projectExpense);
  }

  @DeleteMapping("/{expenseId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProjectExpense(
      @Valid @PositiveOrZero @PathVariable int projectId,
      @Valid @PositiveOrZero @PathVariable int expenseId) {
    projectService.deleteExpense(projectId, expenseId);
  }
}
