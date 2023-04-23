package com.api.wallet.controller;

import com.api.wallet.dto.PaymentRequestDto;
import com.api.wallet.dto.PaymentResponseDto;
import com.api.wallet.dto.WalletTransactionRequestDto;
import com.api.wallet.dto.WalletTransactionResponseDto;
import com.api.wallet.service.PaymentService;
import com.api.wallet.service.WalletService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Mono<PaymentResponseDto> createPaymentTransaction(@RequestBody PaymentRequestDto paymentRequestDto) {
        Mono<PaymentResponseDto> paymentResponseDtoMono = paymentService.createPaymentTransaction(paymentRequestDto);
        System.out.println(paymentResponseDtoMono);
        return paymentService.createPaymentTransaction(paymentRequestDto);
    }
}
