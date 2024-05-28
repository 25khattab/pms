package org.trainopia.pms.features.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trainopia.pms.features.user.dto.UpsertUserDTO;
import org.trainopia.pms.features.userLoginData.UserLoginData;
import org.trainopia.pms.features.userLoginData.UserLoginDataRepository;
import org.trainopia.pms.utility.AppException;
import org.trainopia.pms.utility.CommonError;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserLoginDataRepository userLoginDataRepository;
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserLoginDataRepository userLoginDataRepository,
                           PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userLoginDataRepository = userLoginDataRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById() {
        return null;
    }

    @Override
    @Transactional
    public User create(UpsertUserDTO upsertUserDTO) {
        userRepository.findByEmailOrUserName(upsertUserDTO.getEmail(), upsertUserDTO.getUsername()).ifPresent(user -> {
            throw new AppException("create", this.getClass().getSimpleName(), "email and username must be unique", CommonError.USER_EXISTS, HttpStatus.BAD_REQUEST);
        });
        User newUser = new User(upsertUserDTO.getFirstName(), upsertUserDTO.getLastName(), UserRole.VOLUNTEER);
        String encryptedPassword = bCryptPasswordEncoder.encode(upsertUserDTO.getPassword());
        userRepository.save(newUser);
        UserLoginData userLoginData = new UserLoginData(upsertUserDTO.getUsername(), upsertUserDTO.getEmail(), encryptedPassword, newUser);
        userLoginDataRepository.save(userLoginData);
        newUser.setUserLoginData(userLoginData);
        return newUser;
    }

}
