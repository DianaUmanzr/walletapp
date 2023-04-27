package com.api.wallet.controller;

import com.api.wallet.dto.request.PaymentOnTopRequestDto;
import com.api.wallet.dto.response.PaymentResponseDto;
import com.api.wallet.service.PaymentBusinessDelegate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentBusinessDelegate paymentBusinessDelegate;

    public PaymentController(PaymentBusinessDelegate paymentBusinessDelegate) {
        this.paymentBusinessDelegate = paymentBusinessDelegate;
    }

    @PostMapping
    public PaymentResponseDto createPaymentTransaction(@RequestBody PaymentOnTopRequestDto paymentRequestDto) {
        return paymentBusinessDelegate.createPaymentTransaction(paymentRequestDto);
    }
}
