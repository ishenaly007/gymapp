package com.example.gymapp.repository;

import com.example.gymapp.model.TrainerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с расписанием тренеров.
 */
public interface TrainerScheduleRepository extends JpaRepository<TrainerSchedule, Long> {
}