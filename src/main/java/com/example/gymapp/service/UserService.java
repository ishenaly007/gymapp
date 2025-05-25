package com.example.gymapp.service;

import com.example.gymapp.model.User;
import com.example.gymapp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Сервис для инициализации тестовых пользователей.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initUsers() {
        // Инициализация тестовых пользователей
        User client = new User();
        client.setEmail("client@example.com");
        client.setPassword(passwordEncoder.encode("123456"));
        client.setRole("client");
        userRepository.save(client);

        User trainer = new User();
        trainer.setEmail("trainer@example.com");
        trainer.setPassword(passwordEncoder.encode("123456"));
        trainer.setRole("trainer");
        userRepository.save(trainer);
    }
}