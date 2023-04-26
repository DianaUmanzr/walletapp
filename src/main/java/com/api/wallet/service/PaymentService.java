package com.api.wallet.service;


import com.api.wallet.dto.request.PaymentRequestDto;
import com.api.wallet.dto.response.PaymentResponseDto;
import exception.CommonErrors;
import exception.ExceptionUtils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PaymentService {

    private final WebClient webClient;

    @Value("${external-api.url}")
    private String externalApiUrl;

    public PaymentService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(externalApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @CircuitBreaker(name = "payment-service", fallbackMethod = "createPaymentFallback")
    public PaymentResponseDto createPaymentTransaction(PaymentRequestDto paymentRequestDto) {
        return webClient.post()
                .uri(externalApiUrl + "/api/v1/payments")
                .bodyValue(paymentRequestDto)
                .retrieve()
                .bodyToMono(PaymentResponseDto.class)
                .timeout(Duration.ofSeconds(5)).block();
    }

    public PaymentResponseDto createPaymentFallback(PaymentRequestDto paymentRequestDto, Throwable throwable) {
        ExceptionUtils.throwCallPaymentTransactionException(CommonErrors.CALL_PAYMENT_T_001_ERROR);
        return new PaymentResponseDto();
    }
}
