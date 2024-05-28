package org.trainopia.pms.features.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertUserDTO {
  @NotNull(message = "firstName shouldn't be null")
  @NotEmpty(message = "firstName shouldn't be empty")
  String firstName;

  @NotNull(message = "lastName shouldn't be null")
  @NotEmpty(message = "lastName shouldn't be empty")
  String lastName;

  @NotNull(message = "email shouldn't be null")
  @NotEmpty(message = "email shouldn't be empty")
  @Email
  String email;

  @NotNull(message = "username shouldn't be null")
  @NotEmpty(message = "username shouldn't be empty")
  String username;

  @NotNull(message = "password shouldn't be null")
  @NotEmpty(message = "password shouldn't be empty")
  // TODO add more constraints on password
  String password;
}
