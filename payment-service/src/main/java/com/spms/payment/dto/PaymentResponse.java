package com.spms.payment.dto;

import com.spms.payment.model.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;
    private Long userId;
    private Long vehicleId;
    private BigDecimal amount;
    private TransactionStatus status;
    private String receiptId;
    private LocalDateTime timestamp;
    private String message;
}
