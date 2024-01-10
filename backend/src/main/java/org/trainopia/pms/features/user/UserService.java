package org.trainopia.pms.features.user;

import org.trainopia.pms.features.user.dto.CreateUserDTO;
import org.trainopia.pms.features.user.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> findAll();

    //    public List<User> findAllWithLoginData ();
    public User findById();

    public User create(CreateUserDTO createUserDTO);
    //   public User findByIdWithLoginData ();
}

