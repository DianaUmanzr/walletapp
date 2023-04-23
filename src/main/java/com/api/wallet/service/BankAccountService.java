package com.api.wallet.service;

import com.api.wallet.dto.request.BankInformationRequestDTO;
import com.api.wallet.entity.BankAccount;
import com.api.wallet.entity.User;
import com.api.wallet.repository.BankAccountRepository;
import com.api.wallet.repository.UserRepository;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    private final UserRepository userRepository;

    public Map saveBankAccount(BankInformationRequestDTO bankInformationRequestDTO) {

        Optional<User> userPersisted = userRepository.findById(bankInformationRequestDTO.getUserId());
        User user = User.builder().build();
        if (userPersisted.isPresent()){
            user = User.builder()
                    .userId(userPersisted.get().getUserId())
                    .name(bankInformationRequestDTO.getFirstName())
                    .surname(bankInformationRequestDTO.getLastName())
                    .nationalId(bankInformationRequestDTO.getNationalId())
                    .build();
        } else {
            throw new ServiceException("USER NOT FOUND");
        }

        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(bankInformationRequestDTO.getAccountNumber())
                .routingNumber(bankInformationRequestDTO.getRoutingNumber())
                .bankName(bankInformationRequestDTO.getBankName())
                .user(user).build();

                bankAccountRepository.save(bankAccount);
        return Collections.singletonMap("result", true);
    }
}
