package org.trainopia.pms.features.auth.oAuth2;

public enum OAuth2Provider {
    GOOGLE("google"),
    GITHUB("github"),
    FACEBOOK("facebook");

    public final String constant;

    OAuth2Provider(String constant) {
        this.constant = constant;
    }
}
