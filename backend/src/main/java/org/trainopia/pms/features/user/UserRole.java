package org.trainopia.pms.features.user;

public enum UserRole {
    MANAGER("MANAGER"),
    PROJECT_MANAGER("PROJECT_MANAGER"),
    VOLUNTEER("VOLUNTEER");
    public final String constant;

    UserRole(String constant) {
        this.constant = constant;
    }
}
