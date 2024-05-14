package com.sparta.schedule_management.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Schedule {
    private Long id;
    private String title;
    private String todo;
    private String manager;
    private int password;
    private Date Date;
}
