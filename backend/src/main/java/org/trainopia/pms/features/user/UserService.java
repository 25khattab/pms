package org.trainopia.pms.features.user;

import java.util.List;
import org.trainopia.pms.features.user.dto.CreateUserDTO;

public interface UserService {

  List<User> findAll();

  //    public List<User> findAllWithLoginData ();
  User findById();

  User create(CreateUserDTO createUserDTO);

  Boolean getExistEmail(String email);
  //   public User findByIdWithLoginData ();
}
