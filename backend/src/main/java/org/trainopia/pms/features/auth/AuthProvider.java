package org.trainopia.pms.features.auth;

public enum AuthProvider {
    GOOGLE("google"),
    GITHUB("github"),
    FACEBOOK("facebook"),
    CREDENTIALS("credentials");

    public final String constant;

    AuthProvider(String constant) {
        this.constant = constant;
    }
}