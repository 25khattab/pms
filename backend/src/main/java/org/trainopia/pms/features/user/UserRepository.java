package org.trainopia.pms.features.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u join fetch u.userLoginData" +
           " where u.userLoginData.email=:email or u.userLoginData.username=:userName")
    Optional<User> findByEmailOrUserName(String email, String userName);
}
