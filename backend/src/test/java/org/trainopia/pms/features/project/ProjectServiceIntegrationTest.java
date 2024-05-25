package org.trainopia.pms.features.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.trainopia.pms.features.project.dto.ProjectDTO;
import org.trainopia.pms.features.project.dto.UpsertProjectDTO;
import org.trainopia.pms.features.projectDetails.dto.ProjectDetailsDTO;
import org.trainopia.pms.features.projectExpense.ProjectExpenseRepository;
import org.trainopia.pms.features.projectExpense.ProjectExpenseService;
import org.trainopia.pms.features.projectExpense.dto.ProjectExpenseDTO;
import org.trainopia.pms.utility.AppException;
import org.trainopia.pms.utility.CommonError;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProjectServiceIntegrationTest {
  private final ProjectServiceImpl projectService;
  private final ProjectExpenseService projectExpenseService;
  private final ProjectRepository projectRepository;
  private final ProjectExpenseRepository projectExpenseRepository;
  private Project createdProject;

  @Autowired
  public ProjectServiceIntegrationTest(
      ProjectServiceImpl projectService,
      ProjectExpenseService projectExpenseService,
      ProjectRepository projectRepository,
      ProjectExpenseRepository projectExpenseRepository) {
    this.projectService = projectService;
    this.projectExpenseService = projectExpenseService;
    this.projectRepository = projectRepository;
    this.projectExpenseRepository = projectExpenseRepository;
  }

  @BeforeEach
  void setup() {
    projectExpenseRepository.deleteAll();
    projectRepository.deleteAll();
  }

  void createProject() {
    UpsertProjectDTO projectDTO =
        new UpsertProjectDTO(
            "Test Project",
            18,
            60,
            100.0,
            "Test Location",
            new ProjectDetailsDTO("Test Description", "http://images.com/test"));

    createdProject = projectService.create(projectDTO);
    ProjectExpenseDTO projectExpenseDTO = new ProjectExpenseDTO("Test Expense", 10);
    projectExpenseService.createExpense(createdProject.getId(), projectExpenseDTO);
  }

  @Test
  void testCreateProject() {
    createProject();
    assertNotEquals(0, createdProject.getId());
    assertEquals("Test Project", createdProject.getTitle());
    assertEquals(18, createdProject.getMinAge());
    assertEquals(60, createdProject.getMaxAge());
    assertEquals(100.0, createdProject.getPrice());
    assertEquals("Test Location", createdProject.getLocation());
    assertEquals("Test Description", createdProject.getProjectDetails().getDescription());
    assertEquals("http://images.com/test", createdProject.getProjectDetails().getImagesFolderURL());
  }

  @Test
  void testFindById() {
    createProject();
    Project foundProject = projectService.findById(createdProject.getId());
    assertEquals(createdProject.getId(), foundProject.getId());
  }

  @Test
  void testFindById_NotFound() {
    AppException exception =
        assertThrows(
            AppException.class,
            () -> {
              // we delete before enter so id 1 is ok to test
              projectService.findById(1);
            });
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals(CommonError.PROJECT_NOT_FOUND, exception.getErrors().get(0));
  }

  @Test
  void testUpdateProject() {
    createProject();
    UpsertProjectDTO updatedProjectDTO =
        new UpsertProjectDTO(
            "Updated Project",
            21,
            65,
            150.0,
            "Updated Location",
            new ProjectDetailsDTO("Updated Description", "http://images.com/updated"));

    Project updatedProject = projectService.update(createdProject.getId(), updatedProjectDTO);
    assertEquals("Updated Project", updatedProject.getTitle());
    assertEquals(21, updatedProject.getMinAge());
    assertEquals(65, updatedProject.getMaxAge());
    assertEquals(150.0, updatedProject.getPrice());
    assertEquals("Updated Location", updatedProject.getLocation());
    assertEquals("Updated Description", updatedProject.getProjectDetails().getDescription());
    assertEquals(
        "http://images.com/updated", updatedProject.getProjectDetails().getImagesFolderURL());
  }

  @Test
  void testFindAllProjects() {
    createProject();
    createProject();
    createProject();
    Page<ProjectDTO> projects = projectService.findAll(0, 10, "id", "asc");
    assertEquals(3, projects.getTotalElements());
  }

  @Test
  void testDeleteById() {
    createProject();

    projectService.deleteById(createdProject.getId());
    AppException exception =
        assertThrows(
            AppException.class,
            () -> projectService.findById(createdProject.getId()));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals(CommonError.PROJECT_NOT_FOUND, exception.getErrors().get(0));
  }

  @Test
  void testDeleteById_NotFound() {
    AppException exception =
        assertThrows(
            AppException.class,
            () -> projectService.deleteById(999));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals(CommonError.PROJECT_COULD_NOT_BE_DELETED, exception.getErrors().get(0));
  }
}
