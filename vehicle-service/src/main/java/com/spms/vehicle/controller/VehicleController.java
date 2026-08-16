package com.spms.vehicle.controller;

import com.spms.vehicle.dto.*;
import com.spms.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(@Valid @RequestBody VehicleCreateRequest request) {
        VehicleResponse response = vehicleService.createVehicle(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable("id") Long id) {
        VehicleResponse response = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(
            @PathVariable("id") Long id,
            @Valid @RequestBody VehicleUpdateRequest request) {
        VehicleResponse response = vehicleService.updateVehicle(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/entry")
    public ResponseEntity<VehicleResponse> simulateEntry(@PathVariable("id") Long id) {
        VehicleResponse response = vehicleService.simulateEntry(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/exit")
    public ResponseEntity<VehicleResponse> simulateExit(@PathVariable("id") Long id) {
        VehicleResponse response = vehicleService.simulateExit(id);
        return ResponseEntity.ok(response);
    }
}
