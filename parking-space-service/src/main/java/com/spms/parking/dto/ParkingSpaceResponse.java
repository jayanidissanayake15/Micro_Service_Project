package com.spms.parking.dto;

import com.spms.parking.model.SpaceStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingSpaceResponse {

    private Long id;
    private String location;
    private String zone;
    private Long ownerId;
    private SpaceStatus status;
}
