package com.project.logs.logs.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LogRequest {
    private String date;
    private String activity;
    private Long activityTime;
}
