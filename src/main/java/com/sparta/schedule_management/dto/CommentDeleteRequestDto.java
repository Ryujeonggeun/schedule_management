package com.sparta.schedule_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentDeleteRequestDto {
    @NotBlank
    private String userName;
    @NotNull
    private long requestScheduleId;
}
