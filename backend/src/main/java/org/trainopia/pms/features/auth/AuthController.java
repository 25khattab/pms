package org.trainopia.pms.features.auth;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.trainopia.pms.features.auth.dto.AuthenticationResponse;
import org.trainopia.pms.features.user.dto.UpsertUserDTO;
import org.trainopia.pms.features.user.dto.UserLoginDTO;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        return authService.login(userLoginDTO);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse signUp(@RequestBody @Valid UpsertUserDTO upsertUserDTO) {
        return authService.signUp(upsertUserDTO);
    }
}
