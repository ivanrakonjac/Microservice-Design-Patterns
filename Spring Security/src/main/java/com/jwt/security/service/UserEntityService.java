package com.jwt.security.service;

import com.jwt.security.entities.UserEntity;
import com.jwt.security.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserEntityService {

    private final UserEntityRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }


}
