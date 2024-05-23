package org.trainopia.pms.features.projectExpense;

import org.trainopia.pms.features.projectExpense.dto.ProjectExpenseDTO;

public interface ProjectExpenseService {
  ProjectExpense createExpense(int projectId, ProjectExpenseDTO projectExpense);

  ProjectExpense updateExpense(
      int projectId, int projectExpenseID, ProjectExpenseDTO projectExpense);

  void deleteExpense(int projectId, int projectExpenseID);
}
