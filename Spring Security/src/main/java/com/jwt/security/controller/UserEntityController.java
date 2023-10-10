package com.jwt.security.controller;

import com.jwt.security.entities.UserEntity;
import com.jwt.security.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserEntityController {

    private final UserEntityService userService;

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserEntity>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/user", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserEntity>> getUsers_USER() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/admin", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserEntity>> getUsers_ADMIN() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
