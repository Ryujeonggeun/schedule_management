package com.sparta.schedule_management.service;

import com.sparta.schedule_management.dto.CommentRequestDto;
import com.sparta.schedule_management.dto.CommentResponseDto;
import com.sparta.schedule_management.entity.Comment;
import com.sparta.schedule_management.entity.Schedule;
import com.sparta.schedule_management.repository.CommentRepository;
import com.sparta.schedule_management.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentService(CommentRepository commentRepository, ScheduleRepository scheduleRepository) {
        this.commentRepository = commentRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public CommentResponseDto createComment(CommentRequestDto requestDto) {

        //선택한 일정이 DB에 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(requestDto.getRequestScheduleId()).orElseThrow(()
                -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
                );

            Comment comment = new Comment(requestDto.getUserName(), requestDto.getContent());
            // 댓글이 속할 일정 설정
            comment.setSchedule(schedule);

            //DB저장
            Comment savedComment = commentRepository.save(comment);

            return new CommentResponseDto(savedComment);

    }
}
