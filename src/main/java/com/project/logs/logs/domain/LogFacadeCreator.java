package com.project.logs.logs.domain;

import com.project.logs.logs.repository.LogRepository;
import com.project.logs.users.domain.UserFacade;
import com.project.logs.users.repository.UserRepository;

public class LogFacadeCreator {

    private LogFacade logFacade;

    private LogRepository logRepository;

    private UserFacade userFacade;

    public static LogFacade createLogFacade(LogRepository logRepository, UserFacade userFacade) {
        return LogFacade.builder()
                .logRepository(logRepository)
                .userFacade(userFacade)
                .build();
    }

}
