package org.trainopia.pms.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component("delegateAuthenticationEntryPoint")
public class DelegateAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver exceptionResolver;

    public DelegateAuthenticationEntryPoint(
        @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {this.exceptionResolver = exceptionResolver;}

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
        throws IOException, ServletException {

        exceptionResolver.resolveException(request, response, null, authException);
    }

}
