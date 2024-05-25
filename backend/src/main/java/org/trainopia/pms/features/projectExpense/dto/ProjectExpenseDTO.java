package org.trainopia.pms.features.projectExpense.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjectExpenseDTO {

  @NotBlank() private String name;

  @Positive private int price;
}
