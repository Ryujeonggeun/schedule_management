package com.sparta.schedule_management.dto;

import com.sparta.schedule_management.entity.Schedule;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private LocalDate date;
    private String title;
    private String todo;
    private String manager;
    private LocalDate createdAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.date = schedule.getDate();
        this.title = schedule.getTitle();
        this.todo = schedule.getTodo();
        this.manager = schedule.getManager();
        this.createdAt = schedule.getCreatedAt();
    }
}
