package com.sparta.schedule_management.service;

import com.sparta.schedule_management.dto.SingUpRequestDto;
import com.sparta.schedule_management.entity.User;
import com.sparta.schedule_management.entity.UserRoleEnum;
import com.sparta.schedule_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<String> singup(SingUpRequestDto requestDto) {

        // userName 중복 확인
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 username 입니다.");
        }

        //사용자 ROLE 확인
        // Role 값이 유효한지 확인
        if (!isValidRole(requestDto.getRole())) {
            throw new IllegalArgumentException("유효하지 않은 역할입니다.");
        }

        User user = new User(requestDto);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입에 성공하였습니다,");
        
        
    }

    private boolean isValidRole(String role) {
        try {
            UserRoleEnum.valueOf(role.toUpperCase());
            return true;
        }catch (IllegalArgumentException e) {
            return false;
        }
    }
}
