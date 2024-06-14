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
public class UserLoginDTO {
    @NotNull(message = "email shouldn't be null")
    @NotEmpty(message = "email shouldn't be empty")
    @Email
    private String email;

    @NotNull(message = "password shouldn't be null")
    @NotEmpty(message = "password shouldn't be empty")
    private String password;
}
