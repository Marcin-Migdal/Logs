package com.example.logs.controller;

import com.example.logs.facade.LogFacade;
import com.example.logs.payload.ApiResponse;
import com.example.logs.payload.LogRequest;
import com.example.logs.payload.LogResponse;
import com.example.logs.payload.LogUpdateRequest;
import com.example.logs.security.CurrentUser;
import com.example.logs.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogFacade logFacade;
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @PostMapping
    public ResponseEntity<?> createLog(@Valid @RequestBody LogRequest logRequest, @CurrentUser UserPrincipal currentUser) {
        LogResponse logResponse = logFacade.createLog(logRequest, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/byId/{logId}")
                .buildAndExpand(logResponse.getId()).toUri();

        System.out.println(location.toString());

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Log Created Successfully"));
    }

    @GetMapping("/byId/{logId}")
    public LogResponse getLogByLogId(@PathVariable Long logId) {
        return logFacade.getLogByLogId(logId);
    }

    @GetMapping("/byDate/{date}")
    public List<LogResponse> getLogsByDate(@PathVariable String date, @CurrentUser UserPrincipal currentUser) {
        return logFacade.getLogsByDate(date, currentUser);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<?> deleteById(@PathVariable Long logId) {
        logFacade.deleteById(logId);
        return ResponseEntity.ok("Log deleted");
    }

    @PutMapping
    public LogResponse editLog(@Valid @RequestBody LogUpdateRequest logUpdateRequest) {
        return logFacade.editLog(logUpdateRequest);
    }
}
