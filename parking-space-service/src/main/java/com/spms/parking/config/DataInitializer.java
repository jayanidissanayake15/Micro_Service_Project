package com.spms.parking.config;

import com.spms.parking.model.ParkingSpace;
import com.spms.parking.model.SpaceStatus;
import com.spms.parking.repository.ParkingSpaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ParkingSpaceRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            log.info("Seeding initial Sri Lankan parking spaces data...");

            repository.save(ParkingSpace.builder()
                    .location("Colombo Fort Central Garage")
                    .zone("Fort-Zone-1")
                    .ownerId(2L)
                    .status(SpaceStatus.AVAILABLE)
                    .build());

            repository.save(ParkingSpace.builder()
                    .location("Liberty Plaza Underground - Bambalapitiya")
                    .zone("Bamba-Zone-A")
                    .ownerId(2L)
                    .status(SpaceStatus.OCCUPIED)
                    .build());

            repository.save(ParkingSpace.builder()
                    .location("Kandy City Centre Hub")
                    .zone("Kandy-Zone-1")
                    .ownerId(2L)
                    .status(SpaceStatus.RESERVED)
                    .build());

            repository.save(ParkingSpace.builder()
                    .location("Galle Face Green Parking Complex")
                    .zone("GalleFace-EV-Zone")
                    .ownerId(2L)
                    .status(SpaceStatus.AVAILABLE)
                    .build());

            log.info("Parking Space Service seed data loaded successfully.");
        }
    }
}
