package org.trainopia.pms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authorization.method.PrePostTemplateDefaults;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.trainopia.pms.features.auth.CustomOAuth2UserService;
import org.trainopia.pms.features.user.UserRole;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final DelegateAuthenticationEntryPoint delegateAuthenticationEntryPoint;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          DelegateAuthenticationEntryPoint delegateAuthenticationEntryPoint,
                          AuthenticationSuccessHandler authenticationSuccessHandler) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.delegateAuthenticationEntryPoint = delegateAuthenticationEntryPoint;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    static RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withRolePrefix("")
                                .role(UserRole.MANAGER.name()).implies(UserRole.PROJECT_MANAGER.name())
                                .role(UserRole.PROJECT_MANAGER.name()).implies(UserRole.VOLUNTEER.name()).build();
    }

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

    @Bean
    static PrePostTemplateDefaults prePostTemplateDefaults() {
        return new PrePostTemplateDefaults();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize.requestMatchers("/",
                                                                          "/css/*",
                                                                          "/error",
                                                                          "/favicon.ico").permitAll()
                                                         .requestMatchers("/api/v1/auth/**").permitAll()
                                                         .anyRequest().authenticated())
            .oauth2Login(l -> l.successHandler(authenticationSuccessHandler).userInfoEndpoint(ui -> ui.userService(new CustomOAuth2UserService())))
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exc -> exc.authenticationEntryPoint(delegateAuthenticationEntryPoint));
        return http.build();
    }

}


