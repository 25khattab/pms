package org.trainopia.pms.features.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query("SELECT p FROM Project p JOIN FETCH p.projectExpenses")
    List<Project> findAllWithExpenses();

    @Query("SELECT p FROM Project p JOIN FETCH p.projectExpenses where p.id = :id")
    Project findOneWithExpenses(@Param("id") int id);
}
