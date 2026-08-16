package com.spms.payment.controller;

import com.spms.payment.dto.*;
import com.spms.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentProcessRequest request) {
        PaymentResponse response = paymentService.processPayment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getTransactionById(@PathVariable("id") Long id) {
        PaymentResponse response = paymentService.getTransactionById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/receipt/{receiptId}")
    public ResponseEntity<ReceiptResponse> getReceiptById(@PathVariable("receiptId") String receiptId) {
        ReceiptResponse response = paymentService.getReceiptById(receiptId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponse>> getTransactionsByUserId(@PathVariable("userId") Long userId) {
        List<PaymentResponse> responses = paymentService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(responses);
    }
}
