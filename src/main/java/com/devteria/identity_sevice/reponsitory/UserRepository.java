package com.devteria.identity_sevice.reponsitory;

import com.devteria.identity_sevice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
