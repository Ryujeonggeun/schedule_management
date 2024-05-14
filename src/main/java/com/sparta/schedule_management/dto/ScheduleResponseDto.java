package com.sparta.schedule_management.dto;

import com.sparta.schedule_management.entity.Schedule;
import lombok.Getter;

import java.util.Date;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String todo;
    private String manager;
    private String password;
    private Date Date;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.todo = schedule.getTodo();
        this.manager = schedule.getManager();
        this.password = schedule.getPassword();
        this.Date = schedule.getDate();
    }
}
