package com.spms.payment.service;

import com.spms.payment.dto.*;
import com.spms.payment.exception.BadRequestException;
import com.spms.payment.exception.ResourceNotFoundException;
import com.spms.payment.model.Transaction;
import com.spms.payment.model.TransactionStatus;
import com.spms.payment.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final TransactionRepository transactionRepository;

    public PaymentResponse processPayment(PaymentProcessRequest request) {
        if (request.getCardNumber() == null || request.getCardNumber().replaceAll("\\s+", "").length() < 13) {
            throw new BadRequestException("Invalid payment card details provided.");
        }

        String receiptId = "RCP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Transaction transaction = Transaction.builder()
                .userId(request.getUserId())
                .vehicleId(request.getVehicleId())
                .amount(request.getAmount())
                .status(TransactionStatus.SUCCESS)
                .receiptId(receiptId)
                .timestamp(LocalDateTime.now())
                .build();

        Transaction saved = transactionRepository.save(transaction);
        log.info("Payment of ${} processed successfully for User ID {}, Vehicle ID {}. Receipt ID: {}",
                saved.getAmount(), saved.getUserId(), saved.getVehicleId(), saved.getReceiptId());

        return PaymentResponse.builder()
                .id(saved.getId())
                .userId(saved.getUserId())
                .vehicleId(saved.getVehicleId())
                .amount(saved.getAmount())
                .status(saved.getStatus())
                .receiptId(saved.getReceiptId())
                .timestamp(saved.getTimestamp())
                .message("Payment processed successfully")
                .build();
    }

    public PaymentResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));

        return mapToPaymentResponse(transaction, "Transaction retrieved successfully");
    }

    public ReceiptResponse getReceiptById(String receiptId) {
        Transaction transaction = transactionRepository.findByReceiptId(receiptId)
                .orElseThrow(() -> new ResourceNotFoundException("Receipt not found for ID: " + receiptId));

        return ReceiptResponse.builder()
                .receiptId(transaction.getReceiptId())
                .transactionId(transaction.getId())
                .userId(transaction.getUserId())
                .vehicleId(transaction.getVehicleId())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .issuedAt(transaction.getTimestamp())
                .merchantName("Smart Parking Management System (SPMS) Ltd.")
                .terms("Non-refundable digital parking receipt. Valid for 24 hours from issuance.")
                .build();
    }

    public List<PaymentResponse> getTransactionsByUserId(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        return transactions.stream()
                .map(t -> mapToPaymentResponse(t, "Success"))
                .collect(Collectors.toList());
    }

    private PaymentResponse mapToPaymentResponse(Transaction t, String message) {
        return PaymentResponse.builder()
                .id(t.getId())
                .userId(t.getUserId())
                .vehicleId(t.getVehicleId())
                .amount(t.getAmount())
                .status(t.getStatus())
                .receiptId(t.getReceiptId())
                .timestamp(t.getTimestamp())
                .message(message)
                .build();
    }
}
