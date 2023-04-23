package com.api.wallet.controller;

import com.api.wallet.dto.request.WalletTransactionRequestDto;
import com.api.wallet.dto.response.WalletTransactionResponseDto;
import com.api.wallet.dto.response.WalletWithdrawalResponseDto;
import com.api.wallet.service.WalletService;
import com.api.wallet.service.WalletWithdrawalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets/balance")
public class WalletWithdrawalController
{
    private final WalletWithdrawalService walletWithdrawalService;

    public WalletWithdrawalController(WalletWithdrawalService walletWithdrawalService) {
        this.walletWithdrawalService = walletWithdrawalService;
    }

    @GetMapping
    public WalletWithdrawalResponseDto createWalletTransaction(@RequestParam Long userId) {
        return walletWithdrawalService.createWalletWithdrawal(userId);
    }
}
