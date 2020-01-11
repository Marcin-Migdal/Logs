package com.project.logs.logs.domain;

import com.project.logs.logs.repository.LogRepository;
import com.project.logs.users.repository.UserRepository;

public class LogFacadeCreator {

    private LogFacade logFacade;

    private LogRepository logRepository;

    private UserRepository userRepository;

    public static LogFacade createLogFacade(LogRepository logRepository, UserRepository userRepository) {
        return LogFacade.builder()
                .logRepository(logRepository)
                .userRepository(userRepository)
                .build();
    }

}
