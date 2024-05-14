package com.sparta.schedule_management.entity;


import com.sparta.schedule_management.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String title;
    private String todo;
    private String manager;
    private String password;
    private Date Date;

    public Schedule(ScheduleRequestDto scheduleRequestDto) {
        this.title = scheduleRequestDto.getTitle();
        this.todo = scheduleRequestDto.getTodo();
        this.manager = scheduleRequestDto.getManager();
        this.password = scheduleRequestDto.getPassword();
        this.Date = scheduleRequestDto.getDate();
    }
}
