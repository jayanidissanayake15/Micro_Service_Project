package com.spms.user.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingHistoryDto {

    private Long userId;
    private Long spaceId;
    private String location;
    private String zone;
    private String status;
    private LocalDateTime timestamp;
}
