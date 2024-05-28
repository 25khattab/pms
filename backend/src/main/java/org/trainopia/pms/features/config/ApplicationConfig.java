package org.trainopia.pms.features.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.trainopia.pms.features.auth.AuthUserDetails;
import org.trainopia.pms.features.user.UserRepository;
import org.trainopia.pms.utility.AppException;
import org.trainopia.pms.utility.CommonError;

@Configuration
public class ApplicationConfig {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmailOrUserName("", username).map(AuthUserDetails::new).orElseThrow(
            () -> new AppException("userDetailsService", this.getClass().getSimpleName(), "User Not Found", CommonError.USER_NOT_FOUND,
                                   HttpStatus.BAD_REQUEST));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
