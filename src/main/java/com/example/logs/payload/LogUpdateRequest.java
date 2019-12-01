package com.example.logs.payload;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogUpdateRequest {
    private Long logId;
    private String activity;
    private Long activityTime;
}
