package com.sparta.schedule_management.controller;

import com.sparta.schedule_management.dto.ScheduleRequestDto;
import com.sparta.schedule_management.dto.ScheduleResponseDto;
import com.sparta.schedule_management.entity.Schedule;
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





}
