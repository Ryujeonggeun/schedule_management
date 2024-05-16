package com.sparta.schedule_management.entity;


import com.sparta.schedule_management.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity //JPA가 관리할 수 있는 Entitiy 클래스 지정
@Getter
@Setter
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "todo", nullable = false, length = 200)
    private String todo;
    @Column(name = "manager", nullable = false)
    private String manager;
    @Column(name = "password", nullable = false, length = 20)
    private String password;


    public Schedule(ScheduleRequestDto scheduleRequestDto) {
        this.title = scheduleRequestDto.getTitle();
        this.date = scheduleRequestDto.getDate();
        this.todo = scheduleRequestDto.getTodo();
        this.manager = scheduleRequestDto.getManager();
        this.password = scheduleRequestDto.getPassword();
    }

    public void update(ScheduleRequestDto scheduleRequestDto) {
        this.title = scheduleRequestDto.getTitle();
        this.date = scheduleRequestDto.getDate();
        this.todo = scheduleRequestDto.getTodo();
        this.manager = scheduleRequestDto.getManager();
    }
}
