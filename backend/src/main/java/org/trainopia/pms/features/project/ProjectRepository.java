package org.trainopia.pms.features.project;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
  @Override
  @Query("select p from Project p join fetch p.projectDetails ")
  Page<Project> findAll(Pageable pageable);

  Page<Project> findAllProjectedBy(Pageable pageable);
  @Override
  @Query(
      "FROM Project p "
          + "left join fetch  p.projectExpenses join fetch"
          + " p.projectDetails where p.id=:id")
  Optional<Project> findById(Integer id);
}
