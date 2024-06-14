package org.trainopia.pms.features.auth.oAuth2.user;

import org.springframework.security.core.GrantedAuthority;
import org.trainopia.pms.features.auth.oAuth2.OAuth2Provider;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GithubOAuth2User extends OAuth2UserInfo {

    public GithubOAuth2User(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getFirstName() {
        return Optional.ofNullable(attributes.get("given_name").toString())
                       .orElse("FirstName");
    }

    @Override
    public String getLastName() {
        return Optional.ofNullable(attributes.get("family_name").toString())
                       .orElse("LastName");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.GOOGLE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

}
