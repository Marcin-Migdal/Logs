package com.example.logs

import com.example.logs.facade.AuthFacade
import com.example.logs.facade.AuthFacadeCreator
import com.example.logs.model.User
import com.example.logs.payload.SignUpRequest
import com.example.logs.repository.UserRepository
import com.example.logs.security.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class AuthSpec extends Specification {
    UserRepository userRepository = new UserInMemoryRepository()
    AuthenticationManager authenticationManager = Stub(AuthenticationManager.class)
    PasswordEncoder passwordEncoder = Stub(PasswordEncoder.class)
    JwtTokenProvider tokenProvider = Stub(JwtTokenProvider.class)

    AuthFacade authFacade = AuthFacadeCreator.createAuthFacade(userRepository, authenticationManager, passwordEncoder, tokenProvider)

    def "user"() {
        given: ""

        when: ""
            User user = authFacade.registerUser(SignUpRequest.builder()
                    .email("jj@gmail.com")
                    .name("Jan Kowalski")
                    .password("Ala123!")
                    .username("jj")
                    .build())
        then: "user is registered"
            userRepository.existsById(user.getId())
    }
}
