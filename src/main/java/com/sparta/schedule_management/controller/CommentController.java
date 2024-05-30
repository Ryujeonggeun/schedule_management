package com.sparta.schedule_management.controller;

import com.sparta.schedule_management.dto.CommentDeleteRequestDto;
import com.sparta.schedule_management.dto.CommentRequestDto;
import com.sparta.schedule_management.dto.CommentResponseDto;
import com.sparta.schedule_management.service.CommentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //댓글 등록
    @PostMapping
    public CommentResponseDto createComment(@RequestBody @Valid CommentRequestDto commentRequestDto, BindingResult bindingResult) {

        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
        }
        return commentService.createComment(commentRequestDto);
    }

    //댓글 수정
    @PutMapping("/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id ,@RequestBody @Valid CommentRequestDto commentRequestDto,BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
        }
        return commentService.updateComment(id, commentRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id , @RequestBody @Valid CommentDeleteRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());

            }
        }
        return commentService.delete(id,requestDto);
    }
}
