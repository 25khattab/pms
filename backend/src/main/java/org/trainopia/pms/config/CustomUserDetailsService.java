package org.trainopia.pms.config;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.trainopia.pms.features.auth.AuthProvider;
import org.trainopia.pms.features.auth.AuthUserDetails;
import org.trainopia.pms.features.user.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username).map(AuthUserDetails::new).orElseThrow(
            () -> new BadCredentialsException("Bad Credentials"));
    }

    public UserDetails loadUserByEmailWithProvider(String email, AuthProvider authProvider) {
        return userRepository.findByEmail(email).map(user -> new AuthUserDetails(user, authProvider)).orElseThrow(
            () -> new AuthenticationServiceException("Provider not found"));
    }
}
