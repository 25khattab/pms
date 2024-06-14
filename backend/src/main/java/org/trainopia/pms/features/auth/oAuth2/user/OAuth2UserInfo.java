package org.trainopia.pms.features.auth.oAuth2.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.trainopia.pms.features.auth.oAuth2.OAuth2Provider;

import java.util.Map;

public abstract class OAuth2UserInfo implements UserDetails {


    protected final Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String getEmail();

    public abstract String getImageUrl();

    public abstract OAuth2Provider getProvider();

}
