package com.example.gymapp.controller;

import com.example.gymapp.model.TrainerSchedule;
import com.example.gymapp.repository.TrainerScheduleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с расписанием тренеров.
 */
@RestController
@RequestMapping("/trainerSchedule")
public class TrainerScheduleController {

    @Autowired
    private TrainerScheduleRepository trainerScheduleRepository;

    @GetMapping
    public List<TrainerSchedule> getTrainerSchedule() {
        return trainerScheduleRepository.findAll();
    }

    @PostMapping
    public TrainerSchedule createTrainerSchedule(@Valid @RequestBody TrainerSchedule schedule) {
        return trainerScheduleRepository.save(schedule);
    }

    @PutMapping("/{id}")
    public TrainerSchedule updateTrainerSchedule(@PathVariable Long id, @Valid @RequestBody TrainerSchedule schedule) {
        schedule.setId(id);
        return trainerScheduleRepository.save(schedule);
    }

    @DeleteMapping("/{id}")
    public void deleteTrainerSchedule(@PathVariable Long id) {
        trainerScheduleRepository.deleteById(id);
    }
}