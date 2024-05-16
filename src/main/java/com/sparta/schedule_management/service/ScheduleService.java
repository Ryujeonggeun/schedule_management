package com.sparta.schedule_management.service;

import com.sparta.schedule_management.dto.ScheduleRequestDto;
import com.sparta.schedule_management.dto.ScheduleResponseDto;
import com.sparta.schedule_management.entity.Schedule;
import com.sparta.schedule_management.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(scheduleRequestDto);

        //DB저장
        Schedule savedSchedule = scheduleRepository.save(schedule);

        //Entity -> ResponseDto
        ScheduleResponseDto responseDto = new ScheduleResponseDto(savedSchedule);
        return responseDto;
    }


    public List<ScheduleResponseDto> getSchedules() {
        // Map TO List
        return scheduleRepository.findAllByOrderByDateAsc().stream()
                .map(ScheduleResponseDto::new).toList();
    }

    public List<ScheduleResponseDto> getDateSchedules(LocalDate date) {
      return scheduleRepository.findAllByDate(date).stream().map(ScheduleResponseDto::new).toList();

    }

    @Transactional
    public Long updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        //해당 스케줄이 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);

        //비밀번호 일치하는지 확인하기
        if (schedule.getPassword().equals(scheduleRequestDto.getPassword())) {
            //스케줄 수정
            schedule.update(scheduleRequestDto);
            return schedule.getId();
        } else {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }
    }

    public Long deleteSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        //해당 스케줄이 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);

        if (schedule.getPassword().equals(scheduleRequestDto.getPassword())) {
            //해당 스케줄 삭제하기
            scheduleRepository.delete(schedule);
            return id;
        } else {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }
    }


    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));
    }


}


