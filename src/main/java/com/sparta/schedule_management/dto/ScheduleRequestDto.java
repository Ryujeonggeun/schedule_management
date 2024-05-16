package com.sparta.schedule_management.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class ScheduleRequestDto {
    private LocalDate date;
    private String title;
    private String todo;
    private String manager;
    private String password;


}


