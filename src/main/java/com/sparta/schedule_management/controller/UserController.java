package com.sparta.schedule_management.controller;

import com.sparta.schedule_management.dto.SingUpRequestDto;
import com.sparta.schedule_management.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public  UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> singup(@RequestBody @Valid SingUpRequestDto requestDto, BindingResult bindingResult) {

        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
        }
        return userService.singup(requestDto);
    }
}
