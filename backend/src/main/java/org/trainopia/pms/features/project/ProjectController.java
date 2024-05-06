package org.trainopia.pms.features.project;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.trainopia.pms.features.project.dto.CreateProjectDTO;
import org.trainopia.pms.features.project.dto.ProjectDTO;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public Page<ProjectDTO> getAllProjects(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "3") int size,
                                           @RequestParam(defaultValue = "id,desc") String[] sort) {
        List<Order> orders = new ArrayList<>();
        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Order(_sort[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Order(sort[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sort[0]));
        }
        Pageable paging = PageRequest.of(page, size, Sort.by(orders));

        return projectService.findAll(paging);
    }

    @GetMapping("/{projectId}")
    public Project getProjectById(@Valid @PositiveOrZero @PathVariable int projectId) {
        return projectService.findById(projectId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@Valid @RequestBody CreateProjectDTO projectDTO) {
        return projectService.create(projectDTO);
    }

    //TODO make partial update
    @PutMapping("/{projectId}")
    public Project updateProject(@Valid @PositiveOrZero @PathVariable int projectId,@RequestBody CreateProjectDTO projectDTO) {
        return projectService.update(projectId,projectDTO);
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@Valid @PositiveOrZero @PathVariable int projectId) {
        projectService.deleteById(projectId);
    }

}
