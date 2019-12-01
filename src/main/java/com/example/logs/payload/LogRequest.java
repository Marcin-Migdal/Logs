package com.example.logs.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogRequest {
    private String date;
    private String activity;
    private Long activityTime;
}
