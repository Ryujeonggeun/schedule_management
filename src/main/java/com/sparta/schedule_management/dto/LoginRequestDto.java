package com.sparta.schedule_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotBlank(message = "사용자명을 입력하세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
}
