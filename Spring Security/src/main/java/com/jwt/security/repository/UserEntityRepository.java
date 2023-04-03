package com.jwt.security.repository;

import com.jwt.security.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

}
