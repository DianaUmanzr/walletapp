package com.api.wallet.service;

import com.api.wallet.dto.request.PaymentRequestDto;
import com.api.wallet.dto.request.WalletTransactionRequestDto;
import com.api.wallet.dto.response.PaymentResponseDto;
import com.api.wallet.dto.response.WalletTransactionResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.math.BigDecimal;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WalletService {

    private final WebClient webClient;

    @Value("${external-api.url}")
    private String externalApiUrl;

    public WalletService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(externalApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @CircuitBreaker(name = "wallet-service", fallbackMethod = "createWalletTransactionFallback")
    public WalletTransactionResponseDto createWalletTransaction(WalletTransactionRequestDto walletTransactionRequestDto) {
        return webClient.post()
                .uri(externalApiUrl + "/wallets/transactions")
                .bodyValue(walletTransactionRequestDto)
                .retrieve()
                .bodyToMono(WalletTransactionResponseDto.class)
                .timeout(Duration.ofSeconds(5)).block();
    }

    public WalletTransactionResponseDto createWalletTransactionFallback(WalletTransactionRequestDto walletTransactionRequestDto, Throwable throwable) {
        WalletTransactionResponseDto walletTransactionResponseDto = WalletTransactionResponseDto.builder().build();
        return walletTransactionResponseDto;
    }
}
