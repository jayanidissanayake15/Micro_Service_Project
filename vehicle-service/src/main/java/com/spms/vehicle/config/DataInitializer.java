package com.spms.vehicle.config;

import com.spms.vehicle.model.Vehicle;
import com.spms.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;

    @Override
    public void run(String... args) {
        if (vehicleRepository.count() == 0) {
            log.info("Seeding initial Sri Lankan vehicles data...");

            vehicleRepository.save(Vehicle.builder()
                    .plateNumber("WP-CAB-1234")
                    .type("SEDAN")
                    .userId(1L)
                    .status("OUT")
                    .build());

            vehicleRepository.save(Vehicle.builder()
                    .plateNumber("SP-CAD-5678")
                    .type("SUV")
                    .userId(1L)
                    .status("PARKED")
                    .build());

            vehicleRepository.save(Vehicle.builder()
                    .plateNumber("CP-EV-9999")
                    .type("EV")
                    .userId(2L)
                    .status("OUT")
                    .build());

            log.info("Vehicle Service seed data loaded successfully.");
        }
    }
}
