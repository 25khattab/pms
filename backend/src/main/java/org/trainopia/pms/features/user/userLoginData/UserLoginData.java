package org.trainopia.pms.features.user.userLoginData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.trainopia.pms.features.user.User;
import org.trainopia.pms.utility.BaseEntity;

@Entity
@Setter
@Getter
@Table(name = "user_login_data")
public class UserLoginData extends BaseEntity {

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private User user;

    public UserLoginData() {}

    public UserLoginData(String email, String password, User user) {
        this.email = email;
        this.password = password;
        this.user = user;
    }

}
