package com.sparta.schedule_management.dto;

import com.sparta.schedule_management.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private long id;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private Long scheduleId;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.userName = comment.getUserName();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.scheduleId = comment.getSchedule().getId();
    }
}
