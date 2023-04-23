package com.api.wallet.service;

import com.api.wallet.dto.request.BankAccountRequestDTO;
import com.api.wallet.dto.request.PaymentRequestDto;
import com.api.wallet.dto.response.PaymentResponseDto;
import com.api.wallet.entity.BankAccount;
import com.api.wallet.entity.User;
import com.api.wallet.repository.BankAccountRepository;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public Map saveBankAccount(BankAccountRequestDTO bankAccountRequestDTO) {
        User user = User.builder().name(bankAccountRequestDTO.getFirstName())
                        .surname(bankAccountRequestDTO.getLastName())
                        .nationalId(bankAccountRequestDTO.getIdentificationNumber())
                .build();

        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(bankAccountRequestDTO.getAccountNumber())
                .routingNumber(bankAccountRequestDTO.getRoutingNumber())
                .user(user).build();

                bankAccountRepository.save(bankAccount);
        return Collections.singletonMap("result", true);
    }
}
