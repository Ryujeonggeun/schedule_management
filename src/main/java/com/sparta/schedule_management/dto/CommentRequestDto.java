package com.sparta.schedule_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String content;
    private long requestScheduleId;
}
