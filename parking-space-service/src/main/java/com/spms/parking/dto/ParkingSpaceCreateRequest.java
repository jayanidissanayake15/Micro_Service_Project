package com.spms.parking.dto;

import com.spms.parking.model.SpaceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingSpaceCreateRequest {

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Zone is required")
    private String zone;

    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    private SpaceStatus status;
}
