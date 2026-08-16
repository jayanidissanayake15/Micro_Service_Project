package com.spms.payment.config;

import com.spms.payment.model.Transaction;
import com.spms.payment.model.TransactionStatus;
import com.spms.payment.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final TransactionRepository transactionRepository;

    @Override
    public void run(String... args) {
        if (transactionRepository.count() == 0) {
            log.info("Seeding initial payment transactions data...");

            transactionRepository.save(Transaction.builder()
                    .userId(1L)
                    .vehicleId(1L)
                    .amount(new BigDecimal("15.50"))
                    .status(TransactionStatus.SUCCESS)
                    .receiptId("RCP-INIT001")
                    .timestamp(LocalDateTime.now().minusDays(1))
                    .build());

            transactionRepository.save(Transaction.builder()
                    .userId(1L)
                    .vehicleId(2L)
                    .amount(new BigDecimal("30.00"))
                    .status(TransactionStatus.SUCCESS)
                    .receiptId("RCP-INIT002")
                    .timestamp(LocalDateTime.now().minusHours(3))
                    .build());

            log.info("Payment Service seed data loaded successfully.");
        }
    }
}
