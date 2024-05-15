package com.sparta.schedule_management.dto;

import com.sparta.schedule_management.entity.Schedule;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String todo;
    private String manager;
    private LocalDateTime Date;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.todo = schedule.getTodo();
        this.manager = schedule.getManager();
        this.Date = schedule.getDate();
    }
}
