package com.spms.user.service;

import com.spms.user.dto.*;
import com.spms.user.exception.BadRequestException;
import com.spms.user.exception.ResourceNotFoundException;
import com.spms.user.model.User;
import com.spms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered: " + request.getEmail());
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    public UserResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        return mapToResponse(user);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return mapToResponse(user);
    }

    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        if (!user.getEmail().equalsIgnoreCase(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already in use: " + request.getEmail());
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

    public List<BookingHistoryDto> getUserBookingHistory(Long id) {
        getUserById(id);

        return List.of(
                BookingHistoryDto.builder()
                        .userId(id)
                        .spaceId(101L)
                        .location("Colombo Fort Central Garage")
                        .zone("Fort-Zone-1")
                        .status("RESERVED")
                        .timestamp(LocalDateTime.now().minusDays(2))
                        .build(),
                BookingHistoryDto.builder()
                        .userId(id)
                        .spaceId(102L)
                        .location("Liberty Plaza Underground - Bambalapitiya")
                        .zone("Bamba-Zone-A")
                        .status("COMPLETED")
                        .timestamp(LocalDateTime.now().minusDays(5))
                        .build()
        );
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
