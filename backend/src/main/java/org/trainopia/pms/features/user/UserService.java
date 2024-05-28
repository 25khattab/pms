package org.trainopia.pms.features.user;

import org.trainopia.pms.features.user.dto.UpsertUserDTO;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById();

    User create(UpsertUserDTO upsertUserDTO);
}
