package org.trainopia.pms.features.user.dto;

public record CreateUserDTO(
    String firstName, String lastName, String email, String password, String userName) {}
