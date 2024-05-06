package org.trainopia.pms.features.user.dto;

import java.time.LocalDateTime;
import org.trainopia.pms.features.user.UserRole;

public record UserDTO(
    int id,
    String firstName,
    String lastName,
    UserRole role,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
