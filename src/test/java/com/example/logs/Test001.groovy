package com.example.logs

import com.example.logs.facade.AuthFacade
import com.example.logs.facade.AuthFacadeCreator
import com.example.logs.facade.LogFacade
import com.example.logs.facade.LogFacadeCreator
import com.example.logs.model.User
import com.example.logs.payload.LogRequest
import com.example.logs.payload.LogResponse
import com.example.logs.payload.SignUpRequest
import com.example.logs.repository.LogRepository
import com.example.logs.repository.UserRepository
import com.example.logs.security.JwtTokenProvider
import com.example.logs.security.UserPrincipal
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class Test001 extends Specification {
    LogRepository logRepository = new LogInMemoryRepository()
    UserRepository userRepository = new UserInMemoryRepository()
    AuthenticationManager authenticationManager = Stub(AuthenticationManager.class)
    PasswordEncoder passwordEncoder = Stub(PasswordEncoder.class)
    JwtTokenProvider tokenProvider = Stub(JwtTokenProvider.class)

    LogFacade logFacade = LogFacadeCreator.createLogFacade(logRepository, userRepository)
    AuthFacade authFacade = AuthFacadeCreator.createAuthFacade(userRepository, authenticationManager, passwordEncoder, tokenProvider)

    def "Pierwszy test"() {
        given: ""
            User user = authFacade.registerUser(SignUpRequest.builder()
                    .email("jj@gmail.com")
                    .name("Jan Kowalski")
                    .password("Ala123!")
                    .username("jj")
                    .build())
            UserPrincipal userPrincipal = new UserPrincipal(user.getId(), "Jan Kowalski", "jj", "jj@gmail.com", "Ala123!")
            LogRequest logRequest = LogRequest.builder()
                    .date("2020-01-08")
                    .activity("Log 1")
                    .activityTime(123L)
                    .build()
        when: "user creates log"
            LogResponse logResponse = logFacade.createLog(logRequest, userPrincipal)
        then: "log is created"
            logResponse == LogResponse.builder()
                    .id( ID loga ) //TODO
                    .date("2020-01-08")
                    .activity("Log 1")
                    .activityTime(123L)
                    .build()
    }
}
