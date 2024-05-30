package org.trainopia.pms.features.project;

import org.springframework.data.domain.Page;
import org.trainopia.pms.features.project.dto.ProjectDTO;
import org.trainopia.pms.features.project.dto.UpsertProjectDTO;

public interface ProjectService {
    Project findById(int id);

    Project findProxyById(int id);

    Project create(UpsertProjectDTO upsertProjectDTO);

    Project update(int projectId, UpsertProjectDTO upsertProjectDTO);

    Page<ProjectDTO> findAll(int pageNo, int pageSize, String sortBy, String sortDirection);

    void deleteById(int projectId);
}
