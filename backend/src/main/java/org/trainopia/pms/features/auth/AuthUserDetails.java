package org.trainopia.pms.features.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.trainopia.pms.features.user.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class AuthUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final Set<GrantedAuthority> authorities;

    public AuthUserDetails(User user) {
        this.username = user.getUserLoginData().getUsername();
        this.password = user.getUserLoginData().getPassword();
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
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

}
