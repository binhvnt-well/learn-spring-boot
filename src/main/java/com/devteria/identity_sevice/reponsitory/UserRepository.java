package com.devteria.identity_sevice.reponsitory;

import com.devteria.identity_sevice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsUserByUsername(String username);

    User getUserById(String id);

    Optional<User> findByUsername(String username);
}
