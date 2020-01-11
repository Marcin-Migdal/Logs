package com.project.logs.users

import com.project.logs.base.security.JwtTokenProvider
import com.project.logs.users.domain.UserFacade
import com.project.logs.users.domain.UserFacadeCreator
import com.project.logs.users.dto.SignUpRequest
import com.project.logs.users.model.User
import com.project.logs.users.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserSpec extends Specification {
    UserRepository userRepository = new UserInMemoryRepository()
    AuthenticationManager authenticationManager = Stub(AuthenticationManager.class)
    PasswordEncoder passwordEncoder = Stub(PasswordEncoder.class)
    JwtTokenProvider tokenProvider = Stub(JwtTokenProvider.class)

    UserFacade userFacade = UserFacadeCreator.createUserFacade(userRepository, authenticationManager, passwordEncoder, tokenProvider)

    def "user should be able to register"() {
        when: "user is registering"
            User user = userFacade.registerUser(SignUpRequest.builder()
                    .email("jj@gmail.com")
                    .name("Jan Kowalski")
                    .password("Ala123!")
                    .username("jj")
                    .build())
        then: "user is registered"
            userRepository.existsById(user.getId())
    }
}
