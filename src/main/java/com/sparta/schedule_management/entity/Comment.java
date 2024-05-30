package com.sparta.schedule_management.entity;

import com.sparta.schedule_management.dto.CommentRequestDto;
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
    // 일정아이디??


    // 댓글 : n  스케줄 아이디: 1     N: 1 관계
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment(String userName, String content)  {
        this.userName = userName;
        this.content = content;
        this.createdAt = LocalDateTime.now();

    }


}

