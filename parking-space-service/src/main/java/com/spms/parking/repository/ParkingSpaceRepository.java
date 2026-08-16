package com.spms.parking.repository;

import com.spms.parking.model.ParkingSpace;
import com.spms.parking.model.SpaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long>, JpaSpecificationExecutor<ParkingSpace> {

    List<ParkingSpace> findByLocationContainingIgnoreCaseAndStatus(String location, SpaceStatus status);

    List<ParkingSpace> findByLocationContainingIgnoreCase(String location);

    List<ParkingSpace> findByStatus(SpaceStatus status);
}
