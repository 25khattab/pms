package org.trainopia.pms.features.projectExpense;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectExpenseRepository extends JpaRepository<ProjectExpense, Integer> {

//  @Override
//  @Query("SELECT pe from ProjectExpense pe join fetch pe.project join fetch pe.project.projectDetails where pe.id = :integer")
//  Optional<ProjectExpense> findById(Integer integer);
}
