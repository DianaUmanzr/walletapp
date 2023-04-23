package com.api.wallet.controller;

import com.api.wallet.dto.request.WalletTransactionRequestDto;
import com.api.wallet.dto.response.WalletTransactionResponseDto;
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
    public Mono<WalletTransactionResponseDto> createWalletTransaction(@RequestBody WalletTransactionRequestDto walletTransactionRequestDto) {
        Mono<WalletTransactionResponseDto> walletTransactionResponseMono = walletService.createWalletTransaction(walletTransactionRequestDto);
        System.out.println(walletTransactionResponseMono);
        return walletService.createWalletTransaction(walletTransactionRequestDto);
    }
}