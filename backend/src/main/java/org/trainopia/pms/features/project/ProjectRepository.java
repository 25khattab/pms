package org.trainopia.pms.features.project;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
  @Override
  @Query("select p from Project p join fetch p.projectDetails ")
  Page<Project> findAll(Pageable pageable);
  
  @Override
  @Query(
      "FROM Project p "
          + "left join fetch  p.projectExpenses join fetch "
          + "p.projectDetails where p.id=:id")
  Optional<Project> findById(Integer id);

  @Modifying
  @Query("DELETE FROM Project p WHERE p.id = :projectId")
  int deleteProjectById(@Param("projectId") Integer projectId);
}
