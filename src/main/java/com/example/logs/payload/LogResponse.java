package com.example.logs.payload;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class LogResponse {
    private Long id;
    private String date;
    private String activity;
    private Long activityTime;
}