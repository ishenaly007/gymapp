package com.example.gymapp.controller;

import com.example.gymapp.dto.LoginRequest;
import com.example.gymapp.dto.LoginResponse;
import com.example.gymapp.dto.RegisterRequest;
import com.example.gymapp.model.User;
import com.example.gymapp.security.JwtTokenProvider;
import com.example.gymapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Контроллер для аутентификации и регистрации.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Попытка входа для email: {}", loginRequest.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);

            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setEmail(loginRequest.getEmail());
            response.setRole(authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "").toLowerCase());
            logger.info("Успешный вход для email: {}", loginRequest.getEmail());
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            logger.error("Неверные учетные данные для email: {}", loginRequest.getEmail(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            logger.error("Ошибка при аутентификации для email: {}", loginRequest.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            logger.info("Попытка регистрации для email: {}", registerRequest.getEmail());
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(registerRequest.getPassword());
            user.setRole(registerRequest.getRole());
            userService.registerUser(user);
            logger.info("Успешная регистрация для email: {}", registerRequest.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно зарегистрирован");
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка регистрации для email: {}", registerRequest.getEmail(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}