package com.spms.payment.dto;

import com.spms.payment.model.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptResponse {

    private String receiptId;
    private Long transactionId;
    private Long userId;
    private Long vehicleId;
    private BigDecimal amount;
    private TransactionStatus status;
    private LocalDateTime issuedAt;
    private String merchantName;
    private String terms;
}
