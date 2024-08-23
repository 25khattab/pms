package org.trainopia.pms.features.projectExpense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectExpenseRepository extends JpaRepository<ProjectExpense, Integer> {

    @Modifying
    @Query("DELETE FROM ProjectExpense c WHERE c.project.id = :projectId")
    void deleteAllByProjectId(@Param("projectId") int projectId);

    @Modifying
    @Override
    @Query("DELETE FROM ProjectExpense")
    void deleteAll();
}
