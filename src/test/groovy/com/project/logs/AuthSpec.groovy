package com.project.logs


import com.project.logs.facade.AuthFacade
import com.project.logs.facade.AuthFacadeCreator
import com.project.logs.model.User
import com.project.logs.payload.SignUpRequest
import com.project.logs.repository.UserRepository
import com.project.logs.security.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class AuthSpec extends Specification {
    UserRepository userRepository = new UserInMemoryRepository()
    AuthenticationManager authenticationManager = Stub(AuthenticationManager.class)
    PasswordEncoder passwordEncoder = Stub(PasswordEncoder.class)
    JwtTokenProvider tokenProvider = Stub(JwtTokenProvider.class)

    AuthFacade authFacade = AuthFacadeCreator.createAuthFacade(userRepository, authenticationManager, passwordEncoder, tokenProvider)

    def "user should be able to register"() {
        when: "user is registering"
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
