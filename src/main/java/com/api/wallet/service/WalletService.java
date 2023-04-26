package com.api.wallet.service;

import com.api.wallet.dto.request.WalletTransactionRequestDto;
import com.api.wallet.dto.response.WalletTransactionResponseDto;
import com.api.wallet.entity.BankAccount;
import com.api.wallet.entity.Transaction;
import com.api.wallet.entity.User;
import com.api.wallet.entity.Wallet;
import com.api.wallet.repository.TransactionRepository;
import com.api.wallet.repository.WalletRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.time.Duration;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WalletService {

    private final WebClient webClient;

    private final WalletRepository walletRepository;

    @Value("${external-api.url}")
    private String externalApiUrl;

    public WalletService(WebClient.Builder webClientBuilder, WalletRepository walletRepository) {
        this.webClient = webClientBuilder.baseUrl(externalApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.walletRepository = walletRepository;
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

    public Optional<Wallet> findWalletByUserId(Long userId) {
        return walletRepository.findOne(new Specification<Wallet>() {
            public Predicate toPredicate(Root<Wallet> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<Wallet, User> joinWU = root.join("user");
                return criteriaBuilder.equal(joinWU.get("userId"), userId);
            }
        });
    }

    public <S extends Wallet> S save(S entity) {
        return walletRepository.save(entity);
    }
}

