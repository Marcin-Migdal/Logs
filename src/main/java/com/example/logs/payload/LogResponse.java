package com.example.logs.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LogResponse {
    private Long id;
    private String date;
    private String activity;
    private Long activityTime;
}