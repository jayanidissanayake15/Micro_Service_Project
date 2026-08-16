package com.spms.user.config;

import com.spms.user.model.User;
import com.spms.user.model.UserRole;
import com.spms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            log.info("Seeding initial Sri Lankan users data...");

            userRepository.save(User.builder()
                    .name("Kasun Perera")
                    .email("john.doe@example.com")
                    .password("password123")
                    .role(UserRole.DRIVER)
                    .build());

            userRepository.save(User.builder()
                    .name("Nimali Fernando")
                    .email("alice.owner@example.com")
                    .password("ownerpass")
                    .role(UserRole.OWNER)
                    .build());

            userRepository.save(User.builder()
                    .name("Kaveen Bandara")
                    .email("admin@spms.com")
                    .password("adminpass")
                    .role(UserRole.ADMIN)
                    .build());

            log.info("User Service seed data loaded successfully.");
        }
    }
}
