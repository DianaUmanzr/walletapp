package com.api.wallet.service;

import com.api.wallet.dto.PaymentRequestDto;
import com.api.wallet.dto.PaymentResponseDto;
import com.api.wallet.dto.RequestInfoDTO;
import com.api.wallet.dto.WalletTransactionRequestDto;
import com.api.wallet.dto.WalletTransactionResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    @CircuitBreaker(name = "payment-service", fallbackMethod = "createPaymentTransactionFallback")
    public Mono<PaymentResponseDto> createPaymentTransaction(PaymentRequestDto paymentRequestDto) {
        return webClient.post()
                .uri(externalApiUrl + "/api/v1/payments")
                .body(BodyInserters.fromValue(paymentRequestDto))
                .retrieve()
                .bodyToMono(PaymentResponseDto.class);
        //.timeout(Duration.ofSeconds(5));
    }

    public Mono<PaymentResponseDto> createPaymentTransactionFallback(PaymentRequestDto paymentRequestDto, Throwable throwable) {
        PaymentResponseDto paymentResponseDto = PaymentResponseDto.builder().build();
        return Mono.just(paymentResponseDto);
    }
}
