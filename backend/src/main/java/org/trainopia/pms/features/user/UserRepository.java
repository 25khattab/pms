package org.trainopia.pms.features.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u left join fetch u.userLoginData uld left join fetch u.externalUsersLoginData euld" +
           " where uld.email=:email or euld.email=:email")
    Optional<User> findByEmail(String email);
}
