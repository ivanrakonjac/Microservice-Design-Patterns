package com.jwt.security.auth;

import com.jwt.security.auth.dtos.AuthenticationRequest;
import com.jwt.security.auth.dtos.AuthenticationResponse;
import com.jwt.security.auth.dtos.RegisterRequest;
import com.jwt.security.configuration.JwtService;
import com.jwt.security.entities.Role;
import com.jwt.security.entities.UserEntity;
import com.jwt.security.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserEntityRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){

        UserEntity newUser = UserEntity.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(newUser);

        String newUserToken = jwtService.generateToken(newUser);

        log.info("New user registered successfully with username: " + newUser.getUsername());

        return new AuthenticationResponse(newUserToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // If I came here user is authenticated
        UserEntity newAuthUser = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String newUserToken = jwtService.generateToken(newAuthUser);

        log.info("User authenticated successfully with username: " + newAuthUser.getUsername());

        return new AuthenticationResponse(newUserToken);
    }

}
