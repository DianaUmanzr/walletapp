package com.api.wallet.controller;

import com.api.wallet.dto.request.BankInformationRequestDTO;
import com.api.wallet.service.BankAccountService;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public Map createBankAccount(@RequestBody BankInformationRequestDTO bankInformationRequestDTO) {
        return bankAccountService.saveBankAccount(bankInformationRequestDTO);
    }
}
