package com.project.logs.users.domain;

import com.project.logs.users.repository.UserRepository;
import com.project.logs.base.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthFacadeCreator {

    private AuthFacade authFacade;

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider tokenProvider;

    public static AuthFacade createAuthFacade(UserRepository userRepository, @Autowired AuthenticationManager authenticationManager,
                                              @Autowired PasswordEncoder passwordEncoder, @Autowired JwtTokenProvider tokenProvider) {
        return AuthFacade.builder()
                .userRepository(userRepository)
                .authenticationManager(authenticationManager)
                .passwordEncoder(passwordEncoder)
                .tokenProvider(tokenProvider)
                .build();
    }
}
