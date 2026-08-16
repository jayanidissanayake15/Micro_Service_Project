package com.spms.parking.dto;

import com.spms.parking.model.SpaceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusUpdateRequest {

    @NotNull(message = "Status is required (AVAILABLE, OCCUPIED, RESERVED)")
    private SpaceStatus status;
}
