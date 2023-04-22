package com.api.wallet.controller;

import com.api.wallet.dto.WalletTransactionRequest;
import com.api.wallet.dto.WalletTransactionResponse;
import com.api.wallet.service.WalletService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/transactions")
    public Mono<WalletTransactionResponse> createWalletTransaction(@RequestBody WalletTransactionRequest walletTransactionRequest) {
        Mono<WalletTransactionResponse> walletTransactionResponseMono = walletService.createWalletTransaction(walletTransactionRequest);
        System.out.println(walletTransactionResponseMono);
        return walletService.createWalletTransaction(walletTransactionRequest);
    }
}