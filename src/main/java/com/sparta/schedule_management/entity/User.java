package com.sparta.schedule_management.entity;

import com.sparta.schedule_management.dto.SignUpRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = true)
    private  String username;

    @Column(nullable = false)
    private  String password;
    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;
    private LocalDate createdAt;

    public User(SignUpRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.nickname = requestDto.getNickname();
        this.role = UserRoleEnum.valueOf(requestDto.getRole().toUpperCase()); //role 값을 대문자로 변환
        this.createdAt = LocalDate.now();
    }
}

