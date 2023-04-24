package com.api.wallet.controller;

import com.api.wallet.dto.request.PaymentRequestDto;
import com.api.wallet.dto.request.WalletInformationRequestDTO;
import com.api.wallet.dto.request.WalletTransactionRequestDto;
import com.api.wallet.dto.response.PaymentResponseDto;
import com.api.wallet.dto.response.WalletTransactionResponseDto;
import com.api.wallet.service.WalletBusinessDelegate;
import com.api.wallet.service.WalletService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletBusinessDelegate walletBusinessDelegate;

    public WalletController(WalletBusinessDelegate walletBusinessDelegate) {
        this.walletBusinessDelegate = walletBusinessDelegate;
    }

    @PostMapping("/transactions")
    public WalletTransactionResponseDto createWalletTransaction(@RequestBody WalletInformationRequestDTO walletTransactionRequestDto) {
        return walletBusinessDelegate.createWalletTransaction(walletTransactionRequestDto);
    }
}