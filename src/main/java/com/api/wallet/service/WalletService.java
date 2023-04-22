package com.api.wallet.service;

import com.api.wallet.dto.WalletTransactionRequest;
import com.api.wallet.dto.WalletTransactionResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

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
    public Mono<WalletTransactionResponse> createWalletTransaction(WalletTransactionRequest walletTransactionRequest) {
        return webClient.post()
                .uri("/wallets/transactions")
                .body(BodyInserters.fromValue(walletTransactionRequest))
                .retrieve()
                .bodyToMono(WalletTransactionResponse.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<WalletTransactionResponse> createWalletTransactionFallback(WalletTransactionRequest walletTransactionRequest, Throwable throwable) {
        WalletTransactionResponse walletTransactionResponse = WalletTransactionResponse.builder()
                .transactionId("12")
                .sourceWalletId("waId")
                .destinationWalletId("123")
                .amount(new BigDecimal(10)).build();
        return Mono.just(walletTransactionResponse);
    }

}
