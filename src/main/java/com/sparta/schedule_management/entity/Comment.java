package com.sparta.schedule_management.entity;

import com.sparta.schedule_management.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private Long scheduleId;
    // 일정아이디??


    // 댓글 : n  스케줄 아이디: 1     N: 1 관계
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    //스케줄 아이디 받아오기
    public Comment( Schedule schedule) {
        this.scheduleId = schedule.getId();
        }
}

