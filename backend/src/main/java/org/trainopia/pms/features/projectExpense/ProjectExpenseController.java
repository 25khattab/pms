package org.trainopia.pms.features.projectExpense;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.trainopia.pms.features.auth.accessRoles.IsProjectManager;
import org.trainopia.pms.features.projectExpense.dto.ProjectExpenseDTO;

@RestController
@RequestMapping("/api/v1/projects/{projectId}/expenses")
@IsProjectManager
public class ProjectExpenseController {

    ProjectExpenseService projectExpenseService;

    @Autowired
    public ProjectExpenseController(ProjectExpenseService projectExpenseService) {
        this.projectExpenseService = projectExpenseService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectExpense createProjectExpense(
        @Valid @PositiveOrZero @PathVariable int projectId,
        @Valid @RequestBody ProjectExpenseDTO projectExpense) {
        return projectExpenseService.createExpense(projectId, projectExpense);
    }

    @PutMapping("/{expenseId}")
    public ProjectExpense updateProjectExpense(
        @Valid @PositiveOrZero @PathVariable int projectId,
        @Valid @PositiveOrZero @PathVariable int expenseId,
        @Valid @RequestBody ProjectExpenseDTO projectExpense) {
        return projectExpenseService.updateExpense(projectId, expenseId, projectExpense);
    }

    @DeleteMapping("/{expenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProjectExpense(
        @Valid @PositiveOrZero @PathVariable int projectId,
        @Valid @PositiveOrZero @PathVariable int expenseId) {
        projectExpenseService.deleteExpense(projectId, expenseId);
    }
}
