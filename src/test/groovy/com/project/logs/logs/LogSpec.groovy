package com.project.logs.logs

import com.project.logs.base.security.UserPrincipal
import com.project.logs.logs.domain.LogFacade
import com.project.logs.logs.domain.LogFacadeCreator
import com.project.logs.logs.dto.LogRequest
import com.project.logs.logs.dto.LogResponse
import com.project.logs.logs.dto.LogUpdateRequest
import com.project.logs.logs.exception.ResourceNotFoundException
import com.project.logs.logs.repository.LogRepository
import com.project.logs.users.domain.UserFacade
import com.project.logs.users.model.User
import spock.lang.Specification

class LogSpec extends Specification {
    LogRepository logRepository = new LogInMemoryRepository()
    UserFacade userFacade = Mock(UserFacade.class)

    LogFacade logFacade = LogFacadeCreator.createLogFacade(logRepository, userFacade)

    void userFacadeGetUser() {
        userFacade.getUser(_ as Long) >> {
            return new User(it[0] as Long, "Jan Kowalski", "jj", "jj@gmail.com", "Ala123!")
        }
    }

    def "user should be able to create log"() {
        given: "there is user"
            userFacadeGetUser()
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

    def "user should be able to edit a log"() {
        given: "there is user and log"
            userFacadeGetUser()
            UserPrincipal userPrincipal = new UserPrincipal(1L, "Jan Kowalski", "jj", "jj@gmail.com", "Ala123!")
            LogRequest logRequest = LogRequest.builder()
                    .date("2020-01-08")
                    .activity("Log 1")
                    .activityTime(123L)
                    .build()
            logFacade.createLog(logRequest, userPrincipal)

        when: "user edit log"
            LogResponse editedLog = logFacade.editLog(LogUpdateRequest.builder()
                    .logId(1L)
                    .activity("Log Edit")
                    .activityTime(60L)
                    .build())
        then: "log is updated"
            editedLog == LogResponse.builder()
                    .id(1L)
                    .date("2020-01-08")
                    .activity("Log Edit")
                    .activityTime(60L)
                    .build()
    }

    def "user should be able to delete log"() {
        given: "there is user and log"
            userFacadeGetUser()
            UserPrincipal userPrincipal = new UserPrincipal(1L, "Jan Kowalski", "jj", "jj@gmail.com", "Ala123!")
            LogRequest logRequest = LogRequest.builder()
                    .date("2020-01-08")
                    .activity("Log 1")
                    .activityTime(123L)
                    .build()
            LogResponse logResponse = logFacade.createLog(logRequest, userPrincipal)
        when: "user delete log"
            logFacade.deleteById(logResponse.getId())
            logFacade.getLogByLogId(logResponse.getId())
        then: "log is deleted"
            thrown ResourceNotFoundException

    }

    def "user should be able to get many logs by date"() {
        given: "there is user and more than one log"
            userFacadeGetUser()
            UserPrincipal userPrincipal = new UserPrincipal(1L, "Jan Kowalski", "jj", "jj@gmail.com", "Ala123!")
            LogRequest logRequest = LogRequest.builder()
                    .date("2020-01-08")
                    .activity("Log 1")
                    .activityTime(123L)
                    .build()

            LogRequest logRequest2 = LogRequest.builder()
                .date("2020-01-08")
                .activity("Log 2")
                .activityTime(60L)
                .build()

            logFacade.createLog(logRequest, userPrincipal)
            logFacade.createLog(logRequest2, userPrincipal)
        when: "user try to get logs"
            List<LogResponse> logsByDate = logFacade.getLogsByDate("2020-01-08", userPrincipal)
        then: "user get logs"
            logsByDate.size() == 2
    }
}
