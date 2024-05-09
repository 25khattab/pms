package org.trainopia.pms.utility;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseDTO {
  private int id;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public BaseDTO() {}

  public BaseDTO(int id, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
