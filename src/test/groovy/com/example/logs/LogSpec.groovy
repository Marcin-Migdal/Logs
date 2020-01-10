package com.example.logs

import com.example.logs.facade.LogFacade
import com.example.logs.facade.LogFacadeCreator
import com.example.logs.payload.LogRequest
import com.example.logs.payload.LogResponse
import com.example.logs.repository.LogRepository
import com.example.logs.repository.UserRepository
import com.example.logs.security.UserPrincipal
import spock.lang.Specification

class LogSpec extends Specification {
    LogRepository logRepository = new LogInMemoryRepository()
    UserRepository userRepository = new UserInMemoryRepository()

    LogFacade logFacade = LogFacadeCreator.createLogFacade(logRepository, userRepository)

    def "user should be able to create log"() {
        given: "there is user"
            UserPrincipal userPrincipal = new UserPrincipal(1L, "Jan Kowalski", "jj", "jj@gmail.com", "Ala123!")
            LogRequest logRequest = LogRequest.builder()
                    .date("2020-01-08")
                    .activity("Log 1")
                    .activityTime(123L)
                    .build()
        when: "user creates log"
            LogResponse logResponse = logFacade.createLog(logRequest, userPrincipal)
        then: "log is created"
            logResponse == LogResponse.builder()
                    .id(1L)
                    .date("2020-01-08")
                    .activity("Log 1")
                    .activityTime(123L)
                    .build()
    }
}
