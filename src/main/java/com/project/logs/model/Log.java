package com.project.logs.model;

import com.project.logs.dto.LogDto;
import com.project.logs.model.audit.DateAudit;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "logs")
@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Log extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    Long id;

    String date;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Setter
    @NotBlank
    String activity;

    @Setter
    @NotNull
    Long activityTime;

    public static Log fromDto(LogDto logDto) {
        return Log.builder()
                .id(logDto.getId())
                .date(logDto.getDate())
                .user(logDto.getUser())
                .activity(logDto.getActivity())
                .activityTime(logDto.getActivityTime())
                .build();
    }

    public LogDto dto() {
        return LogDto.builder()
                .id(id)
                .date(date)
                .user(user)
                .activity(activity)
                .activityTime(activityTime)
                .build();
    }
}