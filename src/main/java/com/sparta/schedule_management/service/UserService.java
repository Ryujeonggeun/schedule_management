package com.sparta.schedule_management.service;

import com.sparta.schedule_management.dto.LoginRequestDto;
import com.sparta.schedule_management.dto.SignUpRequestDto;
import com.sparta.schedule_management.entity.User;
import com.sparta.schedule_management.entity.UserRoleEnum;
import com.sparta.schedule_management.jwt.JwtUtil;
import com.sparta.schedule_management.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    public ResponseEntity<String> singup(SignUpRequestDto requestDto) {

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

    public ResponseEntity<String> login(LoginRequestDto requestDto, HttpServletResponse res) {
        // 요청으로부터 사용자명과 비밀번호를 가져옴
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // DB에서 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // 비밀번호 검증
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        //토큰 생성
        String token = jwtUtil.generateToken(username);

        jwtUtil.addJwtToCookie(res, token);

        //토큰 검증
        jwtUtil.getUsernameFromToken(token);
        jwtUtil.validateToken(token);


        // 로그인 성공 시 반환할 메시지와 상태코드를 설정하여 반환
        return ResponseEntity.ok("로그인에 성공했습니다.");

    }
}
