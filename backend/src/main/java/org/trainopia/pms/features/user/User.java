package org.trainopia.pms.features.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.trainopia.pms.features.user.externalUserLoginData.ExternalUserLoginData;
import org.trainopia.pms.features.user.userLoginData.UserLoginData;
import org.trainopia.pms.utility.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.VOLUNTEER;

    @Column(name="verified")
    private Boolean verified;

    @Column(name="refresh_token_version")
    private int refreshTokenVersion;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserLoginData userLoginData;

    @OneToMany(mappedBy = "user",
               orphanRemoval = true, // to remove the entity entirely when setting project null
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<ExternalUserLoginData> externalUsersLoginData = new ArrayList<>();
    ;

    public User() {}

    public User(String firstName, String lastName, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public void addExternalUsersLoginData(ExternalUserLoginData externalUserLoginData) {
        externalUsersLoginData.add(externalUserLoginData);
        externalUserLoginData.setUser(this);
    }

    public void removeExternalUsersLoginData(ExternalUserLoginData externalUserLoginData) {
        externalUsersLoginData.remove(externalUserLoginData);
        externalUserLoginData.setUser(null);
    }

}
