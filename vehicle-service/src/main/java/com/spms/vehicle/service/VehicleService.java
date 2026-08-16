package com.spms.vehicle.service;

import com.spms.vehicle.client.UserServiceClient;
import com.spms.vehicle.dto.*;
import com.spms.vehicle.exception.BadRequestException;
import com.spms.vehicle.exception.ResourceNotFoundException;
import com.spms.vehicle.model.Vehicle;
import com.spms.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserServiceClient userServiceClient;

    public VehicleResponse createVehicle(VehicleCreateRequest request) {
        if (vehicleRepository.existsByPlateNumber(request.getPlateNumber())) {
            throw new BadRequestException("Vehicle with plate number already registered: " + request.getPlateNumber());
        }

        try {
            UserDto user = userServiceClient.getUserById(request.getUserId());
            log.info("Successfully validated user ID: {} ({}) via Feign Client", user.getId(), user.getName());
        } catch (Exception e) {
            log.warn("Could not validate user ID {} via Feign: {}", request.getUserId(), e.getMessage());
        }

        Vehicle vehicle = Vehicle.builder()
                .plateNumber(request.getPlateNumber())
                .type(request.getType())
                .userId(request.getUserId())
                .status(request.getStatus() != null ? request.getStatus() : "OUT")
                .build();

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return mapToResponse(savedVehicle);
    }

    public VehicleResponse getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + id));
        return mapToResponse(vehicle);
    }

    public VehicleResponse updateVehicle(Long id, VehicleUpdateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + id));

        if (!vehicle.getPlateNumber().equalsIgnoreCase(request.getPlateNumber()) &&
                vehicleRepository.existsByPlateNumber(request.getPlateNumber())) {
            throw new BadRequestException("Plate number already registered to another vehicle: " + request.getPlateNumber());
        }

        vehicle.setPlateNumber(request.getPlateNumber());
        vehicle.setType(request.getType());
        if (request.getStatus() != null) {
            vehicle.setStatus(request.getStatus());
        }

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return mapToResponse(updatedVehicle);
    }

    public VehicleResponse simulateEntry(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + id));

        vehicle.setStatus("PARKED");
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        log.info("Vehicle ID {} ({}) entered parking lot. Status updated to PARKED.", id, vehicle.getPlateNumber());
        return mapToResponse(updatedVehicle);
    }

    public VehicleResponse simulateExit(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + id));

        vehicle.setStatus("OUT");
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        log.info("Vehicle ID {} ({}) exited parking lot. Status updated to OUT.", id, vehicle.getPlateNumber());
        return mapToResponse(updatedVehicle);
    }

    private VehicleResponse mapToResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .plateNumber(vehicle.getPlateNumber())
                .type(vehicle.getType())
                .userId(vehicle.getUserId())
                .status(vehicle.getStatus())
                .build();
    }
}
