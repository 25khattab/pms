package org.trainopia.pms.features.user.dto;

import org.trainopia.pms.features.user.UserRole;

import java.time.LocalDateTime;

public record UserDTO(int id,
    String firstName,
    String lastName,
    UserRole role,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
