package org.trainopia.pms.features.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.trainopia.pms.config.JwtService;
import org.trainopia.pms.features.auth.dto.AuthenticationResponse;
import org.trainopia.pms.features.auth.oAuth2.user.OAuth2UserFactory;
import org.trainopia.pms.features.auth.oAuth2.user.OAuth2UserInfo;
import org.trainopia.pms.features.user.UserService;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

@Component
public class OAuth2SuccessAuthenticationHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    public OAuth2SuccessAuthenticationHandler(JwtService jwtService, ObjectMapper objectMapper, UserService userService) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken authenticationToken) {
            OAuth2UserInfo oAuth2UserInfo = OAuth2UserFactory.getOAuth2UserInfo(authenticationToken.getAuthorizedClientRegistrationId(),
                                                                                authenticationToken.getPrincipal().getAttributes());
            userService.linkOrCreateFromProvider(oAuth2UserInfo);
            String token = jwtService.generateToken(oAuth2UserInfo);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String responseBody = objectMapper.writeValueAsString(AuthenticationResponse.builder().accessToken(token).build());
            response.getWriter().write(responseBody);
        } else {
            throw new AuthenticationException("Authentication Failed");
        }
    }
}
