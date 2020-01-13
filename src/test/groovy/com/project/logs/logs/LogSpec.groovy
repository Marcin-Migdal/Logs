package com.project.logs.logs

import com.project.logs.base.security.UserPrincipal
import com.project.logs.logs.domain.LogFacade
import com.project.logs.logs.domain.LogFacadeCreator
import com.project.logs.logs.dto.LogRequest
import com.project.logs.logs.dto.LogResponse
import com.project.logs.logs.repository.LogRepository
import com.project.logs.users.domain.UserFacade
import spock.lang.Specification

class LogSpec extends Specification {
    LogRepository logRepository = new LogInMemoryRepository()
    UserFacade userFacade =  Mock(UserFacade.class)

    LogFacade logFacade = LogFacadeCreator.createLogFacade(logRepository, userFacade)

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
