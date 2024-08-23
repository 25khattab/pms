package org.trainopia.pms.features.auth.oAuth2.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class only purpose is to mimic {@link DefaultOAuth2User}
 * because the attribute map is set to {@code Collections.unmodifiableMap(new LinkedHashMap<>(attributes));}
 *
 * <p>
 * and for that it can't be modified which is necessary to add user email especially from GitHub
 */
public class CustomOAuth2User implements OAuth2User {
    private final Set<GrantedAuthority> authorities;

    private final Map<String, Object> attributes;

    private final String name;

    public CustomOAuth2User(OAuth2User oAuth2User) {
        this.authorities = new LinkedHashSet<>(oAuth2User.getAuthorities());
        this.attributes = new LinkedHashMap<>(oAuth2User.getAttributes());
        this.name = oAuth2User.getName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return name;
    }
}
