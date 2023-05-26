package com.uade.ad.repository;

import com.uade.ad.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUsersByEmail(String email);
    boolean existsByEmail(String email);
}
