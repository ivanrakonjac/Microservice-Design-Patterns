package com.jwt.security.configuration;

import com.jwt.security.entities.UserEntity;
import com.jwt.security.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserEntityRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService(){
         return new UserDetailsService() {
            @Override
            public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException("User not found"));
            }
        };
    }

}
