package org.trainopia.pms.utility;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BaseDTO {
    private int id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BaseDTO(int id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
