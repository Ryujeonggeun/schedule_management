package com.sparta.schedule_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    //사용자 아이디
    @NotBlank
    private String userId;
    //사용자 이름
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]+&")
    private String username;
    //비밀번호
    @Size(min = 8, max = 15)
    @Pattern(regexp =  "^[a-zA-Z0-9]+$")
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String role; // "USER" 또는 "ADMIN" 값이 올 수 있음 , 소문자를 입력받아도 가능함
}
