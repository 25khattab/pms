package org.trainopia.pms.features.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.trainopia.pms.features.auth.dto.AuthenticationResponse;
import org.trainopia.pms.features.config.JwtService;
import org.trainopia.pms.features.user.User;
import org.trainopia.pms.features.user.UserService;
import org.trainopia.pms.features.user.dto.UpsertUserDTO;
import org.trainopia.pms.features.user.dto.UserLoginDTO;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService, JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse login(UserLoginDTO request) {
        Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String jwtToken = jwtService.generateToken((AuthUserDetails) authenticate.getPrincipal());
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }

    public AuthenticationResponse signUp(UpsertUserDTO upsertUserDTO) {
        User createdUser = userService.create(upsertUserDTO);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(upsertUserDTO.getUsername(), upsertUserDTO.getPassword()));
        UserDetails userDetails = new AuthUserDetails(createdUser);
        String jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }

}
