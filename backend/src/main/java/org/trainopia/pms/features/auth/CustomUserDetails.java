package org.trainopia.pms.features.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {

    AuthProvider getAuthProvider();
}
