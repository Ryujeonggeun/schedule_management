package com.sparta.schedule_management.controller;

import com.sparta.schedule_management.dto.ScheduleRequestDto;
import com.sparta.schedule_management.dto.ScheduleResponseDto;
import com.sparta.schedule_management.entity.Schedule;
import com.sparta.schedule_management.service.ScheduleService;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.createSchedule(scheduleRequestDto);

    }

    //전체 일정 날짜 순으로 내림차순
    @GetMapping
    public List<ScheduleResponseDto> getSchedules() {
        return scheduleService.getSchedules();
    }

    @GetMapping("/search")
    private List<ScheduleResponseDto> getSchedulesByDate(@RequestParam LocalDate date) {
        return scheduleService.getDateSchedules(date);
    }


    @PutMapping("/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.updateSchedule(id, scheduleRequestDto);

    }

    @DeleteMapping("/{id}")
    public Long deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto ) {

        return scheduleService.deleteSchedule(id, scheduleRequestDto);

    }


}
