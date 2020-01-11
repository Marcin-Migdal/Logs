package com.project.logs.logs.dto;

import com.project.logs.users.model.User;
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