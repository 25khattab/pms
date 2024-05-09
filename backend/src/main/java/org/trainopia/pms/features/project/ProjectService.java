package org.trainopia.pms.features.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.trainopia.pms.features.project.dto.CreateProjectDTO;
import org.trainopia.pms.features.project.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
  Project findById(int id);

  Project findProxyById(int id);

  Project create(CreateProjectDTO createProjectDTO);

  Project update(int projectId, CreateProjectDTO createProjectDTO);

  Page<ProjectDTO> findAll(int pageNo, int pageSize, String sortBy, String sortDirection);

  void deleteById(int projectId);
}
