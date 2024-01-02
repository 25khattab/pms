package org.trainopia.pms.features.project;

import java.util.List;

public interface ProjectService {
    Project findById(int id);

    Project save(Project project);

    List<Project> findAll();

    List<Project> findAllWithExpenses();

    Project findWithExpensesById(int id);

    Project update(Project project);

    void deleteById(int id);
}
