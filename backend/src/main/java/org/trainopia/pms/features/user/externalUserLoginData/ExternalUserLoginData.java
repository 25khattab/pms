package org.trainopia.pms.features.user.externalUserLoginData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.trainopia.pms.features.auth.AuthProvider;
import org.trainopia.pms.features.user.User;
import org.trainopia.pms.utility.BaseEntity;

@Entity
@Setter
@Getter
@Table(name = "external_user_login_data")
public class ExternalUserLoginData extends BaseEntity {

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "OAuth2Provider")
    private AuthProvider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ExternalUserLoginData() {}

    public ExternalUserLoginData(String email, AuthProvider provider, User user) {
        this.email = email;
        this.provider = provider;
        this.user = user;
    }

}
