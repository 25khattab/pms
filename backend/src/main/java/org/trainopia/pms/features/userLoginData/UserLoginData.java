package org.trainopia.pms.features.userLoginData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.trainopia.pms.features.user.User;
import org.trainopia.pms.utility.BaseEntity;

@Entity
@Table(name = "user_login_data")
public class UserLoginData extends BaseEntity {
    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name="password")
    @JsonIgnore
    private String password;

    @OneToOne(mappedBy = "userLoginData" , fetch = FetchType.LAZY)
    private User user;

    public UserLoginData() {
    }

    public UserLoginData(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
