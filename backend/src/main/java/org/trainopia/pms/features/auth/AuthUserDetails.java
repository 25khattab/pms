package org.trainopia.pms.features.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.trainopia.pms.features.user.User;
import org.trainopia.pms.features.user.externalUserLoginData.ExternalUserLoginData;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class AuthUserDetails implements CustomUserDetails {
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;
    private AuthProvider authProvider;

    public AuthUserDetails(User user) {
        this.username = user.getUserLoginData().getEmail();
        this.password = user.getUserLoginData().getPassword();
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
        this.authProvider = AuthProvider.CREDENTIALS;
    }

    public AuthUserDetails(User user, AuthProvider authProvider) {
        Optional<ExternalUserLoginData> externalUser = user.getExternalUsersLoginData().stream().filter(u -> u.getProvider().equals(authProvider))
                                                           .findFirst();
        externalUser.ifPresent(u -> {
            this.username = u.getEmail();
            this.authProvider = authProvider;
            this.password = "";
            this.authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
        });
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public AuthProvider getAuthProvider() {
        return authProvider;
    }
}
