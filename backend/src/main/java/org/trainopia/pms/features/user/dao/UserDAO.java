package org.trainopia.pms.features.user.dao;

import org.trainopia.pms.features.user.User;
import org.trainopia.pms.features.user.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    public List<User> findAll();
    public List<User> findAllWithLoginData();
    public void save(User user);
    public void delete(User user);
    public Optional<User> findById(int id);

    public Optional<User> findByEmailOrUserName(String email,String userName);
}
