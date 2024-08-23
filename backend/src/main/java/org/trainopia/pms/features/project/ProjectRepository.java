package org.trainopia.pms.features.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.trainopia.pms.features.project.dto.ProjectDTO;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query(
        "SELECT NEW org.trainopia.pms.features.project.dto."
        + "ProjectDTO(p.id, p.createdAt, p.updatedAt, p.title, p.minAge, p.maxAge, p.price, p.location, p.projectDetails)"
        + " FROM Project p")
    Page<ProjectDTO> findAllProjects(Pageable pageable);

    @Override
    @Query(
        "FROM Project p "
        + "left join fetch  p.projectExpenses join fetch "
        + "p.projectDetails where p.id=:id")
    Optional<Project> findById(Integer id);

    @Modifying
    @Query("DELETE FROM Project p WHERE p.id = :projectId")
    int deleteProjectById(@Param("projectId") Integer projectId);

    @Override
    @Modifying
    @Query("DELETE FROM Project")
    void deleteAll();

}
