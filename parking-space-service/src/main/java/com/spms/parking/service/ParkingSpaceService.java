package com.spms.parking.service;

import com.spms.parking.dto.*;
import com.spms.parking.exception.BadRequestException;
import com.spms.parking.exception.ResourceNotFoundException;
import com.spms.parking.model.ParkingSpace;
import com.spms.parking.model.SpaceStatus;
import com.spms.parking.repository.ParkingSpaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingSpaceService {

    private final ParkingSpaceRepository repository;

    public List<ParkingSpaceResponse> getSpaces(String location, String zone, SpaceStatus status) {
        List<ParkingSpace> spaces = repository.findAll();

        return spaces.stream()
                .filter(space -> location == null || space.getLocation().toLowerCase().contains(location.toLowerCase()))
                .filter(space -> zone == null || space.getZone().equalsIgnoreCase(zone))
                .filter(space -> status == null || space.getStatus() == status)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ParkingSpaceResponse getSpaceById(Long id) {
        ParkingSpace space = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));
        return mapToResponse(space);
    }

    public ParkingSpaceResponse createSpace(ParkingSpaceCreateRequest request) {
        ParkingSpace space = ParkingSpace.builder()
                .location(request.getLocation())
                .zone(request.getZone())
                .ownerId(request.getOwnerId())
                .status(request.getStatus() != null ? request.getStatus() : SpaceStatus.AVAILABLE)
                .build();

        ParkingSpace saved = repository.save(space);
        log.info("Created new parking space ID {} in Zone {}", saved.getId(), saved.getZone());
        return mapToResponse(saved);
    }

    public ParkingSpaceResponse reserveSpace(Long id) {
        ParkingSpace space = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));

        if (space.getStatus() != SpaceStatus.AVAILABLE) {
            throw new BadRequestException("Parking space ID " + id + " is not available for reservation (Current status: " + space.getStatus() + ")");
        }

        space.setStatus(SpaceStatus.RESERVED);
        ParkingSpace updated = repository.save(space);
        log.info("Parking space ID {} status changed to RESERVED", id);
        return mapToResponse(updated);
    }

    public ParkingSpaceResponse releaseSpace(Long id) {
        ParkingSpace space = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));

        space.setStatus(SpaceStatus.AVAILABLE);
        ParkingSpace updated = repository.save(space);
        log.info("Parking space ID {} released, status changed to AVAILABLE", id);
        return mapToResponse(updated);
    }

    public ParkingSpaceResponse updateStatus(Long id, SpaceStatus newStatus) {
        ParkingSpace space = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with ID: " + id));

        space.setStatus(newStatus);
        ParkingSpace updated = repository.save(space);
        log.info("Simulated IoT status update for space ID {}: new status = {}", id, newStatus);
        return mapToResponse(updated);
    }

    private ParkingSpaceResponse mapToResponse(ParkingSpace space) {
        return ParkingSpaceResponse.builder()
                .id(space.getId())
                .location(space.getLocation())
                .zone(space.getZone())
                .ownerId(space.getOwnerId())
                .status(space.getStatus())
                .build();
    }
}
