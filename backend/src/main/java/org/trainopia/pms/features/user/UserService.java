package org.trainopia.pms.features.user;

import org.trainopia.pms.features.auth.oAuth2.user.OAuth2UserInfo;
import org.trainopia.pms.features.user.dto.UpsertUserDTO;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById();

    User create(UpsertUserDTO upsertUserDTO);

    User linkOrCreateFromProvider(OAuth2UserInfo oAuth2UserInfo);

    //TODO

    //    User update(UpsertUserDTO upsertUserDTO);

    //    void updatePassword();

    //    /**
    //     * Unlink provider if the user has userLoginData, if not exception will be thrown.
    //     */
    //    void unlinkProvider();


}
