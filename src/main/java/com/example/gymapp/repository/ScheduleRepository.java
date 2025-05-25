package com.example.gymapp.repository;

import com.example.gymapp.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с расписанием клиентов.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByEmail(String email);
}