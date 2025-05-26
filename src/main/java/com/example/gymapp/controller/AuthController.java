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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для аутентификации и регистрации.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
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
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(registerRequest.getPassword());
            user.setRole(registerRequest.getRole());
            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно зарегистрирован");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}