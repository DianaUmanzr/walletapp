package com.api.wallet.service;

import com.api.wallet.dto.request.WalletTransactionRequestDto;
import com.api.wallet.dto.response.WalletTransactionResponseDto;
import com.api.wallet.dto.response.WalletWithdrawalResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WalletWithdrawalService {

    private final WebClient webClient;

    @Value("${external-api.url}")
    private String externalApiUrl;

    public WalletWithdrawalService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(externalApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @CircuitBreaker(name = "wallet-withdrawal-service", fallbackMethod = "createWalletWithdrawalFallback")
    public WalletWithdrawalResponseDto createWalletWithdrawal(Long userId) {
        return webClient.get()
                .uri(externalApiUrl + "/wallets/balance?user_id={user_id}", userId)
                .retrieve()
                .bodyToMono(WalletWithdrawalResponseDto.class)
                .timeout(Duration.ofSeconds(5)).block();
    }

    public WalletWithdrawalResponseDto createWalletWithdrawalFallback(Long userId, Throwable throwable) {
        WalletWithdrawalResponseDto walletWithdrawalResponseDto = WalletWithdrawalResponseDto.builder()
                .user_id(userId).build();
        return walletWithdrawalResponseDto;
    }
}
