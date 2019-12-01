package com.example.logs.controller;


import com.example.logs.payload.ApiResponse;
import com.example.logs.payload.LogRequest;
import com.example.logs.payload.LogResponse;
import com.example.logs.payload.LogUpdateRequest;
import com.example.logs.security.CurrentUser;
import com.example.logs.security.UserPrincipal;
import com.example.logs.Facade.LogFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createLog(@Valid @RequestBody LogRequest logRequest, @CurrentUser UserPrincipal currentUser) {
        LogResponse logResponse = logFacade.createLog(logRequest, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pollId}")
                .buildAndExpand(logResponse.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Log Created Successfully"));
    }

    @GetMapping("/byId/{logId}")
    @PreAuthorize("hasRole('USER')")
    public LogResponse getLogByLogId(@PathVariable Long logId) {
        return logFacade.getLogByLogId(logId);
    }

    @GetMapping("/byDate/{date}")
    @PreAuthorize("hasRole('USER')")
    public List<LogResponse> getLogsByDate(@PathVariable String date, @CurrentUser UserPrincipal currentUser) {
        return logFacade.getLogsByDate(date, currentUser);
    }

    @DeleteMapping("/{logId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteById(@PathVariable Long logId) {
         logFacade.deleteById(logId);
         return ResponseEntity.ok("Log deleted");
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public LogResponse editLog(@Valid @RequestBody LogUpdateRequest logUpdateRequest){
        return logFacade.editLog(logUpdateRequest);
    }
}
