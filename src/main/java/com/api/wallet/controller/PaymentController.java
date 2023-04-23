package com.api.wallet.controller;

import com.api.wallet.dto.request.PaymentRequestDto;
import com.api.wallet.dto.response.PaymentResponseDto;
import com.api.wallet.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public PaymentResponseDto createPaymentTransaction(@RequestBody PaymentRequestDto paymentRequestDto) {
        return paymentService.createPaymentTransaction(paymentRequestDto);
    }
}
