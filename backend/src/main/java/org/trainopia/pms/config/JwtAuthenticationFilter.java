package org.trainopia.pms.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.trainopia.pms.features.auth.AuthProvider;
import org.trainopia.pms.features.auth.CustomUserDetails;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService,
                                   @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
        throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String jwt = authHeader.substring(7).trim();
            String userIdentifier = jwtService.extractUsername(jwt);
            if (userIdentifier != null && !userIdentifier.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                String authProvider = jwtService.extractClaim(jwt, (claims) -> claims.get("provider", String.class));
                CustomUserDetails userDetails;
                if (AuthProvider.valueOf(authProvider).equals(AuthProvider.CREDENTIALS)) {
                    userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(userIdentifier);
                } else {
                    userDetails = (CustomUserDetails) userDetailsService.loadUserByEmailWithProvider(userIdentifier,
                                                                                                     AuthProvider.valueOf(authProvider));

                }
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                                                                                                            userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            filterChain.doFilter(request, response);

        } catch (Exception exception) {
            resolver.resolveException(request, response, null, exception);
        }
    }
}
