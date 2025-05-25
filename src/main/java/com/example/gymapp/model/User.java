package com.example.gymapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Модель пользователя (тренер или клиент).
 */
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private String email;
    private String password;
    private String role; // "client" или "trainer"
}