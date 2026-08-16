package com.spms.vehicle.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {

    private Long id;
    private String plateNumber;
    private String type;
    private Long userId;
    private String status;
}
