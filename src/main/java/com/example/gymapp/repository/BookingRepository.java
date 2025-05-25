package com.example.gymapp.repository;

import com.example.gymapp.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с бронированиями.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {
}