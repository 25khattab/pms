package org.trainopia.pms.features.project;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.trainopia.pms.features.project.dto.ProjectDTO;
import org.trainopia.pms.features.project.dto.UpsertProjectDTO;
import org.trainopia.pms.features.projectDetails.ProjectDetails;
import org.trainopia.pms.features.projectDetails.dto.ProjectDetailsDTO;
import org.trainopia.pms.features.projectExpense.ProjectExpenseRepository;
import org.trainopia.pms.utility.AppException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectExpenseRepository projectExpenseRepository;
    @InjectMocks
    private ProjectServiceImpl projectService;
    private Project project;
    private UpsertProjectDTO upsertProjectDTO;

    @BeforeEach
    void setUp() {
        project = new Project("Test Title", 10, 20, 19.99, "maadi");
        project.setProjectDetails(new ProjectDetails("aaaa", "/test/folder"));
        upsertProjectDTO =
            new UpsertProjectDTO(
                "Test Title", 10, 20, 19.99, "maadi", new ProjectDetailsDTO("aaaa", "/test/folder"));
    }

    @Test
    public void Create_project_success() {
        when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);

        Project result = projectService.create(upsertProjectDTO);

        Assertions.assertThat(result.getTitle()).isEqualTo(upsertProjectDTO.getTitle());

        verify(projectRepository, times(1)).save(Mockito.any(Project.class));
    }

    @Test
    public void Update_project_with_valid_id_success() {
        project.setTitle("Updated Test Title");
        upsertProjectDTO.setTitle("Update Test Title");

        when(projectRepository.findById(1)).thenReturn(Optional.ofNullable(project));
        when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);

        Project result = projectService.update(1, upsertProjectDTO);

        Assertions.assertThat(result.getTitle()).isEqualTo(upsertProjectDTO.getTitle());
        verify(projectRepository, times(1)).findById(1);
        verify(projectRepository, times(1)).save(Mockito.any(Project.class));
    }

    @Test
    public void Update_project_with_invalid_id_throws_exception() {
        project.setTitle("Updated Test Title");
        upsertProjectDTO.setTitle("Update Test Title");

        when(projectRepository.findById(1)).thenReturn(Optional.empty());

        AppException res =
            assertThrows(AppException.class, () -> projectService.update(1, upsertProjectDTO));

        Assertions.assertThat(res.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(projectRepository, times(1)).findById(1);
        verify(projectRepository, times(0)).save(Mockito.any(Project.class));
    }

    @Test
    public void Search_for_project_with_valid_id() {
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));

        Project result = projectService.findById(1);

        Assertions.assertThat(result.getTitle()).isEqualTo(project.getTitle());
        verify(projectRepository, times(1)).findById(1);
    }

    @Test
    public void Search_for_project_with_invalid_id_throws_exception() {
        when(projectRepository.findById(1)).thenReturn(Optional.empty());

        AppException res = assertThrows(AppException.class, () -> projectService.findById(1));

        Assertions.assertThat(res.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(projectRepository, times(1)).findById(1);
    }

    @Test
    public void Search_for_all_projects_return_page() {
        Page<ProjectDTO> projects = Mockito.mock(Page.class);

        when(projectRepository.findAllProjects(Mockito.any(Pageable.class))).thenReturn(projects);

        Page<ProjectDTO> projectDTOPage = projectService.findAll(0, 10, "id", "ASC");

        Assertions.assertThat(projectDTOPage.getPageable()).isEqualTo(projects.getPageable());
        verify(projectRepository, times(1)).findAllProjects(Mockito.any(Pageable.class));
    }

    @Test
    public void Delete_project_with_valid_id() {
        when(projectRepository.deleteProjectById(1)).thenReturn(1);

        projectService.deleteById(1);

        verify(projectExpenseRepository, times(1)).deleteAllByProjectId(1);
        verify(projectRepository, times(1)).deleteProjectById(1);
    }

    @Test
    public void Delete_project_with_invalid_id_throws_exception() {
        when(projectRepository.deleteProjectById(1)).thenReturn(0);

        assertThrows(AppException.class, () -> projectService.deleteById(1));
        verify(projectExpenseRepository, times(1)).deleteAllByProjectId(1);
        verify(projectRepository, times(1)).deleteProjectById(1);
    }
}
