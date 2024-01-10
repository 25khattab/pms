package org.trainopia.pms.features.user.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.trainopia.pms.features.user.User;
import org.trainopia.pms.features.user.dto.UserDTO;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //    @Query("SELECT u from User u join fetch u.userLoginData where u.id=:id")
    //    public Optional<User> findByIdWithLoginData(int id){
    //
    //    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Query("")
    public List<User> findAllWithLoginData() {
        return entityManager.createQuery("SELECT u from User u join fetch u.userLoginData", User.class).getResultList();
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
        ;
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    public Optional<User> findById(int id) {
        //        result = entityManager.createQuery("""
        //                                               SELECT NEW org.trainopia.pms.features.user.dto.UserDTO
        //                                                       (u.id,u.firstName,u.lastName,u.role,u.createdAt,u.updatedAt)
        //                                                        from User u where u.id=:id
        //                                               """, User.class).setParameter("id", id).getSingleResult();
        return Optional.of(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByEmailOrUserName(String email, String userName) {

        TypedQuery<User> query =
            entityManager.createQuery("""
                                          select u from User u join fetch u.userLoginData
                                          where u.userLoginData.email=:email or u.userLoginData.userName=:userName """,
                                      User.class);
        query.setParameter("email", email);
        query.setParameter("userName", userName);
        List<User> result = query.getResultList();
        return result.size() == 0 ? Optional.empty() : Optional.of(result.get(0));

    }

}