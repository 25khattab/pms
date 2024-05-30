package org.trainopia.pms.features.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.trainopia.pms.utility.AppAuthenticationException;
import org.trainopia.pms.utility.CommonError;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService,
                                   @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
        throws ServletException, IOException {
        if (request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userIdentifier;
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new AppAuthenticationException("doFilterInternal", this.getClass().getSimpleName(), "Token Not Found",
                                                     CommonError.JWT_TOKEN_NOT_FOUND, HttpStatus.UNAUTHORIZED);
            }

            jwt = authHeader.substring(7);
            userIdentifier = jwtService.extractUsername(jwt);
            if (userIdentifier != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userIdentifier);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                                                                                                            userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    filterChain.doFilter(request, response);
                }
            }

        } catch (Exception exception) {
            resolver.resolveException(request, response, null, exception);
        }
    }
}
