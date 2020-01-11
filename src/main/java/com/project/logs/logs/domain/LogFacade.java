package com.project.logs.logs.domain;

import com.project.logs.logs.dto.LogDto;
import com.project.logs.logs.exception.ResourceNotFoundException;
import com.project.logs.logs.model.Log;
import com.project.logs.logs.dto.LogRequest;
import com.project.logs.logs.dto.LogResponse;
import com.project.logs.logs.dto.LogUpdateRequest;
import com.project.logs.logs.repository.LogRepository;
import com.project.logs.users.repository.UserRepository;
import com.project.logs.base.security.UserPrincipal;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Builder
public class LogFacade {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(LogFacade.class);

    public LogResponse createLog(LogRequest logRequest, UserPrincipal currentUser) {
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

        return LogResponse.builder()
                .id(logDto.getId())
                .date(logDto.getDate())
                .activity(logDto.getActivity())
                .activityTime(logDto.getActivityTime())
                .build();
    }
}
