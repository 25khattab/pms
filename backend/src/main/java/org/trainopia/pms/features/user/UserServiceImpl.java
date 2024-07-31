package org.trainopia.pms.features.user;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trainopia.pms.features.auth.oAuth2.user.OAuth2UserInfo;
import org.trainopia.pms.features.user.dto.UpsertUserDTO;
import org.trainopia.pms.features.user.externalUserLoginData.ExternalUserLoginData;
import org.trainopia.pms.features.user.externalUserLoginData.ExternalUserLoginDataRepository;
import org.trainopia.pms.features.user.userLoginData.UserLoginData;
import org.trainopia.pms.features.user.userLoginData.UserLoginDataRepository;
import org.trainopia.pms.utility.AppException;
import org.trainopia.pms.utility.CommonError;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserLoginDataRepository userLoginDataRepository;
    private final ExternalUserLoginDataRepository externalUserLoginDataRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserLoginDataRepository userLoginDataRepository,
                           ExternalUserLoginDataRepository externalUserLoginDataRepository,
                           PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userLoginDataRepository = userLoginDataRepository;
        this.externalUserLoginDataRepository = externalUserLoginDataRepository;
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
        userRepository.findByEmail(upsertUserDTO.getEmail()).ifPresent(user -> {
            throw new AppException("create", this.getClass().getSimpleName(), "email must be unique", CommonError.USER_EXISTS,
                                   HttpStatus.BAD_REQUEST);
        });
        User newUser = new User(upsertUserDTO.getFirstName(), upsertUserDTO.getLastName(), UserRole.VOLUNTEER);
        String encryptedPassword = bCryptPasswordEncoder.encode(upsertUserDTO.getPassword());
        userRepository.save(newUser);
        UserLoginData userLoginData = new UserLoginData(upsertUserDTO.getEmail(), encryptedPassword, newUser);
        userLoginDataRepository.save(userLoginData);
        newUser.setUserLoginData(userLoginData);
        return newUser;
    }

    @Override
    @Transactional
    public User linkOrCreateFromProvider(@NonNull OAuth2UserInfo oAuth2UserInfo) {
        // check if a user exist with the provided email
        User user = userRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);
        if (user != null) {
            // if user exists we then check if the provider is unique or already exist
            Optional<ExternalUserLoginData> existingUserWithSameProvider = user.getExternalUsersLoginData().stream().filter(
                externalData -> externalData.getProvider() == oAuth2UserInfo.getAuthProvider()).findFirst();
            if (existingUserWithSameProvider.isEmpty()) {
                ExternalUserLoginData externalUserLoginData = new ExternalUserLoginData(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getAuthProvider(),
                                                                                        user);
                user.addExternalUsersLoginData(externalUserLoginData);
            }
            return user;
        } else {
            User newUser = new User(oAuth2UserInfo.getFirstName(), oAuth2UserInfo.getLastName(), UserRole.VOLUNTEER);
            userRepository.save(newUser);
            ExternalUserLoginData externalUserLoginData = new ExternalUserLoginData(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getAuthProvider(),
                                                                                    newUser);
            externalUserLoginDataRepository.save(externalUserLoginData);
            newUser.addExternalUsersLoginData(externalUserLoginData);
            return newUser;
        }

    }

}
