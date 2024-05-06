package org.trainopia.pms.features.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.trainopia.pms.features.user.dao.UserDAO;
import org.trainopia.pms.features.user.dto.CreateUserDTO;
import org.trainopia.pms.features.userLoginData.UserLoginData;
import org.trainopia.pms.utility.AppException;
import org.trainopia.pms.utility.CommonError;

@Service
public class UserServiceImpl implements UserService {

  UserDAO userDAO;
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserServiceImpl(UserDAO userDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userDAO = userDAO;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public List<User> findAll() {
    List<User> result = userDAO.findAll();
    return result;
  }

  @Override
  public User findById() {
    return null;
  }

  @Override
  public User create(CreateUserDTO createUserDTO) {
    User existingUser =
        userDAO.findByEmailOrUserName(createUserDTO.email(), createUserDTO.userName()).orElse(null);
    if (existingUser != null) {
      throw new AppException(
          "create",
          this.getClass().getSimpleName(),
          "User Exist",
          CommonError.USER_EXISTS,
          HttpStatus.BAD_REQUEST);
    }
    User newUser =
        new User(createUserDTO.firstName(), createUserDTO.lastName(), UserRole.VOLUNTEER);
    String encryptedPassword = bCryptPasswordEncoder.encode(createUserDTO.password());
    UserLoginData userLoginData =
        new UserLoginData(createUserDTO.userName(), createUserDTO.email(), encryptedPassword);
    newUser.setUserLoginData(userLoginData);
    userDAO.save(newUser);
    return newUser;
  }

  @Override
  public Boolean getExistEmail(String email) {
    return null;
  }

  //    @Override
  //    public List<User> findAllWithLoginData() {
  //        return userRepository.findAllWithLoginData();
  //    }
  //
  //    @Override
  //    public UserDTO findById() {
  //        return null;
  //    }
  //
  //    @Override
  //    public User findByIdWithLoginData() {
  //        return null;
  //    }
}
