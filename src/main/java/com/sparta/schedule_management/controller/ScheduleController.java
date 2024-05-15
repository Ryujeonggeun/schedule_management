package com.sparta.schedule_management.controller;

import com.sparta.schedule_management.dto.ScheduleRequestDto;
import com.sparta.schedule_management.dto.ScheduleResponseDto;
import com.sparta.schedule_management.entity.Schedule;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {


    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(scheduleRequestDto);

        //Schedule Max ID Check
        Long maxId = scheduleList.size() > 0 ? Collections.max(scheduleList.keySet()) + 1 : 1;
        schedule.setId(maxId);

        //DB저장
        scheduleList.put(maxId, schedule);

        //Entity -> ResponseDto
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
        return responseDto;
    }


    @GetMapping
    public List<ScheduleResponseDto> getSchedules() {
        // Map TO List
        List<ScheduleResponseDto> responseList = scheduleList.values().stream()
                .map(ScheduleResponseDto::new).toList();
        return responseList;
    }


    @PutMapping("{id}/{password}")
    public Long updateSchedule(@PathVariable Long id, @PathVariable String password, @RequestBody ScheduleRequestDto scheduleRequestDto) {

        //해당 스케줄이 DB에 존재하는지 확인
        if (scheduleList.containsKey(id)) {
            Schedule schedule = scheduleList.get(id);

            //비밀번호 일치하는지 확인하기
            if (schedule.getPassword().equals(password)) {
                //스케줄 수정
                schedule.update(scheduleRequestDto);
                return schedule.getId();
            } else {
                throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    @DeleteMapping("{id}/{password}")
    public Long deleteSchedule(@PathVariable Long id, @PathVariable String password) {

        //해당 스케줄이 DB에 존재하는지 확인
        if (scheduleList.containsKey(id)) {
            Schedule schedule = scheduleList.get(id);

            if (schedule.getPassword().equals(password)) {
                //해당 스케줄 삭제하기
                scheduleList.remove(id);
                return id;
            } else {
                throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }


}
