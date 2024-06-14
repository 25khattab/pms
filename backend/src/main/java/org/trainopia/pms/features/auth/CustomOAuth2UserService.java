package org.trainopia.pms.features.auth;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestTemplate;
import org.trainopia.pms.features.auth.oAuth2.OAuth2Provider;
import org.trainopia.pms.features.auth.oAuth2.user.CustomOAuth2User;

import java.util.List;
import java.util.Map;

/**
 * The point of this class is to edit the OAuth2User
 * <p></p>
 * ex: GitHub doesn't return email attribute by default, so we need to fetch it
 * and add it to the attributes
 */

public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    //

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        CustomOAuth2User oAuth2User = new CustomOAuth2User(super.loadUser(userRequest));

        if (userRequest.getClientRegistration().getRegistrationId().equals(OAuth2Provider.GITHUB.constant)) {
            String email = getEmailFromGitHub(userRequest.getAccessToken().getTokenValue());
            if (email == null) {
                throw new OAuth2AuthenticationException("email not found");
            }
            // Add the email attribute to the user's attributes
            Map<String, Object> attributes = oAuth2User.getAttributes();
            attributes.put("email", email);
        }
        return oAuth2User;
    }

    private String getEmailFromGitHub(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.github.com/user/emails";
        List<Map<String, Object>> emails = restTemplate.exchange(
            url, HttpMethod.GET, new HttpEntity<>(createHeaders(accessToken)), List.class).getBody();

        if (emails != null) {
            for (Map<String, Object> email : emails) {
                if (Boolean.TRUE.equals(email.get("primary")) && Boolean.TRUE.equals(email.get("verified"))) {
                    return (String) email.get("email");
                }
            }
        }

        return null;
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }

}
