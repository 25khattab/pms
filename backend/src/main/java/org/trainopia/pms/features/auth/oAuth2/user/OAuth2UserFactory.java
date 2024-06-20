package org.trainopia.pms.features.auth.oAuth2.user;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.trainopia.pms.features.auth.AuthProvider;

import java.util.Map;

public class OAuth2UserFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.constant)) {
            return new GoogleOAuth2User(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.GITHUB.constant)) {
            return new GithubOAuth2User(attributes);
        } else {
            throw new OAuth2AuthenticationException("Invalid registration ID: " + registrationId);
        }

    }
}
