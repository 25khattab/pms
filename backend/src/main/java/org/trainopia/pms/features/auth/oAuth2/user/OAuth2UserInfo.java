package org.trainopia.pms.features.auth.oAuth2.user;

import org.springframework.security.core.GrantedAuthority;
import org.trainopia.pms.features.auth.CustomUserDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class OAuth2UserInfo implements CustomUserDetails {

    protected final Map<String, Object> attributes;
    protected Set<GrantedAuthority> authorities;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;

    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String getEmail();

    public abstract String getImageUrl();

}
