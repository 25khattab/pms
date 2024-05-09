package org.trainopia.pms.features.user.dao;

import java.util.List;
import java.util.Optional;
import org.trainopia.pms.features.user.User;

public interface UserDAO {
  List<User> findAll();

  List<User> findAllWithLoginData();

  void save(User user);

  void delete(User user);

  Optional<User> findById(int id);

  Optional<User> findByEmailOrUserName(String email, String userName);
}
