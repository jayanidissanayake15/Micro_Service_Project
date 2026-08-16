package com.spms.parking.controller;

import com.spms.parking.dto.*;
import com.spms.parking.model.SpaceStatus;
import com.spms.parking.service.ParkingSpaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-spaces")
@RequiredArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    @GetMapping
    public ResponseEntity<List<ParkingSpaceResponse>> getSpaces(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String zone,
            @RequestParam(required = false) SpaceStatus status) {
        List<ParkingSpaceResponse> spaces = parkingSpaceService.getSpaces(location, zone, status);
        return ResponseEntity.ok(spaces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpaceResponse> getSpaceById(@PathVariable("id") Long id) {
        ParkingSpaceResponse space = parkingSpaceService.getSpaceById(id);
        return ResponseEntity.ok(space);
    }

    @PostMapping
    public ResponseEntity<ParkingSpaceResponse> createSpace(@Valid @RequestBody ParkingSpaceCreateRequest request) {
        ParkingSpaceResponse response = parkingSpaceService.createSpace(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/reserve")
    public ResponseEntity<ParkingSpaceResponse> reserveSpace(@PathVariable("id") Long id) {
        ParkingSpaceResponse response = parkingSpaceService.reserveSpace(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/release")
    public ResponseEntity<ParkingSpaceResponse> releaseSpace(@PathVariable("id") Long id) {
        ParkingSpaceResponse response = parkingSpaceService.releaseSpace(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ParkingSpaceResponse> updateStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody StatusUpdateRequest request) {
        ParkingSpaceResponse response = parkingSpaceService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok(response);
    }
}
