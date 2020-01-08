package com.example.logs.facade;

import com.example.logs.repository.LogRepository;
import com.example.logs.repository.UserRepository;

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
