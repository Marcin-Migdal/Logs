package com.project.logs.facade;

import com.project.logs.repository.LogRepository;
import com.project.logs.repository.UserRepository;

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
