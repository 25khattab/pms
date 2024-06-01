package org.trainopia.pms.features.auth.accessRoles;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority(T(org.trainopia.pms.features.user.UserRole).VOLUNTEER)")
public @interface IsVolunteer {}
