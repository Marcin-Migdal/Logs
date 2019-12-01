package com.example.logs.Facade;

import com.example.logs.dto.LogDto;
import com.example.logs.exception.ResourceNotFoundException;
import com.example.logs.model.Log;
import com.example.logs.payload.LogRequest;
import com.example.logs.payload.LogResponse;
import com.example.logs.payload.LogUpdateRequest;
import com.example.logs.repository.LogRepository;
import com.example.logs.repository.UserRepository;
import com.example.logs.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogFacade {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(LogFacade.class);

    public LogResponse createLog( LogRequest logRequest, UserPrincipal currentUser) {
        Log log = Log.builder()
                .date(logRequest.getDate())
                .user(userRepository.getOne(currentUser.getId()))
                .activity(logRequest.getActivity())
                .activityTime(logRequest.getActivityTime())
                .build();

        logRepository.save(log);
        return mapLogDtoToLogResponse(log.dto());
    }

    public LogResponse getLogByLogId(Long logId) {
        LogDto log = logRepository.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("Log", "id", logId)).dto();

        return mapLogDtoToLogResponse(log);
    }

    public List<LogResponse> getLogsByDate(String date, UserPrincipal currentUser) {
        List<LogResponse> logResponse = new ArrayList<>();

        logRepository.findByDateAndUserId(date, currentUser.getId())
                .forEach((log -> logResponse.add(mapLogDtoToLogResponse(log.dto()))));

        return logResponse;
    }

    public void deleteById(Long logId) {
        logRepository.deleteById(logId);
    }

    public LogResponse editLog(LogUpdateRequest logUpdateRequest) {
        LogDto logDto = logRepository.findById(logUpdateRequest.getLogId())
                .orElseThrow(() -> new ResourceNotFoundException("Log", "id", logUpdateRequest.getLogId())).dto();

        logDto.setActivity(logUpdateRequest.getActivity());
        logDto.setActivityTime(logUpdateRequest.getActivityTime());

        logRepository.save(Log.fromDto(logDto));

        return mapLogDtoToLogResponse(logDto);
    }

    private LogResponse mapLogDtoToLogResponse(LogDto logDto) {
        LogResponse logResponse = LogResponse.builder()
                .id(logDto.getId())
                .date(logDto.getDate())
                .activity(logDto.getActivity())
                .activityTime(logDto.getActivityTime())
                .build();

        return logResponse;
    }
}
