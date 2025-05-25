package com.example.gymapp.controller;

import com.example.gymapp.model.Schedule;
import com.example.gymapp.repository.ScheduleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с расписанием клиентов.
 */
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @GetMapping(params = "email")
    public List<Schedule> getSchedulesByEmail(@RequestParam String email) {
        return scheduleRepository.findByEmail(email);
    }

    @PostMapping
    public Schedule createSchedule(@Valid @RequestBody Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable Long id, @Valid @RequestBody Schedule schedule) {
        schedule.setId(id);
        return scheduleRepository.save(schedule);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleRepository.deleteById(id);
    }
}