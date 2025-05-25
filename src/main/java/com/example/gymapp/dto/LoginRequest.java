package com.example.gymapp.dto;

import lombok.Data;

/**
 * DTO для запроса на вход.
 */
@Data
public class LoginRequest {
    private String email;
    private String password;
}