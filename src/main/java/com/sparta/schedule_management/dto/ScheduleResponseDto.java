package com.sparta.schedule_management.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String todo;
    private String manager;
    private int password;
    private Date Date;
}
