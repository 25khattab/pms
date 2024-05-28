package org.trainopia.pms.features.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.trainopia.pms.features.userLoginData.UserLoginData;
import org.trainopia.pms.utility.BaseEntity;

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

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private UserLoginData userLoginData;

  public User() {}

  public User(String firstName, String lastName, UserRole role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
  }

}
