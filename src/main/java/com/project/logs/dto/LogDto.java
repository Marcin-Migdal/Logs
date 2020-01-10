package com.project.logs.dto;

import com.project.logs.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PUBLIC)
public class LogDto {
    Long id;
    String date;
    User user;
    String activity;
    Long activityTime;
}