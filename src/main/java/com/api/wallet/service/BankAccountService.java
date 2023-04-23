package com.api.wallet.service;

import com.api.wallet.dto.request.BankInformationRequestDTO;
import com.api.wallet.entity.BankAccount;
import com.api.wallet.entity.User;
import com.api.wallet.repository.BankAccountRepository;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public Map saveBankAccount(BankInformationRequestDTO bankInformationRequestDTO) {

        User user = User.builder().name(bankInformationRequestDTO.getFirstName())
                        .surname(bankInformationRequestDTO.getLastName())
                        .nationalId(bankInformationRequestDTO.getNationalId())
                .build();

        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(bankInformationRequestDTO.getAccountNumber())
                .routingNumber(bankInformationRequestDTO.getRoutingNumber())
                .user(user).build();

                bankAccountRepository.save(bankAccount);
        return Collections.singletonMap("result", true);
    }
}
