package com.sparta.schedule_management.repository;

import com.sparta.schedule_management.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByOrderByDateAsc();
    List<Schedule> findAllByDate(LocalDate date);
}
