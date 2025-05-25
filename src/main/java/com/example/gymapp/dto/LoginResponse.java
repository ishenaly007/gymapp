package com.example.gymapp.dto;

import lombok.Data;

/**
 * DTO для ответа при успешной аутентификации.
 */
@Data
public class LoginResponse {
    private String token;
    private String email;
    private String role;
}